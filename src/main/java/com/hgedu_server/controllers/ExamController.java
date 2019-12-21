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
import com.hgedu_server.models.Question;
import com.hgedu_server.models.QuestionDetail;
import com.hgedu_server.services.ExamResultService;
import com.hgedu_server.services.ExamService;
import com.hgedu_server.services.ExamTestService;
import com.hgedu_server.services.QuestionService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
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
    }
    
    @GetMapping("/api/doExam/{examId}/{userId}/{classId}/{nthTrial}")
    public ResponseEntity<?> getTestForExam(@PathVariable("examId") Long examId, @PathVariable("userId") Long userId, @PathVariable("classId") Long classId, @PathVariable("nthTrial") Long nthTrial) {
        //check if no trial left
        System.out.println("O day");
        Long currentTrial = examResultService.getNthTrial(examId, userId);
        Exam exam = examService.getExamById(examId);
        List<QuestionDetail> questionMap = new ArrayList<>();
        if(exam != null){
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
                    result.setStudentId(userId);
                    result.setClassId(classId);
                    examResultService.createNewExamResult(result);
                    //get questions for test
                    questionMap = questionService.getQuestionsForTestRandomList(examTest.getTestId());
                }
            }
        }
        return ResponseEntity.ok(questionMap);
    }
    
    @PostMapping("/api/doExam/{examId}/{userId}")
    public ResponseEntity<?> SubmitTest(@PathVariable("examId") Long examId, @PathVariable("userId") Long userId, @RequestBody Map<Long, Long> studentAnswers) {
        //get examResultId
        ExamResult examResult = examResultService.getExamResultForTest(examId, userId);
        Exam exam = examService.getExamById(examId);
        if(examResult == null || exam == null) {
            
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
                        
                    }
                    return ResponseEntity.ok(mark);
                }
            } catch (ParseException ex) {
                Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
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
