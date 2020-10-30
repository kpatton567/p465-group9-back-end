package com.group9.prevue.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class SnackQuantity {

	@ManyToOne
	private Snack snack;
	private Integer quantity;
	
	public SnackQuantity() {}
	
	public SnackQuantity(Snack snack, Integer quantity) {
		this.snack = snack;
		this.quantity = quantity;
	}

	public Snack getSnack() {
		return snack;
	}

	public void setSnack(Snack snack) {
		this.snack = snack;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
