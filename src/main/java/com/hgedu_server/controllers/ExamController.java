/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hgedu_server.controllers;

import com.hgedu_server.models.AnswerOption;
import com.hgedu_server.models.ClassStudent;
import com.hgedu_server.models.Exam;
import com.hgedu_server.models.ExamProgress;
import com.hgedu_server.models.ExamResult;
import com.hgedu_server.models.ExamTest;
import com.hgedu_server.models.Grade;
import com.hgedu_server.models.Question;
import com.hgedu_server.models.QuestionDetail;
import com.hgedu_server.models.Test;
import com.hgedu_server.services.ClassStudentService;
import com.hgedu_server.services.ExamResultService;
import com.hgedu_server.services.ExamService;
import com.hgedu_server.services.ExamTestService;
import com.hgedu_server.services.GradeService;
import com.hgedu_server.services.QuestionService;
import com.hgedu_server.services.StorageService;
import com.hgedu_server.services.TestService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.opencv.core.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.CLAHE;

/**
 *
 * @author Administrator
 */
@RestController
public class ExamController {
    @Autowired
    private ExamService examService;
    
    @Autowired
    private ExamResultService examResultService;
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private ExamTestService examTestService;
    
    @Autowired
    private GradeService gradeService;
    
    @Autowired
    private ClassStudentService classStudentService;
    
    @Autowired
    private TestService testService;
    
    @Autowired
    private StorageService storageService;
    
    @PostMapping("/api/exam") 
    public ResponseEntity<?> createExam(@Valid @RequestBody Exam exam) {
        Exam createdExam = examService.createExam(exam);
        return ResponseEntity.ok(createdExam);
    }
    
    @PutMapping("/api/exam") 
    public ResponseEntity<?> updateExam(@Valid @RequestBody Exam exam) {
        Exam updatedExam = examService.createExam(exam);
        return ResponseEntity.ok(updatedExam);
    }
    
    @GetMapping("/api/exam/{examId}/progress") 
    public ResponseEntity<?> getProgress(@PathVariable("examId") Long examId) {
        String progress = examService.getExamProgress(examId);
        return ResponseEntity.ok(progress);
    }
    
    //@GetMapping("/api/exam/imageProcess") 
    public String processImage(String imageFilePath, Map<Integer, Integer> testQuestionMap) {
        Mat grayImage = new Mat();
        Mat detectedEdges = new Mat();
        Mat threshOuput = new Mat();
        Mat bitwiseOuput = new Mat();
        Mat cannyEdges = new Mat();
        Mat morphoImg = new Mat();
        
        Mat originalMat = Imgcodecs.imread(imageFilePath);
        Imgproc.cvtColor(originalMat, grayImage, Imgproc.COLOR_BGR2GRAY);
        // reduce noise with a 3x3 kernel
        Imgproc.GaussianBlur(grayImage, detectedEdges, new Size(3, 3),0);
        //threshold
        Imgproc.adaptiveThreshold(detectedEdges,threshOuput,255,Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY,75,10);
        //bitwise_not
        Core.bitwise_not(threshOuput, bitwiseOuput);
        //find answer blocks
        List<MatOfPoint> answerBlocks = getAnswerBlocks(bitwiseOuput);
        if(answerBlocks.size() == 4) {
            Map<Integer, List<Integer>> allBlockAnswers = new HashMap<>();
            System.out.println("point " + answerBlocks.get(0).get(0, 0)[0]);
            //sort answer blocks
            Collections.sort(answerBlocks, (MatOfPoint mat1, MatOfPoint mat2) -> {
                double xValueMat1 = mat1.get(0, 0)[0];
                double xValueMat2 = mat2.get(0, 0)[0];
                if (Double.compare(xValueMat1, xValueMat2) > 0)
                    return 1;
                if (Double.compare(xValueMat1, xValueMat2) < 0)
                    return -1;
                return 0;
            });
            int count = 0;
            for(MatOfPoint block: answerBlocks) {
                Mat blockGray = new Mat();
                Mat appliedClahe = new Mat();
                Mat blurred = new Mat();
                Mat thresh = new Mat();
                Mat bitwise = new Mat();
                Mat croppedBlock = cropImage(originalMat, block);
                //Imgcodecs.imwrite("C:/Users/Administrator/Pictures/test4_procressed" + count++ + ".jpg", croppedBlock);
                List<MatOfPoint> questionList = new ArrayList<>();
                //block to grayscale
                Imgproc.cvtColor(croppedBlock, blockGray, Imgproc.COLOR_BGR2GRAY);
                //apply CLAHE (Contrast Limited Adaptive Histogram Equalization) to increate brightness
                CLAHE clahe = Imgproc.createCLAHE(2.0, new Size(8, 8));
                clahe.apply(blockGray, appliedClahe);
                //apply Gaussian Blur
                Imgproc.GaussianBlur(appliedClahe, blurred, new Size(3, 3),0);
                //threshold
                Imgproc.adaptiveThreshold(blurred,thresh,255,Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY,75,10);
                //bitwise_not
                Core.bitwise_not(thresh, bitwise);
             
                //get question list
                questionList = getQuestions(thresh);
                if(questionList.size() >= 120) {
                    questionList = questionList.subList(0, 120);
                    //get center point of answer contour
                    List<Point> answerContourCenter = getContourCenter(questionList);
                    answerContourCenter = sortPointByY(answerContourCenter, Boolean.FALSE);
                    //get question map
                    Map<Integer, List<Point>> questionMap = getQuestionMap(answerContourCenter);
                    //get list of chosen answers
                    List<Point> chosenAnswers = getChosenAnswers(croppedBlock, count);
                    //sort chosen answers
                    chosenAnswers = sortPointByY(chosenAnswers, Boolean.FALSE);
                    //get answers index map
                    //get data
                    Map<Integer, List<Integer>> answerMap = getAnswerMap(questionMap, chosenAnswers);
                    for(Integer x: answerMap.keySet()) {
                        //System.out.println("x: " + x);
                        //System.out.println("size: " + answerMap.get(x).size());
                        //turn all block answer from 1-30 to 1-120 question index
                        allBlockAnswers.put(x + 30 * count, answerMap.get(x));
                        answerMap.get(x).forEach(item -> 
                               System.out.println("q" + x + " :" + item)
                        );
                    }
                } else {
                    return "Failed";
                }
                count++;
            }
            //get mark
            for(Integer key: allBlockAnswers.keySet()) {
                System.out.println("question " + key + ": ");
                for(Integer answer: allBlockAnswers.get(key)) {
                    System.out.print(answer + ", ");
                }
            }
            float sheetMark = getSheetMark(testQuestionMap, allBlockAnswers);
            return String.valueOf(sheetMark);
        } else {
            return "Failed";
        }
        // canny detector
//        Imgproc.Canny(detectedEdges, cannyEdges, 10, 20);
//        Mat kernel = new Mat(new Size(3, 3), CvType.CV_8UC1, new Scalar(255));
//        Imgproc.morphologyEx(cannyEdges, morphoImg, Imgproc.MORPH_CLOSE, kernel);
//        Imgcodecs.imwrite("C:/Users/Administrator/Pictures/test4_procressed.jpg", cannyEdges);
        //finding contours
        
        //Drawing contours on a new image
//        Mat contours = new Mat();
//        contours.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC3);
//        Random r = new Random();
//        for (int i = 0; i < contourList.size(); i++) {
//            Imgproc.drawContours(contours, contourList, i, new Scalar(r.nextInt(255), r.nextInt(255), r.nextInt(255)), -1);
//        }


//        Imgproc.drawContours(originalMat, answerRegions, 3, new Scalar(0,255,0), 2);
//        Imgcodecs.imwrite("C:/Users/Administrator/Pictures/test4_procressed.jpg", cannyEdges);
//        return "OK";
    }
    
    private float getSheetMark(Map<Integer, Integer> testQuestionMap, Map<Integer, List<Integer>> allBlockAnswers) {
        Integer numberOfRightAnswers = 0;
        for(int questionIndex = 1; questionIndex < testQuestionMap.size(); questionIndex++ ) {
            List<Integer> questionAnswers = allBlockAnswers.get(questionIndex);
            if(!questionAnswers.isEmpty() && questionAnswers.size() == 1) {
                Integer answerIndex = questionAnswers.get(0);
                if(Objects.equals(answerIndex, testQuestionMap.get(questionIndex))) {
                    numberOfRightAnswers++;
                }
            }
        }
        System.out.println("Right answers: " + numberOfRightAnswers);
        return ((float)numberOfRightAnswers)*10/testQuestionMap.keySet().size();
    }
    
    private List<Point> getChosenAnswers(Mat inputImage, Integer index) {
        Mat mask = new Mat();
        
        Scalar lowerBound = new Scalar(0, 0, 10);
        Scalar upperBound = new Scalar(255,255,165);
        
        Core.inRange(inputImage, lowerBound, upperBound, mask);
        
        Mat kernel = Mat.ones(3, 3, CvType.CV_8U);
        Imgproc.erode(mask, mask, kernel, new Point(-1,-1), 6);
        Imgproc.dilate(mask, mask, kernel, new Point(-1,-1), 3);
        
        Mat hierarchy = new Mat();
        List<MatOfPoint> contourList = new ArrayList<>(); //A list to store all the contours
        Imgproc.findContours(mask.clone(), contourList, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        
        MatOfPoint2f[] contoursPoly  = new MatOfPoint2f[contourList.size()];
        List<MatOfPoint> contoursPolyList = new ArrayList<>(contoursPoly.length);
        List<Point> chosenAnswerCenterList = new ArrayList<>();
        Rect[] boundRect = new Rect[contourList.size()];
        Point[] centers = new Point[contourList.size()];
        float[][] radius = new float[contourList.size()][1];
        for(int i = 0; i < contourList.size(); i++) {
            contoursPoly[i] = new MatOfPoint2f();
            Imgproc.approxPolyDP(new MatOfPoint2f(contourList.get(i).toArray()), contoursPoly[i], 3, true);
            boundRect[i] = Imgproc.boundingRect(new MatOfPoint(contoursPoly[i].toArray()));
            centers[i] = new Point();
            Imgproc.minEnclosingCircle(contoursPoly[i], centers[i], radius[i]);
            contoursPolyList.add(new MatOfPoint(contoursPoly[i].toArray()));
            if(Float.compare(radius[i][0], 8) >= 0  && Float.compare(radius[i][0], 15) <= 0) {
                chosenAnswerCenterList.add(centers[i]);
            }
        }
        
//        Imgproc.drawContours(inputImage, contoursPolyList, 0, new Scalar(0,255,0), 2);
//        Imgcodecs.imwrite("C:/Users/Administrator/Pictures/test_procressed" + index + ".jpg", inputImage);
        
        return chosenAnswerCenterList;
    }
    
    private Map<Integer, List<Point>> getQuestionMap(List<Point> answerCenterList) {
        Integer numberOfQuestions = 1;
        Integer coeff = 0;
        Map<Integer, List<Point>> questionMap = new HashMap<>();
        while(numberOfQuestions <= 30) {
            List<Point> sortedAnswerPoints = sortPointByX(answerCenterList.subList(coeff, coeff+4), Boolean.FALSE);
            questionMap.put(numberOfQuestions, sortedAnswerPoints);
            coeff += 4;
            numberOfQuestions++;
        }
//        for(Point p: questionMap.get(1)) {
//            System.out.println("dap an " + p.x + " " + p.y);
//        }
        return questionMap;
    }
    
    private Map<Integer, List<Integer>> getAnswerMap(Map<Integer, List<Point>> questionMap, List<Point> answerList) {
        //initialize answers map
        Map<Integer, List<Integer>> answerMap = new HashMap<>();
        Integer numberOfQuestion = 1;
        while(numberOfQuestion <= 30) {
            answerMap.put(numberOfQuestion, new ArrayList<>());
            numberOfQuestion++;
        }
        //get data
        for(Point answer: answerList) {
            for(Integer key: questionMap.keySet()) {
                //System.out.println(">>>>>>>>there");
                Integer counter = 0;
                Boolean isNext = true;
                while(counter < questionMap.get(key).size()) {
                    //System.out.println(key + " :" + Math.abs(answer.y - questionMap.get(key).get(counter).y));
                    //System.out.println(key + " :" + Math.abs(answer.x - questionMap.get(key).get(counter).x));
                    if(Math.abs(answer.y - questionMap.get(key).get(counter).y) <= 10 && 
                            Math.abs(answer.x - questionMap.get(key).get(counter).x) <= 10) {
                        //System.out.println("counter " + counter);
                        answerMap.get(key).add(counter);
                        isNext = false;
                        break;
                    }
                    counter++;
                }
                if(isNext == false) {
                    break;
                }
            }
        }
        return answerMap;
    }
    
    private Mat cropImage(Mat inputImage, MatOfPoint boundingContour) {
        //Find the bounding rectangle of a selected contour
        Rect rect = Imgproc.boundingRect(boundingContour);
        Mat ROI = inputImage.submat(rect.y, rect.y + rect.height, rect.x + 65, rect.x + rect.width);
        return ROI;
    }
    
    private List<MatOfPoint> getAnswerBlocks(Mat inputImage) {
        Mat hierarchy = new Mat();
        List<MatOfPoint> contourList = new ArrayList<>(); //A list to store all the contours
        Imgproc.findContours(inputImage, contourList, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Collections.sort(contourList, (MatOfPoint mat1, MatOfPoint mat2) -> {
            double contourAreaMat1 = Imgproc.contourArea(mat1);
            double contourAreaMat2 = Imgproc.contourArea(mat2);
            if (Double.compare(contourAreaMat1, contourAreaMat2) > 0)
                return 1;
            if (Double.compare(contourAreaMat1, contourAreaMat2) < 0)
                return -1;
            return 0;
        });
        Collections.reverse(contourList);
        
        List<MatOfPoint> answerRegions = new ArrayList<>();
        for(MatOfPoint contour: contourList) {
            MatOfPoint2f newMat1 = new MatOfPoint2f(contour.toArray());
            MatOfPoint2f newMat2 = new MatOfPoint2f();
            double peri = Imgproc.arcLength(newMat1,true);
            Imgproc.approxPolyDP(newMat1, newMat2,0.02*peri,true);
        
            if(answerRegions.size() < 4 && newMat2.toList().size() == 4) {
                newMat2.convertTo(contour, CvType.CV_32S);
                answerRegions.add(contour);
            } else {
                break;
            }
        }
        return answerRegions;
    }
    
    private List<MatOfPoint> getQuestions(Mat inputImage) {
        Mat hierarchy = new Mat();
        List<MatOfPoint> allContourList = new ArrayList<>(); //A list to store all the contours
        Imgproc.findContours(inputImage, allContourList, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        allContourList = sortContoursByArea(allContourList, Boolean.TRUE);
        
        MatOfPoint2f[] contoursPoly  = new MatOfPoint2f[allContourList.size()];
        List<MatOfPoint> contoursPolyList = new ArrayList<>(contoursPoly.length);
        Rect[] boundRect = new Rect[allContourList.size()];
        Point[] centers = new Point[allContourList.size()];
        float[][] radius = new float[allContourList.size()][1];
        for(int i = 0; i < allContourList.size(); i++) {
            contoursPoly[i] = new MatOfPoint2f();
            Imgproc.approxPolyDP(new MatOfPoint2f(allContourList.get(i).toArray()), contoursPoly[i], 3, true);
            boundRect[i] = Imgproc.boundingRect(new MatOfPoint(contoursPoly[i].toArray()));
            centers[i] = new Point();
            Imgproc.minEnclosingCircle(contoursPoly[i], centers[i], radius[i]);
            if(Float.compare(radius[i][0], 15) >= 0  && Float.compare(radius[i][0], 21) <= 0) {
                contoursPolyList.add(new MatOfPoint(contoursPoly[i].toArray()));
            }
        }
        
        contoursPolyList = sortContoursByArea(contoursPolyList, Boolean.TRUE);
        
        return contoursPolyList;
    }
    
    private List<Point> getContourCenter(List<MatOfPoint> contourList) {
        MatOfPoint2f[] contoursPoly  = new MatOfPoint2f[contourList.size()];
        Rect[] boundRect = new Rect[contourList.size()];
        Point[] centers = new Point[contourList.size()];
        float[][] radius = new float[contourList.size()][1];
        for(int i = 0; i < contourList.size(); i++) {
            contoursPoly[i] = new MatOfPoint2f();
            Imgproc.approxPolyDP(new MatOfPoint2f(contourList.get(i).toArray()), contoursPoly[i], 3, true);
            boundRect[i] = Imgproc.boundingRect(new MatOfPoint(contoursPoly[i].toArray()));
            centers[i] = new Point();
            Imgproc.minEnclosingCircle(contoursPoly[i], centers[i], radius[i]);
        }
        return Arrays.asList(centers);
    }
    
    private List<MatOfPoint> sortContoursByArea(List<MatOfPoint> contourList, Boolean reverse) {
        Collections.sort(contourList, (MatOfPoint mat1, MatOfPoint mat2) -> {
            double contourAreaMat1 = Imgproc.contourArea(mat1);
            double contourAreaMat2 = Imgproc.contourArea(mat2);
            if (Double.compare(contourAreaMat1, contourAreaMat2) > 0)
                return 1;
            if (Double.compare(contourAreaMat1, contourAreaMat2) < 0)
                return -1;
            return 0;
        });
        if(reverse == true) {
            Collections.reverse(contourList);
        }
        return contourList;
    }
    
    private List<Point> sortPointByY(List<Point> pointList, Boolean reverse) {
        Collections.sort(pointList, (Point p1, Point p2) -> {
            if (Double.compare(p1.y, p2.y) > 0)
                return 1;
            if (Double.compare(p1.y, p2.y) < 0)
                return -1;
            return 0;
        });
        if(reverse == true) {
            Collections.reverse(pointList);
        }
        return pointList;
    }
    
    private List<Point> sortPointByX(List<Point> pointList, Boolean reverse) {
        Collections.sort(pointList, (Point p1, Point p2) -> {
            if (Double.compare(p1.x, p2.x) > 0)
                return 1;
            if (Double.compare(p1.x, p2.x) < 0)
                return -1;
            return 0;
        });
        if(reverse == true) {
            Collections.reverse(pointList);
        }
        return pointList;
    }
    
    @GetMapping("/api/omr/{teacherEmail}/{testCode}")
    public ResponseEntity<?> getTestForOMR(@PathVariable("teacherEmail") String teacherEmail, @PathVariable("testCode") String testCode) {
        Test test = testService.getTestForOMR(testCode, teacherEmail);
        if(test == null) {
            return ResponseEntity.ok("Either test code or email is wrong!");
        }
        return ResponseEntity.ok(test);
    }
    
    @PostMapping("/api/omr/{testId}")
    public ResponseEntity<?> markTestForOMR(@PathVariable("testId") Long testId, @RequestBody Map<String, String> imageLink) {
        try {
            System.out.println(">>>>>>>running here");
            Map<Integer, Integer> testQuestionMap = questionService.getQuestionMapForOMR(testId);
            System.out.println("size: " + testQuestionMap.keySet().size());
            String[] imageStrArr = imageLink.get("image").split("/");
            String imageFileName = imageStrArr[imageStrArr.length-1];
            System.out.println("File name: " + imageFileName);
            String imageFilePath = storageService.loadAsResource(imageFileName).getFile().getPath();
            System.out.println("File path: " + imageFilePath);
            String result = processImage(imageFilePath, testQuestionMap);
            if(result.equals("failed")) {
                return ResponseEntity.ok("failed");
            } else if(result.equals("OK")) {
                return ResponseEntity.ok("OK");
            }
            return ResponseEntity.ok(result);
        } catch (IOException ex) {
            Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.ok("failed");
        }
    }
    
    @GetMapping("/api/omr/{testId}/test")
    public ResponseEntity<?> markTestForOMR_Test(@PathVariable("testId") Long testId){ //, @RequestBody String imageLink) {
        try {
            //Map<Integer, Integer> testQuestionMap = questionService.getQuestionMapForOMR(testId);
            Map<Integer, Integer> testQuestionMap = new HashMap<>();
            testQuestionMap.put(1, 2);
            testQuestionMap.put(2, 0);
            testQuestionMap.put(3, 1);
            testQuestionMap.put(4, 1);
            testQuestionMap.put(5, 3);
            
            testQuestionMap.put(6, 2);
            testQuestionMap.put(7, 1);
            testQuestionMap.put(8, 3);
            testQuestionMap.put(9, 0);
            testQuestionMap.put(10, 1);
            
            testQuestionMap.put(11, 1);
            testQuestionMap.put(12, 3);
            testQuestionMap.put(13, 1);
            testQuestionMap.put(14, 2);
            testQuestionMap.put(15, 2);
            
            testQuestionMap.put(16, 2);
            testQuestionMap.put(17, 2);
            testQuestionMap.put(18, 2);
            testQuestionMap.put(19, 2);
            testQuestionMap.put(20, 2);
            
            testQuestionMap.put(21, 2);
            testQuestionMap.put(22, 2);
            testQuestionMap.put(23, 2);
            testQuestionMap.put(24, 2);
            testQuestionMap.put(25, 2);
            
            testQuestionMap.put(26, 2);
            testQuestionMap.put(27, 2);
            testQuestionMap.put(28, 2);
            testQuestionMap.put(29, 2);
            testQuestionMap.put(30, 2);
            String imageLink = "http://localhost:8084/files/test5.jpg";
            String[] imageStrArr = imageLink.split("/");
            String imageFileName = imageStrArr[imageStrArr.length-1];
            String imageFilePath = storageService.loadAsResource(imageFileName).getFile().getPath();
            String result = processImage(imageFilePath, testQuestionMap);
            if(result.equals("failed")) {
                return ResponseEntity.ok("failed");
            } else if(result.equals("OK")) {
                return ResponseEntity.ok("OK");
            }
            return ResponseEntity.ok(result);
        } catch (IOException ex) {
            Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.ok("failed");
        }
    }
    
    @GetMapping("/api/omr/{testId}")
    public ResponseEntity<?> getAnswersForOMR(@PathVariable("testId") Long testId) {
        Map<Integer, Integer> testQuestionMap = questionService.getQuestionMapForOMR(testId);
        return ResponseEntity.ok(testQuestionMap);
    }
    
    @GetMapping("/api/doExam/{examId}/{userId}/{classId}/{nthTrial}")
    public ResponseEntity<?> getTestForExam(@PathVariable("examId") Long examId, @PathVariable("userId") Long userId, @PathVariable("classId") Long classId, @PathVariable("nthTrial") Long nthTrial) {
        //check if no trial left
        System.out.println("O day");
        ClassStudent classStudent = classStudentService.getByClassIdAndStudentId(classId, userId);
        Exam exam = examService.getExamById(examId);
        List<QuestionDetail> questionMap = new ArrayList<>();
        if(exam != null && classStudent != null){
            Long currentTrial = examResultService.getNthTrial(examId, classStudent.getId());
            if(Objects.equals(currentTrial, exam.getTrials())) {
                return ResponseEntity.ok("Hết lượt");
            } else {
                //get randome test id for exam
                ExamTest examTest = examTestService.getRandomTestIdForExam(examId);
                if(examTest != null) {
                    //create new exam result
                    ExamResult result = new ExamResult();
                    result.setExamTestId(examTest.getId());
                    result.setStartedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.forLanguageTag("vi")).format(new Date()));
                    result.setIsCompleted(Boolean.FALSE);
                    result.setIsResetted(Boolean.FALSE);
                    result.setMark(0);
                    result.setNthTrial((currentTrial == null) ? 1 : currentTrial + 1);
                    result.setClassStudentId(classStudent.getId());
                    examResultService.createNewExamResult(result);
                    //get questions for test
                    questionMap = questionService.getQuestionsForTestRandomList(examTest.getTestId());
                }
            }
        }
        return ResponseEntity.ok(questionMap);
    }
    
    @PostMapping("/api/doExam/{examId}/{classId}/{userId}")
    public ResponseEntity<?> SubmitTest(@PathVariable("examId") Long examId, @PathVariable("classId") Long classId, @PathVariable("userId") Long userId, @RequestBody Map<Long, Long> studentAnswers) {
        //get examResultId
        ClassStudent classStudent = classStudentService.getByClassIdAndStudentId(classId, userId);
        Exam exam = examService.getExamById(examId);
        if(classStudent == null || exam == null) {
            
        } else {
            ExamResult examResult = examResultService.getExamResultForTest(examId, classStudent.getId());
            if(examResult == null) {
                
            } else {
                try {
                    ExamTest examTest = examTestService.getExamTestById(examResult.getExamTestId());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentTime = formatter.format(new Date());
                    Date completedTime = formatter.parse(currentTime);
                    Date startedTime = formatter.parse(examResult.getStartedTime());
                    long diffInMillies = Math.abs(completedTime.getTime() - startedTime.getTime());
                    long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    double statistic = (double)diff/exam.getDuration();
                    if(Double.compare(statistic, 1.5) > 0) {

                    } else {
                        float mark = questionService.getTestMark(studentAnswers, examTest.getTestId());
                        examResult.setCompletedTime(currentTime);
                        examResult.setIsCompleted(Boolean.TRUE);
                        examResult.setMark(mark);
                        examResultService.updateExamResult(examResult);
                        if(exam.getIsMarked()) {
                            Grade studentGrade = gradeService.getStudentGradeByClassStudentIdAndPowerLevel(examResult.getClassStudentId(), exam.getPowerLevel());
                            if(studentGrade != null) {
                                String gradeValue = studentGrade.getValue();
                                if(gradeValue != null && !gradeValue.trim().equalsIgnoreCase("")) {
                                    gradeValue = gradeValue.concat(", " + examResult.getMark());
                                } else {
                                    gradeValue = String.valueOf(examResult.getMark());
                                }
                                studentGrade.setValue(gradeValue);
                                gradeService.updateGrade(studentGrade);
                            }
                        }
                        return ResponseEntity.ok(mark);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return ResponseEntity.ok("OK");
    }
    
    @GetMapping("/api/answers/{questionId}") 
    public ResponseEntity<?> getAnswers(@PathVariable("questionId") Long questionId) {
        Set<String> answers = new LinkedHashSet<>();
        answers.add("1285");
        answers.add("1283");
        answers.add("1284");
        List<AnswerOption> result = questionService.getAnswersBySpecificOrder(questionId, answers);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/api/tests/{testId}/content") 
    public ResponseEntity<?> getQuestionsForTest(@PathVariable("testId") Long testId) {
        List<QuestionDetail> result = questionService.getQuestionsForTestRandomList(testId);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/api/exam/class/{classId}/all")
    public ResponseEntity<?> getExamsForClass(@PathVariable("classId") Long classId) {
        List<Exam> examList = examService.getExamsForClass(classId);
        return ResponseEntity.ok(examList);
    }
    
    @GetMapping("/api/exam/{examId}")
    public ResponseEntity<?> getExam(@PathVariable("examId") Long examId) {
        Exam exam = examService.getExamById(examId);
        return ResponseEntity.ok(exam);
    }
    
    @GetMapping("/api/exam/schedule/{teacherId}/all") 
    public ResponseEntity<?> getExamSchedule(@PathVariable("teacherId") Long teacherId) {
        Map<String, List<ExamProgress>> examMap = examService.getExamSchedule(teacherId);
        return ResponseEntity.ok(examMap);
    }
    
    @GetMapping("/api/exam/history/{teacherId}/all") 
    public ResponseEntity<?> getExamHistory(@PathVariable("teacherId") Long teacherId) {
        Map<String,List<ExamProgress>> examMap = examService.getExamHistory(teacherId);
        return ResponseEntity.ok(examMap);
    }
    
    @DeleteMapping("/api/exam/{id}")
    public ResponseEntity deleteExam(@PathVariable("id") Long id) {
      try {
        examService.deleteExam(id);
      } catch (Exception e) {
        return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
      }

      return new ResponseEntity<>("Exam has been deleted!", HttpStatus.OK);
    }
}
