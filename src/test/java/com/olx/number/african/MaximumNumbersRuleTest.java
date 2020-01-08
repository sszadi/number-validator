package com.olx.number.african;

import com.olx.number.NumberValidationResult;
import com.olx.number.PhoneNumber;
import com.olx.number.Status;
import org.junit.jupiter.api.Test;

import static com.olx.number.Description.TOO_MUCH_NUMBERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MaximumNumbersRuleTest {

	@Test
	void shouldMarkTooLongNumberAsInvalid() {
		// given
		PhoneNumber phoneNumber = PhoneNumber.builder().number("44443334324324").build();

		// when
		maximumNumbersRule.validate(phoneNumber);

		// then
		assertEquals(Status.INVALID, phoneNumber.getStatus());

		NumberValidationResult expectedResult = new NumberValidationResult();
		expectedResult.setDescription(TOO_MUCH_NUMBERS);
		assertEquals(expectedResult, phoneNumber.getResult());
	}

	@Test
	void shouldNotMarkGoodNumberAsInvalid() {
		// given
		PhoneNumber phoneNumber = PhoneNumber.builder().number("77777777777").build();

		// when
		maximumNumbersRule.validate(phoneNumber);

		// then
		assertNull(phoneNumber.getStatus());
		assertNull(phoneNumber.getResult());
	}

	private MaximumNumbersRule maximumNumbersRule = new MaximumNumbersRule();
}
