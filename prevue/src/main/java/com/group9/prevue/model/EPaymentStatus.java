package com.group9.prevue.model;

public enum EPaymentStatus {

	REFUNDABLE, //Refund available
	REQUESTED, //Refund requested, not yet accepted
	REFUNDED, //Refund was accepted by theater manager
	FINAL //Can no longer be refunded, either because it was denied a refund or is past showtime date
}
