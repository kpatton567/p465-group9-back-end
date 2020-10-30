package com.group9.prevue.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.Theater;
import com.group9.prevue.model.User;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long>{

	Optional<Theater> findByManager(User manager);
}
