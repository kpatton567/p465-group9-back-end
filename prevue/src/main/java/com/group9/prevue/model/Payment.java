package com.group9.prevue.model;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Theater theater;
	@ManyToOne
	private Movie movie;
	@ManyToOne
	private User user;
	
	private Date paymentDate;
	
	@Embedded
	private ShowtimeInfo showtimePrice;
	private Integer ticketCount;
	
	@OneToOne
	private PaymentInfo paymentInfo;
	
	@ElementCollection
	private List<Snack> snacks;
	//private Double total;
	
	public Payment() {}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Theater getTheater() {
		return theater;
	}
	
	public void setTheater(Theater theater) {
		this.theater = theater;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
	
	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	
	public List<Snack> getSnacks() {
		return snacks;
	}
	
	public void setSnacks(List<Snack> snacks) {
		this.snacks = snacks;
	}
	
	/*public Double getTotal() {
		return total;
	}
	
	public void setTotal(Double total) {
		this.total = total;
	}*/

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public ShowtimeInfo getShowtimePrice() {
		return showtimePrice;
	}

	public void setShowtimePrice(ShowtimeInfo showtimePrice) {
		this.showtimePrice = showtimePrice;
	}

	public Integer getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	
}
