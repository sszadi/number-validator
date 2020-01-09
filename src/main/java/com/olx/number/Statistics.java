package com.olx.number;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "numberList")
class Statistics {

	@Id
	private String fileIdentifier;
	@OneToMany(mappedBy = "statistic")
	@Cascade(CascadeType.ALL)
	private List<PhoneNumber> numberList;
	private int validAmount;
	private int fixedAmount;
	private int invalidAmount;
	private int createdAmount;
}
