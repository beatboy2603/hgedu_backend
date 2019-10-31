/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.services;

import com.hgedu_server.models.TestToWord;
import com.hgedu_server.repositories.TestToWordRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

    @Autowired
    private TestToWordRepository testToWordRepository;

    public List<String> getAllContent() {
        return testToWordRepository.getAllContent();
    }

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

        //for making this to work with Office 2007 Word also, special font settings are necessary
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
                    runLatex.setText(s + " ");
                }
            }
        } catch (Exception ex) {
            System.out.println("Loi parse");
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

    public TestToWord saveContent() {
        String json = convertToJson();
        TestToWord testToWord = new TestToWord();
        testToWord.setContent(json);
        return testToWordRepository.save(testToWord);   
    }

    public String convertToJson() {
        FileInputStream input;
        try {
            input = new FileInputStream(new File("/Users/admin/Desktop/readexcel1.xlsx"));

            XSSFWorkbook workbook = new XSSFWorkbook(input);
            Sheet sheet0 = workbook.getSheetAt(0);
            Iterator<Row> iteratorRow = sheet0.iterator();
            String regex = "<[a-z][a-z]>";
            String dummy = "<ct>aaf</ct>";
            String dummy1 = "asfasf";

            String[] s1 = null;
            String[] s2 = null;
            ArrayList<String> arr = new ArrayList<>();
            ArrayList<String> arr1 = new ArrayList<>();
            int count = 0;

            String debai = "";
            String json = "[";
            String wrapFormular = "{\"insert\":{\"formula\":\"";
            String wrapText = "{\"insert\":\"";
            String debai2 = "";

            while (iteratorRow.hasNext()) {
                Row row = iteratorRow.next();
                Iterator<Cell> iteratorCell = row.cellIterator();
                while (iteratorCell.hasNext()) {
                    Cell cell = iteratorCell.next();
                    debai = cell.getStringCellValue();
                    s1 = debai.split(regex);
                    for (int k = 0; k < s1.length; k++) {
                        if (k == 0) {
                            json += wrapText + s1[0].trim() + "\"},";
                        }
                        if (s1[k].contains("</ct>")) {
                            s2 = s1[k].split("</ct>");

                            json += wrapFormular + s2[0].trim() + "\"}},";
                            json += wrapText + s2[1].trim() + "\"},";
                        }
                    }
                }
            }
            json += "]";
            return json; 
        } catch (Exception ex) {
            Logger.getLogger(TestToWordService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public void formatWord() {
        try {
            String qResult = "";
            String aResult = "";
// export
// question            

// answer q1           
            String a1 = "[{ \"insert\": \"Chuong\"},{ \"insert\": \"án1\"}]";
            String a2 = "[{ \"insert\": \"Đá\"},{ \"insert\": \"án2\"}]";
            String a3 = "[{ \"insert\": \"Đá\"},{ \"insert\": \"án3\"}]";
            String a4 = "[{ \"insert\": \"Đá\"},{ \"insert\": \"án4\"}]";
            // question list           
            ArrayList<String> answers = new ArrayList<>();
            answers.add(a1);
            answers.add(a2);
            answers.add(a3);
            answers.add(a4);
// answer q2            

// question list            
            List<String> questions = getAllContent();

// output $ list         
            ArrayList<String> outputq = new ArrayList<>();
            ArrayList<String> outputa = new ArrayList<>();

            for (int i = 0; i < answers.size(); i++) {
                JSONParser parser = new JSONParser();
                JSONArray jsonArr = (JSONArray) parser.parse(answers.get(i));
                for (int j = 0; j < jsonArr.size(); j++) {
                    JSONObject object = (JSONObject) jsonArr.get(j);
                    if (object.get("insert") instanceof String) {
//                    System.out.println(object.get("insert"));
                        aResult += object.get("insert");
                    } else {
//                    System.out.println(((JSONObject) object.get("insert")).get("formula"));
                        String formula = " $" + ((JSONObject) object.get("insert")).get("formula") + "$ ";
                        aResult += formula;
                    }

                }
                outputa.add(aResult);
                aResult = "";
            }

            for (int i = 0; i < outputa.size(); i++) {
                System.out.println(outputa.get(i));
            }

            for (int i = 0; i < questions.size(); i++) {
                JSONParser parser = new JSONParser();
                JSONArray jsonArr = (JSONArray) parser.parse(questions.get(i));


                for (int j = 0; j < jsonArr.size(); j++) {
                    JSONObject object = (JSONObject) jsonArr.get(j);
                    if (object.get("insert") instanceof String) {
//                    System.out.println(object.get("insert"));
                        qResult += object.get("insert");
                    } else {
//                    System.out.println(((JSONObject) object.get("insert")).get("formula"));
                        String formula = " $" + ((JSONObject) object.get("insert")).get("formula") + "$ ";
                        qResult += formula;
                    }

                }
                outputq.add(qResult);
                qResult = "";
            }

            XWPFDocument doc = new XWPFDocument();
            FileOutputStream fos = new FileOutputStream(new File("formatwordtest1.docx"));
// Export
            XWPFTable table = doc.createTable(1, 2);
            table.setWidth(10000);
            XWPFTableRow tableRowOne = table.getRow(0);

            tableRowOne.getCell(0).setWidth("1000");
            XWPFParagraph paragraph0 = tableRowOne.getCell(0).addParagraph();
            setRun(paragraph0.createRun(), "Verdana", 10, "000000", "Câu", true, false);
            tableRowOne.getCell(0).removeParagraph(0);

            tableRowOne.getCell(1).setWidth("9000");
            XWPFParagraph paragraph1 = tableRowOne.getCell(1).addParagraph();
            setRun(paragraph1.createRun(), "Verdana", 10, "000000", "Nội dung", true, false);
            tableRowOne.getCell(1).removeParagraph(0);

            for (int i = 0; i < outputq.size(); i++) {

                XWPFTableRow tableRow = table.createRow();

                XWPFParagraph questionPara0 = tableRow.getCell(0).addParagraph();
                setRun(questionPara0.createRun(), "Verdana", 10, "000000", "Câu " + (i + 1), true, false);
                tableRow.getCell(0).removeParagraph(0);

// lấy câu hỏi cho vào bảng 
                XWPFParagraph questionPara1Q = tableRow.getCell(1).addParagraph();
                XWPFRun questionRun = null;
                String parseForm = outputq.get(i);
//                questionRun.setText(outputq.get(i));
                parseTextWithMathML(questionPara1Q, questionRun, parseForm);

                XWPFParagraph answerPara = tableRow.getCell(1).addParagraph();
                tableRow.getCell(1).removeParagraph(0);

// Xử lý câu hỏi 
                int type = 0;
                for (String outputItem : outputa) {
                    if (outputItem.length() < 10 && type <= 0) {
                        type = 1;
                    }
                    if (outputItem.length() >= 10 && outputa.get(i).length() < 25 && type <= 1) {
                        type = 2;
                    }
                    if (outputItem.length() >= 25 && type <= 2) {
                        type = 3;
                    }
                }

                XmlCursor cursor = answerPara.getCTP().newCursor();
                XWPFTable nestedTable = answerPara.getBody().insertNewTbl(cursor);
// nestedTable               
                XWPFTableRow nestedTableRow = null;
                XWPFTableCell cellOfNestedTable;

                for (int j = 0; j < outputa.size(); j++) {
                    String choice = "";
                    switch (j) {
                        case 0:
                            choice = "A";
                            break;
                        case 1:
                            choice = "B";
                            break;
                        case 2:
                            choice = "C";
                            break;
                        case 3:
                            choice = "D";
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
                        XWPFRun run = para.createRun();
                        run.setText(choice + ":" + outputa.get(j));
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
                        XWPFRun run = para.createRun();
                        run.setText(choice + ":" + outputa.get(j));
//                        setRun(para.createRun(), "Verdana", 10, "000000", choice + ": " + answersParts[order], false, false);
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
                        XWPFRun run = para.createRun();
                        run.setText(choice + ":" + outputa.get(j));
//                        setRun(para.createRun(), "Verdana", 10, "000000", choice + ": " + answersParts[order], false, false);
                    }
                }
            }

            table.removeRow(0);

// Close
            doc.write(fos);
            fos.close();
            System.out.println("done");

        } catch (Exception ex) {
            Logger.getLogger(TestToWordService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
