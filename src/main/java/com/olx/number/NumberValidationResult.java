package com.olx.number;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
class NumberValidationResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private PhoneNumber phoneNumber;
	private Description description;
}
