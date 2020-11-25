package com.group9.prevue.repository;

import java.util.List;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {

	List<Coupon> findByExpirationDateGreaterThan(Date date);
	Optional<Coupon> findByCodeAndExpirationDateGreaterThan(String code, Date date);
}
