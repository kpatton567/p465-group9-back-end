package com.group9.prevue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.UserProfile;

@Repository
public interface ProfileRepository extends JpaRepository<UserProfile, String>{

}
