package com.olx.number;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static com.olx.number.Description.DELETED_REDUNDANT_CHARS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class NumberNormalizationProcessorTest {

	@Test
	void shouldSplitNumbers() {
		// given
		PhoneNumber firstNumber = PhoneNumber.builder().number("445454543").build();
		PhoneNumber secondNumber = PhoneNumber.builder().number("34343434").build();
		PhoneNumber numbersToSplit = PhoneNumber.builder().number(firstNumber.getNumber() + "_DELETED_" + secondNumber.getNumber()).build();
		List<PhoneNumber> numbers = Collections.singletonList(numbersToSplit);

		// when
		List<PhoneNumber> result = normalizationProcessor.normalizeNumbers(numbers);

		// then
		assertFalse(result.contains(numbersToSplit));
		assertThat(result).usingElementComparatorOnFields("number")
			.contains(firstNumber);
		assertThat(result).usingElementComparatorOnFields("number")
			.contains(secondNumber);
		assertEquals(result.stream().filter(r -> Status.FIXED.equals(r.getStatus())).count(), result.size());
		NumberValidationResult validationResult = new NumberValidationResult();
		validationResult.setDescription(DELETED_REDUNDANT_CHARS);
		assertEquals(result.stream().filter(r -> validationResult.equals(r.getResult())).count(), result.size());
	}

	@Test
	void shouldNormalizeNumbers() {
		// given
		String properNumber = "445454543";
		PhoneNumber phoneNumber = PhoneNumber.builder().number("_DELETED_" + properNumber).build();
		List<PhoneNumber> numbers = Collections.singletonList(phoneNumber);

		// when
		List<PhoneNumber> result = normalizationProcessor.normalizeNumbers(numbers);

		// then

		assertThat(result.stream().map(PhoneNumber::getNumber))
			.contains(properNumber);
		assertEquals(result.stream().filter(r -> Status.FIXED.equals(r.getStatus())).count(), result.size());
		NumberValidationResult validationResult = new NumberValidationResult();
		validationResult.setDescription(DELETED_REDUNDANT_CHARS);
		assertEquals(result.stream().filter(r -> validationResult.equals(r.getResult())).count(), result.size());
	}

	@Autowired
	private NumberNormalizationProcessor normalizationProcessor;

}
