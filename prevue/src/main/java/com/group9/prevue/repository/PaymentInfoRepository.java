package com.group9.prevue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.PaymentInfo;
import com.group9.prevue.model.User;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long>{

	List<PaymentInfo> findByUser(User userId);
}
