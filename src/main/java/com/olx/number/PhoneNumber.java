package com.olx.number;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhoneNumber {

	@Id
	private String numberId;
	private String number;
	@Enumerated(EnumType.STRING)
	private Status status;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "numberId", referencedColumnName = "id")
	private NumberValidationResult result;
	@ManyToOne
	@JoinColumn(name = "fileIdentifier", nullable = false)
	private Statistics statistic;
}
