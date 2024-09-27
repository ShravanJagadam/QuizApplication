package com.example.AppQuiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.AppQuiz.Question;
import com.example.AppQuiz.QuizDao.QuizDao;
import com.example.AppQuiz.dao.QuestionDao;
import com.example.AppQuiz.model.QuestionWrapper;
import com.example.AppQuiz.model.Quiz;
import com.example.AppQuiz.model.Response;

@Service
public class QuizService {
	@Autowired
	 QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		// TODO Auto-generated method stub
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);
		
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizDao.save(quiz);
		return new ResponseEntity<>("success",HttpStatus.CREATED);
		 
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestions(Integer id) {
		// TODO Auto-generated method stub
		Optional<Quiz> quiz = quizDao.findById(id);
		List<Question> questionsFromDB = quiz.get().getQuestions();
		List<QuestionWrapper> questionsForUser = new ArrayList<>();
		for(Question q : questionsFromDB) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
			questionsForUser.add(qw);
		}
		return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
		
		
	}

	public ResponseEntity<Integer> calculate(Integer id, List<Response> responses) {
		// TODO Auto-generated method stub
		Quiz quiz = quizDao.findById(id).get();
		List<Question> questions = quiz.getQuestions();
		int score = 0;
		int i = 0;
		for(Response response : responses) {
			if(response.getResponse().equals(questions.get(i).getRightAnswer()))
				score++;
			i++;
		}
		
		return new ResponseEntity<>(score,HttpStatus.OK);
	}
	
	
	
}
