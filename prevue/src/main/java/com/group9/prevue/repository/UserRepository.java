package com.group9.prevue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.group9.prevue.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Boolean existsByUserId(String userId);
	User findByUserId(String userId);
}
