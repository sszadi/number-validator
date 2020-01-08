package com.olx.number.african;

import com.olx.number.NumberValidationResult;
import com.olx.number.PhoneNumber;
import com.olx.number.Status;
import org.junit.jupiter.api.Test;

import static com.olx.number.Description.FIXED_PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProperPrefixRuleTest {

	@Test
	void shouldFixedNumberWitWrongPrefix() {
		// given
		String number = "433342134";
		String wrongPrefix = "12";
		PhoneNumber phoneNumber = PhoneNumber.builder().number(wrongPrefix + number).build();

		// when
		properPrefixRule.validate(phoneNumber);

		// then
		assertEquals(Status.FIXED, phoneNumber.getStatus());
		assertEquals(AfricanNumberRules.PREFIX + number, phoneNumber.getNumber());

		NumberValidationResult expectedResult = new NumberValidationResult();
		expectedResult.setDescription(FIXED_PREFIX);
		assertEquals(expectedResult, phoneNumber.getResult());
	}

	@Test
	void shouldNotFixGoodNumber() {
		// given
		PhoneNumber phoneNumber = PhoneNumber.builder().number("27595849584").build();

		// when
		properPrefixRule.validate(phoneNumber);

		// then
		assertNull(phoneNumber.getStatus());
		assertNull(phoneNumber.getResult());
	}

	private ProperPrefixRule properPrefixRule = new ProperPrefixRule();
}
