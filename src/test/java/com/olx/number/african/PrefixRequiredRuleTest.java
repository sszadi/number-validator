package com.olx.number.african;

import com.olx.number.NumberValidationResult;
import com.olx.number.PhoneNumber;
import com.olx.number.Status;
import org.junit.jupiter.api.Test;

import static com.olx.number.Description.ADDED_PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PrefixRequiredRuleTest {

	@Test
	void shouldFixedNumberWithoutPrefix() {
		// given
		String numberWithoutPrefix = "433342134";
		PhoneNumber phoneNumber = PhoneNumber.builder().number(numberWithoutPrefix).build();

		// when
		prefixRequiredRule.validate(phoneNumber);

		// then
		assertEquals(Status.FIXED, phoneNumber.getStatus());
		assertEquals(AfricanNumberRules.PREFIX + numberWithoutPrefix, phoneNumber.getNumber());

		NumberValidationResult expectedResult = new NumberValidationResult();
		expectedResult.setDescription(ADDED_PREFIX);
		assertEquals(expectedResult, phoneNumber.getResult());
	}

	@Test
	void shouldNotFixGoodNumber() {
		// given
		PhoneNumber phoneNumber = PhoneNumber.builder().number("27595849584").build();

		// when
		prefixRequiredRule.validate(phoneNumber);

		// then
		assertNull(phoneNumber.getStatus());
		assertNull(phoneNumber.getResult());
	}

	private PrefixRequiredRule prefixRequiredRule = new PrefixRequiredRule();

}
