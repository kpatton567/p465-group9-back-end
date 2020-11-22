package com.group9.prevue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.Review;
import com.group9.prevue.model.Theater;
import com.group9.prevue.model.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

	List<Review> findByTheater(Theater theater);
	List<Review> findByUser(User user);
}
