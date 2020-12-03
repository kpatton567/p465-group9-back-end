package com.group9.prevue.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.EPaymentStatus;
import com.group9.prevue.model.Payment;
import com.group9.prevue.model.User;
import com.group9.prevue.model.Theater;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	List<Payment> findByUser(User userId);
	List<Payment> findByTheater(Theater theaterId);
	List<Payment> findByTheaterAndStatus(Theater theaterId, EPaymentStatus status);
	List<Payment> findByTheaterAndStatusNot(Theater theaterId, EPaymentStatus status);
	List<Payment> findByTheaterAndStatusNotAndPaymentDateGreaterThan(Theater theaterId, EPaymentStatus status, Date date);
}
