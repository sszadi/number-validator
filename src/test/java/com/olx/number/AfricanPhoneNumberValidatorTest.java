package com.olx.number;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AfricanPhoneNumberValidatorTest {

	@Test
	void shouldMarkValidNumbers() {
		// given
		List<PhoneNumber> validPhoneNumbers = Arrays.asList(
			PhoneNumber.builder().number("27836475847").build(),
			PhoneNumber.builder().number("27334344317").build());

		// when
		List<PhoneNumber> result = africanPhoneNumberValidator.validatePhoneNumbers(validPhoneNumbers);

		// then
		assertEquals(validPhoneNumbers.size(), result.stream().filter(n -> Status.VALID.equals(n.getStatus())).count());

	}

	@Test
	void shouldPassToNormalizeOnlyInvalidNumbers() {
		// given
		PhoneNumber validNumber = PhoneNumber.builder().number("27836475847").build();
		PhoneNumber invalidNumber = PhoneNumber.builder().number("2734317").build();
		List<PhoneNumber> validPhoneNumbers = Arrays.asList(
			validNumber,
			invalidNumber);

		// when
		africanPhoneNumberValidator.validatePhoneNumbers(validPhoneNumbers);

		//then
		ArgumentCaptor<List<PhoneNumber>> argument = ArgumentCaptor.forClass(List.class);
		verify(normalizationProcessor).normalizeNumbers(argument.capture());
		assertThat(argument.getValue()).usingElementComparatorOnFields("number")
			.contains(invalidNumber);
		assertThat(argument.getValue()).usingElementComparatorOnFields("number")
			.doesNotContain(validNumber);
	}


	@Test
	void shouldReturnValidAndInvalidNumbersWithProperStatus() {
		// given
		PhoneNumber validNumber = PhoneNumber.builder().number("27836475847").status(Status.FIXED).build();
		PhoneNumber invalidNumber = PhoneNumber.builder().number("2734317").status(Status.INVALID).build();
		List<PhoneNumber> validPhoneNumbers = Arrays.asList(
			validNumber,
			invalidNumber);
		when(normalizationProcessor.normalizeNumbers(anyList())).thenReturn(Collections.singletonList(invalidNumber));

		// when
		List<PhoneNumber> phoneNumbers = africanPhoneNumberValidator.validatePhoneNumbers(validPhoneNumbers);

		//then
		assertThat(phoneNumbers).usingElementComparatorOnFields("number", "status")
			.contains(invalidNumber);
		assertThat(phoneNumbers).usingElementComparatorOnFields("number", "status")
			.contains(validNumber);

	}

	@Autowired
	private AfricanPhoneNumberValidator africanPhoneNumberValidator;

	@MockBean
	private NumberNormalizationProcessor normalizationProcessor;

}
