package com.olx.number.african;

import com.olx.number.NumberValidationResult;
import com.olx.number.PhoneNumber;
import com.olx.number.Status;
import org.junit.jupiter.api.Test;

import static com.olx.number.Description.NOT_ENOUGH_NUMBERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MinimumNumbersRuleTest {


	@Test
	void shouldMarkTooLongShortNumberAsInvalid() {
		// given
		PhoneNumber phoneNumber = PhoneNumber.builder().number("444433").build();

		// when
		minimumNumbersRule.validate(phoneNumber);

		// then
		assertEquals(Status.INVALID, phoneNumber.getStatus());

		NumberValidationResult expectedResult = new NumberValidationResult();
		expectedResult.setDescription(NOT_ENOUGH_NUMBERS);
		assertEquals(expectedResult, phoneNumber.getResult());
	}

	@Test
	void shouldNotMarkGoodNumberAsInvalid() {
		// given
		PhoneNumber phoneNumber = PhoneNumber.builder().number("77777777777").build();

		// when
		minimumNumbersRule.validate(phoneNumber);

		// then
		assertNull(phoneNumber.getStatus());
		assertNull(phoneNumber.getResult());
	}

	private MinimumNumbersRule minimumNumbersRule = new MinimumNumbersRule();
}
