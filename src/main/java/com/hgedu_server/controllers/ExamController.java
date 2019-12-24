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
import com.hgedu_server.services.ClassStudentService;
import com.hgedu_server.services.ExamResultService;
import com.hgedu_server.services.ExamService;
import com.hgedu_server.services.ExamTestService;
import com.hgedu_server.services.GradeService;
import com.hgedu_server.services.QuestionService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
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
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

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
    
    @GetMapping("/api/exam/imageProcess") 
    public void processImage() {
        Mat grayImage = new Mat();
        Mat detectedEdges = new Mat();
        Mat cannyEdges = new Mat();
        Mat morphoImg = new Mat();
        Mat hierarchy = new Mat();

        List<MatOfPoint> contourList = new ArrayList<MatOfPoint>(); //A list to store all the contours
        
        Mat originalMat = Imgcodecs.imread("C:/Users/Administrator/Pictures/test2.jpg");
        Imgproc.cvtColor(originalMat, grayImage, Imgproc.COLOR_BGR2GRAY);
        // reduce noise with a 3x3 kernel
        Imgproc.blur(grayImage, detectedEdges, new Size(5, 5));
        // canny detector
        Imgproc.Canny(detectedEdges, cannyEdges, 10, 20);
        Mat kernel = new Mat(new Size(3, 3), CvType.CV_8UC1, new Scalar(255));
        Imgproc.morphologyEx(cannyEdges, morphoImg, Imgproc.MORPH_CLOSE, kernel);
        Imgcodecs.imwrite("C:/Users/Administrator/Pictures/test4_procressed.jpg", cannyEdges);
        //finding contours
        Imgproc.findContours(morphoImg, contourList, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        //Drawing contours on a new image
//        Mat contours = new Mat();
//        contours.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC3);
//        Random r = new Random();
//        for (int i = 0; i < contourList.size(); i++) {
//            Imgproc.drawContours(contours, contourList, i, new Scalar(r.nextInt(255), r.nextInt(255), r.nextInt(255)), -1);
//        }
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
        System.out.println(answerRegions.size());
        Imgproc.drawContours(originalMat, answerRegions, 3, new Scalar(0,255,0), 2);
        Imgcodecs.imwrite("C:/Users/Administrator/Pictures/test4_procressed.jpg", cannyEdges);
//blurred = cv2.GaussianBlur(gray, (11, 11), 2)
//edged = cv2.Canny(blurred, 10, 20)
//#out = get_circles(image);
//#plt.imshow(edged)
//out = extract_color(image)
//
//#th = cv2.adaptiveThreshold(blurred, 255, cv2.THRESH_BINARY, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, 115, 1)
//th = cv2.threshold(blurred, 255, cv2.THRESH_BINARY, 115, 1)
//ret,thresh1 = cv2.threshold(image,160,255,cv2.THRESH_TRUNC)
//kernel = np.ones((5,5),np.uint8)
//opening = cv2.morphologyEx(edged, cv2.MORPH_CLOSE, kernel)
//contours = cv2.findContours(opening, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[1]
//con = cv2.drawContours(image, contours, -1, (0,255,0), 2)
//#cv2.imshow("someth",con)
//#plt.imshow(con)
//contours = sorted(contours, key=cv2.contourArea, reverse=True)
//
//arr = []
//for contour in contours:
//    p=cv2.arcLength(contour,True)
//    approx=cv2.approxPolyDP(contour,0.02*p,True)
//
//    if len(arr) < 4 and len(approx)==4:
//        arr.append(approx)
//    else:
//        break
//
//img = crop_image(image, arr[2])
//print(arr)
//#------Get an ordered list of answer blocks--------
//gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
//blurred = cv2.GaussianBlur(gray, (5, 5), 0)
//#blurred = cv2.medianBlur(gray, 3)
//edged = cv2.Canny(blurred, 50, 100)
//
//kernel = np.ones((5,5),np.uint8)
//opening = cv2.morphologyEx(edged, cv2.MORPH_CLOSE, kernel)
//th = cv2.adaptiveThreshold(blurred, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 115, 1)
//
//cnts = cv2.findContours(th, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_SIMPLE)[1]
//
//con = cv2.drawContours(img, cnts, -1, (0,255,0), 2)
//
//
//
//cnts = sorted(cnts, key=cv2.contourArea, reverse=True)
//arr = []
//for c in cnts:
//    p=cv2.arcLength(c,True)
//    approx=cv2.approxPolyDP(c,0.02*p,True)
//
//    if len(arr) < 24 and len(approx)==4:
//        arr.append(approx)
//    else:
//        break
//#con = cv2.drawContours(img, arr, -1, (0,255,0), 2)
//
//sorted_arr = sorted(arr, key=lambda ctr: cv2.boundingRect(ctr)[0] )
//print(sorted_arr)
//print(len(sorted_arr))
//min_index = 0
//max_index = 6
//while max_index <= len(sorted_arr):
//    sorted_arr[min_index:max_index] = sorted(sorted_arr[min_index:max_index], 
//              key=lambda ctr: cv2.boundingRect(ctr)[0] + cv2.boundingRect(ctr)[1] * image.shape[1])
//    min_index = min_index + 6
//    max_index = max_index + 6
//
//con = cv2.drawContours(img, sorted_arr, -1, (0,255,0), 2)
//print(sorted_arr)
//cropped_block = crop_image(img, sorted_arr[0])
//cv2.imshow("c",cropped_block)
//#------Get chosen answer-------
//cropped_block = cv2.cvtColor(cropped_block, cv2.COLOR_BGR2RGB)
//gray = cv2.cvtColor(cropped_block, cv2.COLOR_BGR2GRAY)
//blurred = cv2.GaussianBlur(gray, (5, 5), 0)
//th = cv2.adaptiveThreshold(blurred, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 25, 15)
//kernel = np.ones((5,5),np.uint8)
//opening = cv2.morphologyEx(th, cv2.MORPH_CLOSE, kernel)
//edged = cv2.Canny(opening, 50, 100)
//cnts = cv2.findContours(edged, cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)[1]
//con = cv2.drawContours(cropped_block, cnts, -1, (0,255,0), 2)
//
//
//#answers_blocks = get_answers_blocks(cropped_answers)
//#cropped_block = crop_image(cropped_answers, answers_blocks[0])
//
//#-------Get answers from a block -- experimenting
//gray = cv2.cvtColor(cropped_block, cv2.COLOR_BGR2GRAY)
//thresh = cv2.threshold(gray,0,255,cv2.THRESH_BINARY+cv2.THRESH_OTSU)[1]
//thresh = cv2.bitwise_not(thresh)
//
//element = cv2.getStructuringElement(shape=cv2.MORPH_RECT, ksize=(7, 7))
//
//morph_img = thresh.copy()
//kernel = np.ones((5,5),np.uint8)
//
//morph_img = cv2.morphologyEx(src=thresh, op=cv2.MORPH_CLOSE, kernel=kernel)
//
//bgr = cv2.cvtColor(morph_img, cv2.COLOR_GRAY2BGR)
//
//contours = get_contours(bgr, cv2.RETR_CCOMP)
//contours = sorted(contours, key=cv2.contourArea, reverse=True)
//contours = contours[1:]
//
//ellipses = {}
//questions_dict = {}
//for contour in contours:
//    if len(contour) > 5:
//        (x, y), (MA, ma), angle = cv2.fitEllipse(contour)
//        ellipse = (x, y), (MA, ma), angle
//        area = (math.pi / 4) * MA * ma
//        ellipses[ellipse] = area
//
//sorted_ellipses = sorted(ellipses.items(), key=lambda x: x[1], reverse=True)
//sorted_ellipses = sorted_ellipses[0:20]
//ellipse_arr = []
//for ellipse in sorted_ellipses:
//    center = ellipse[0][0]
//    ellipse_arr.append(tuple([round(x) for x in center]))
//
//ellipse_arr = sorted(ellipse_arr, key=lambda k: k[1]) 
//
//questions_dict[1] = sorted(ellipse_arr[0:4], key=lambda x: x[0])
//questions_dict[2] = sorted(ellipse_arr[4:8], key=lambda x: x[0])
//questions_dict[3] = sorted(ellipse_arr[8:12], key=lambda x: x[0])
//questions_dict[4] = sorted(ellipse_arr[12:16], key=lambda x: x[0])
//questions_dict[5] = sorted(ellipse_arr[16:20], key=lambda x: x[0])

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
