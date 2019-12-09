/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.AnswerOption;
import com.hgedu_server.models.Question;
import com.hgedu_server.repositories.AnswerRepository;
import com.hgedu_server.repositories.QuestionRepository;
import com.hgedu_server.repositories.TestToWordRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import uk.ac.ed.ph.snuggletex.SnuggleEngine;
import uk.ac.ed.ph.snuggletex.SnuggleInput;
import uk.ac.ed.ph.snuggletex.SnuggleSession;

/**
 *
 * @author admin
 */
@Service
public class TestToWordService {

    private Path fileStorageLocation;

    @Autowired
    private TestToWordRepository testToWordRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    static File stylesheet = new File("MML2OMML.XSL");
    static TransformerFactory tFactory = TransformerFactory.newInstance();
    static StreamSource stylesource = new StreamSource(stylesheet);

    public CTOMath getOMML(String mathML) throws Exception {
        Transformer transformer = tFactory.newTransformer(stylesource);
        StringReader stringreader = new StringReader(mathML);
        StreamSource source = new StreamSource(stringreader);
        StringWriter stringwriter = new StringWriter();
        StreamResult result = new StreamResult(stringwriter);
        transformer.transform(source, result);
        String ooML = stringwriter.toString();
        stringwriter.close();
        CTOMathPara ctOMathPara = CTOMathPara.Factory.parse(ooML);
        CTOMath ctOMath = ctOMathPara.getOMathArray(0);
        XmlCursor xmlcursor = ctOMath.newCursor();
        while (xmlcursor.hasNextToken()) {
            XmlCursor.TokenType tokentype = xmlcursor.toNextToken();
            if (tokentype.isStart()) {
                if (xmlcursor.getObject() instanceof CTR) {
                    CTR cTR = (CTR) xmlcursor.getObject();
                    cTR.addNewRPr2().addNewRFonts().setAscii("Cambria Math");
                    cTR.getRPr2().getRFonts().setHAnsi("Cambria Math");
                }
            }
        }
        return ctOMath;
    }

    public void parseTextWithMathML(XWPFParagraph paragraph, XWPFRun runLatex, String text) {
        try {
            SnuggleEngine engine = new SnuggleEngine();
            SnuggleSession sSession = engine.createSession();
            SnuggleInput input = new SnuggleInput(text);
            sSession.parseInput(input);
            String mathML = sSession.buildXMLString();
            for (String s : mathML.split("\\s+(?=<math)|(?<=</math>)\\s+")) {
                if (s.startsWith("<math")) {
                    CTOMath ctOMath = getOMML(s);
                    CTP ctp = paragraph.getCTP();
                    ctp.addNewOMath();
                    ctp.setOMathArray(ctp.sizeOfOMathArray() - 1, ctOMath);
                } else {
                    runLatex = paragraph.createRun();

                    runLatex.setText(" " + s + " ");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TestToWordService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setRun(XWPFRun run, String fontFamily, int fontSize, String colorRGB, String text, boolean bold, boolean addBreak) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setColor(colorRGB);
        run.setText(text);
        run.setBold(bold);
        if (addBreak) {
            run.addBreak();
        }
    }

    public void getQuestions() {
        FileInputStream input;
        try {
            input = new FileInputStream("read3.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            Sheet sheet0 = workbook.getSheetAt(0);
            Iterator<Row> iteratorRow = sheet0.iterator();
            String regex = "<[a-z][a-z]>";
            String debai = "";
            String wrapFormular = "{\"insert\":{\"formula\":\"";
            String wrapText = "{\"insert\":\"";
            int answer = 0;
            String qCode = "";
            Long questionId = null;
            ArrayList<AnswerOption> aos = new ArrayList<>();
            ArrayList<Question> qs = new ArrayList<>();
            while (iteratorRow.hasNext()) {
                Row row = iteratorRow.next();
                if (row.getCell(1).getStringCellValue().equals("end")) {
                    if (aos.size() > 1 && aos.size() < 5) {
                        int checkCorrect = 0;
                        for (int i = 0; i < aos.size(); i++) {
                            if (aos.get(i).isIsCorrect() == true) {
                                checkCorrect++;
                            }
                        }
                        if (checkCorrect == 1) {
                            for (Question question : qs) {
                                try {
                                    questionRepository.save(question);
                                    questionId = questionRepository.findQuestionIdByQuestionCode(question.getQuestionCode());
                                    for (int i = 0; i < aos.size(); i++) {
                                        aos.get(i).setQuestionId(questionId);
//                                        answerRepository.save(aos.get(i));
                                    }
                                    answerRepository.saveAll(aos);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    break;
                }
                if (row.getCell(0) != null) {
                    int checkCorrect = 0;
                    if (aos.size() > 1 && aos.size() < 5) {
                        for (int i = 0; i < aos.size(); i++) {
                            if (aos.get(i).isIsCorrect() == true) {
                                checkCorrect++;
                            }
                        }
                        if (checkCorrect == 1) {
                            for (Question question : qs) {
                                try {
                                    questionRepository.save(question);
                                    questionId = questionRepository.findQuestionIdByQuestionCode(question.getQuestionCode());
                                    for (int i = 0; i < aos.size(); i++) {
                                        aos.get(i).setQuestionId(questionId);
                                    }
                                    answerRepository.saveAll(aos);
                                } catch (Exception e) {
                                    System.out.println("aaa");
                                }
                            }

                        }
                    }
                    qs.clear();
                    Question qtion = new Question();
                    String json = "{\"ops\":[";
                    String[] s1 = null;
                    String[] s2 = null;
                    qCode = row.getCell(1).getStringCellValue();
                    String question = row.getCell(2).getStringCellValue();
                    String description = row.getCell(3).getStringCellValue();
                    int difficultyId = (int) row.getCell(4).getNumericCellValue();
                    int gradeLevelId = (int) row.getCell(5).getNumericCellValue();
                    int qTypeId = (int) row.getCell(6).getNumericCellValue();
                    String explanation = row.getCell(7).getStringCellValue();
                    s1 = question.split(regex);
                    for (int k = 0; k < s1.length; k++) {
                        if (s1[k].contains("</ct>")) {
                            s2 = s1[k].split("</ct>");
                            for (int a = 0; a < s2.length; a++) {
                                if (a == 0) {
                                    json += wrapFormular + s2[a].trim() + "\"}},";
                                } else {
                                    json += wrapText + s2[a].trim() + "\"},";
                                }
                            }
                        } else {
                            json += wrapText + s1[0].trim() + "\"},";
                        }
                    }
                    json += "]}";
                    qtion.setQuestionCode(qCode);
                    qtion.setContent(json);
                    qtion.setDescription(description);
                    qtion.setDifficultyId(difficultyId);
                    qtion.setGradeLevelId(gradeLevelId);
                    qtion.setQuestionTypeId(qTypeId);
                    qtion.setExplanation(explanation);
                    qs.add(qtion);
                    aos.clear();
                } else {
                    AnswerOption answerOption = new AnswerOption();
                    String json = "[";
                    String[] s1 = null;
                    String[] s2 = null;
                    String content = row.getCell(1).getStringCellValue();
                    boolean isCorrect = row.getCell(2).getBooleanCellValue();
                    s1 = content.split(regex);
                    for (int k = 0; k < s1.length; k++) {
                        if (s1[k].contains("</ct>")) {
                            s2 = s1[k].split("</ct>");
                            for (int a = 0; a < s2.length; a++) {
                                if (a == 0) {
                                    json += wrapFormular + s2[a].trim() + "\"}},";
                                } else {
                                    json += wrapText + s2[a].trim() + "\"},";
                                }
                            }
                        } else {
                            json += wrapText + s1[0].trim() + "\"},";
                        }
                    }
                    json += "]";
                    answerOption.setContent(json);
                    answerOption.setIsCorrect(isCorrect);
                    aos.add(answerOption);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TestToWordService.class.getName()).log(Level.SEVERE, null, ex);
        }
        formatWord();
    }

    public void formatWord() {
        try {
            String qResult = "";
            String aResult = "";
            List<AnswerOption> answerOptions = answerRepository.findAll();
            List<Question> questions = questionRepository.findAll();
            for (int i = 0; i < answerOptions.size(); i++) {
                JSONParser parser = new JSONParser();
                JSONArray jsonArr = (JSONArray) parser.parse(answerOptions.get(i).getContent());
                for (int j = 0; j < jsonArr.size(); j++) {
                    JSONObject object = (JSONObject) jsonArr.get(j);
                    if (object.get("insert") instanceof String) {
                        aResult += object.get("insert");
                    } else {
                        String formula = " $" + ((JSONObject) object.get("insert")).get("formula") + "$ ";
                        aResult += formula;
                    }
                }
                answerOptions.get(i).setContent(aResult);
                aResult = "";
            }
            for (int i = 0; i < questions.size(); i++) {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(questions.get(i).getContent());
                JSONArray jsonArr = (JSONArray) jsonObject.get("ops");
                for (int j = 0; j < jsonArr.size(); j++) {
                    JSONObject object = (JSONObject) jsonArr.get(j);
                    if (object.get("insert") instanceof String) {
                        qResult += object.get("insert");
                    } else {
                        try {
                            String width = ((JSONObject) object.get("attributes")).get("width") + "<size>";
                            String imageLink = ((JSONObject) object.get("insert")).get("image") + "</image>";
                            qResult += "<image>" + width + imageLink;
                        } catch (Exception e) {
                            String formula = " $" + ((JSONObject) object.get("insert")).get("formula") + "$ ";
                            qResult += formula;
                        }
                    }
                }
                questions.get(i).setContent(qResult);
                qResult = "";
            }
        } catch (Exception e) {
            Logger.getLogger(TestToWordService.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void toWord(List<Question> questions, List<AnswerOption> answerOptions) {
        try {
            XWPFDocument doc = new XWPFDocument();
            FileOutputStream fos = new FileOutputStream(new File("format_file.docx"));
            XWPFTable table = doc.createTable(1, 2);
            table.setWidth(10000);
            XWPFTableRow tableRowOne = table.getRow(0);
            tableRowOne.getCell(0).setWidth("1200");
            XWPFParagraph paragraph0 = tableRowOne.getCell(0).addParagraph();
            paragraph0.setSpacingBefore(100);
            paragraph0.setSpacingAfter(100);
            setRun(paragraph0.createRun(), "Verdana", 10, "000000", " Câu", true, false);
            tableRowOne.getCell(0).removeParagraph(0);
            tableRowOne.getCell(1).setWidth("9000");
            XWPFParagraph paragraph1 = tableRowOne.getCell(1).addParagraph();
            paragraph1.setAlignment(ParagraphAlignment.CENTER);
            paragraph1.setSpacingAfter(100);
            paragraph1.setSpacingBefore(100);
            setRun(paragraph1.createRun(), "Verdana", 10, "000000", "Nội dung", true, false);
            tableRowOne.getCell(1).removeParagraph(0);
            String s1[] = null;
            String s2[] = null;
            String s3[] = null;
            String s4[] = null;
            for (int i = 0; i < questions.size(); i++) {
                XWPFTableRow tableRow = table.createRow();
                XWPFParagraph questionPara0 = tableRow.getCell(0).addParagraph();
                setRun(questionPara0.createRun(), "Verdana", 10, "000000", " Câu " + (i + 1) + ":", true, false);
                XWPFParagraph questionPara1Q = tableRow.getCell(1).addParagraph();
                String content = questions.get(i).getContent();
                if (content.contains("<image>")) {
                    if (content.contains("</image><image>")) {
                        s1 = content.split("</image><image>");
                        for (String s : s1) {
                            s2 = s.split("<image>");
                            for (String s2Item : s2) {
                                if (!s2Item.contains("<size>")) {
                                    XmlCursor cursor1 = questionPara1Q.getCTP().newCursor();
                                    XWPFParagraph p1 = questionPara1Q.getBody().insertNewParagraph(cursor1);
                                    XWPFRun runP1 = null;
                                    parseTextWithMathML(p1, runP1, s2Item);
                                } else {
                                    s3 = s2Item.split("</image>");
                                    for (String s3Item : s3) {
                                        if (!s3Item.contains("<size>")) {
                                            XmlCursor cursor2 = questionPara1Q.getCTP().newCursor();
                                            XWPFParagraph p2 = questionPara1Q.getBody().insertNewParagraph(cursor2);
                                            XWPFRun runP2 = null;
                                            parseTextWithMathML(p2, runP2, s3Item);
                                        } else {
                                            s4 = s3Item.split("<size>");
                                            Double width = Double.parseDouble(s4[0]);
                                            XmlCursor cursor3 = questionPara1Q.getCTP().newCursor();
                                            XWPFParagraph p3 = questionPara1Q.getBody().insertNewParagraph(cursor3);
                                            XWPFRun runP3 = p3.createRun();
                                            runP3.addPicture(new FileInputStream(s4[1]), XWPFDocument.PICTURE_TYPE_PNG, s4[1], Units.toEMU(width), Units.toEMU(width));
                                            p3.setAlignment(ParagraphAlignment.CENTER);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        s1 = content.split("<image>");
                        for (int q = 0; q < s1.length; q++) {
                            if (q == 0) {
                                XmlCursor cursor1 = questionPara1Q.getCTP().newCursor();
                                XWPFParagraph p1 = questionPara1Q.getBody().insertNewParagraph(cursor1);
                                XWPFRun runP1 = null;
                                String parseForm = s1[0];
                                parseTextWithMathML(p1, runP1, parseForm);
                            } else {
                                s2 = s1[q].split("</image>");
                                for (int w = 0; w < s2.length; w++) {
                                    if (w == 1) {
                                        XmlCursor cursor2 = questionPara1Q.getCTP().newCursor();
                                        XWPFParagraph p2 = questionPara1Q.getBody().insertNewParagraph(cursor2);
                                        XWPFRun runP2 = null;
                                        String parseForm = s2[1];
                                        parseTextWithMathML(p2, runP2, parseForm);
                                        p2.setAlignment(ParagraphAlignment.LEFT);
                                    } else {
                                        s3 = s2[0].split("<size>");
                                        Double width = Double.parseDouble(s3[0]);
                                        XmlCursor cursor1 = questionPara1Q.getCTP().newCursor();
                                        XWPFParagraph p3 = questionPara1Q.getBody().insertNewParagraph(cursor1);
                                        XWPFRun runP3 = p3.createRun();
                                        runP3.addPicture(new FileInputStream(s3[1]), XWPFDocument.PICTURE_TYPE_PNG, s3[1], Units.toEMU(width), Units.toEMU(width));
                                        p3.setAlignment(ParagraphAlignment.CENTER);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    XWPFRun qRun = null;
                    parseTextWithMathML(questionPara1Q, qRun, content);
                }
                questionPara1Q.setSpacingAfter(150);
                XWPFParagraph answerPara = tableRow.getCell(1).addParagraph();
                tableRow.getCell(1).removeParagraph(0);
                List<String> answerContent = new ArrayList<>();
                for (int m = 0; m < answerOptions.size(); m++) {
                    if (questions.get(i).getQuestionId().equals(answerOptions.get(m).getQuestionId())) {
                        answerContent.add(answerOptions.get(m).getContent());
                    }
                }
                int type = 0;
                for (String outputItem : answerContent) {
                    if (outputItem.length() < 15 && type <= 0) {
                        type = 1;
                    }
                    if (outputItem.length() >= 15 && outputItem.length() < 30 && type <= 1) {
                        type = 2;
                    }
                    if (outputItem.length() >= 30 && type <= 2) {
                        type = 3;
                    }
                }
                XmlCursor cursor = answerPara.getCTP().newCursor();
                XWPFTable nestedTable = answerPara.getBody().insertNewTbl(cursor);
                XWPFTableRow nestedTableRow = null;
                XWPFTableCell cellOfNestedTable;
                for (int j = 0; j < answerContent.size(); j++) {
                    String choice = "";
                    switch (j) {
                        case 0:
                            choice = " A";
                            break;
                        case 1:
                            choice = " B";
                            break;
                        case 2:
                            choice = " C";
                            break;
                        case 3:
                            choice = " D";
                            break;
                    }
                    if (type == 1) {
                        if (j % 4 == 0) {
                            nestedTableRow = nestedTable.createRow();
                        }
                        cellOfNestedTable = nestedTableRow.createCell();
                        cellOfNestedTable.setWidth("2250");
                        XWPFParagraph para = cellOfNestedTable.addParagraph();
                        cellOfNestedTable.removeParagraph(0);
                        XWPFRun answerRun = null;
                        String answerParseForm = choice + ".  " + answerContent.get(j);
                        parseTextWithMathML(para, answerRun, answerParseForm);
                    }
                    if (type == 2) {
                        if (j == 0 || j % 2 == 0) {
                            nestedTableRow = nestedTable.createRow();
                        }
                        if (j <= 1) {
                            cellOfNestedTable = nestedTableRow.createCell();
                        } else {
                            if (j % 2 == 0) {
                                cellOfNestedTable = nestedTableRow.getCell(0);
                            } else {
                                cellOfNestedTable = nestedTableRow.getCell(1);
                            }
                        }
                        cellOfNestedTable.setWidth("4500");
                        XWPFParagraph para = cellOfNestedTable.addParagraph();
                        cellOfNestedTable.removeParagraph(0);
                        XWPFRun answerRun = null;
                        String answerParseForm = choice + ". " + answerContent.get(j);
                        parseTextWithMathML(para, answerRun, answerParseForm);
                    }
                    if (type == 3) {
                        nestedTableRow = nestedTable.createRow();
                        if (j == 0) {
                            cellOfNestedTable = nestedTableRow.createCell();
                        } else {
                            cellOfNestedTable = nestedTableRow.getCell(0);
                        }
                        cellOfNestedTable.setWidth("9000");
                        XWPFParagraph para = cellOfNestedTable.addParagraph();
                        cellOfNestedTable.removeParagraph(0);
                        XWPFRun answerRun = null;
                        String answerParseForm = choice + "." + answerContent.get(j);
                        parseTextWithMathML(para, answerRun, answerParseForm);
                    }
                }
                answerContent.clear();
            }
            doc.write(fos);
            fos.close();
            System.out.println("done");
        } catch (Exception e) {
            Logger.getLogger(TestToWordService.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                System.out.println("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            System.out.println("File not found " + fileName);
        }
        return null;
    }

}
