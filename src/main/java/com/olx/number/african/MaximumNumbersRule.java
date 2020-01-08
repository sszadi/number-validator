package com.olx.number.african;

import com.olx.number.NumberRule;
import com.olx.number.NumberValidationResult;
import com.olx.number.PhoneNumber;
import com.olx.number.Status;

import static com.olx.number.Description.TOO_MUCH_NUMBERS;

public class MaximumNumbersRule implements NumberRule {
	@Override
	public void validate(PhoneNumber phoneNumber) {
		if (phoneNumber.getNumber().length() >= 12) {
			phoneNumber.setStatus(Status.INVALID);
			NumberValidationResult result = new NumberValidationResult();
			result.setDescription(TOO_MUCH_NUMBERS);
			phoneNumber.setResult(result);
		}
	}
}
