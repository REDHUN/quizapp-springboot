package com.redhun.quizapp.dao;

import com.redhun.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

	List<Question> findByCategory(String category);

	@Query(value = "SELECT * FROM question q Where q.category=:category ORDER BY RANDOM()", nativeQuery = true)
	List<Question> findRandomQuestionsByCatogory(String category, int numQ);
}