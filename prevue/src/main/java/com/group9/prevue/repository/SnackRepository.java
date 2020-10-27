package com.group9.prevue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.Snack;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Long>{

}
