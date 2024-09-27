package com.example.AppQuiz.QuizDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AppQuiz.Question;
import com.example.AppQuiz.model.Quiz;

@Repository
public interface QuizDao extends JpaRepository<Quiz,Integer >{
	
}
