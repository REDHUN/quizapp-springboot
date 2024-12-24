package com.redhun.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.redhun.quizapp.model.QuestionWrapper;
import com.redhun.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.redhun.quizapp.dao.QuestionDao;
import com.redhun.quizapp.dao.QuizDao;
import com.redhun.quizapp.model.Question;
import com.redhun.quizapp.model.Quiz;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCatogory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Optional<Quiz> quiz	=quizDao.findById(id);
        List<Question> questionFromDB=quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser=new ArrayList<>();

        for(Question q: questionFromDB) {

            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }
        return  new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for (Response response : responses) {
        
            System.out.println(response.getResponse());
            System.out.println(questions.get(i).getRightAnswer());
            if (response.getResponse().equals(questions.get(i).getRightAnswer())){
                right++;
            }



            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
