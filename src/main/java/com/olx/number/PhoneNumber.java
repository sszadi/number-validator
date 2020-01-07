package com.olx.number;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@Builder
class PhoneNumber {

	@Id
	private String numberId;
	private String number;
	private Status status;
	@OneToOne
	private NumberValidationResult numberValidationResult;
}
