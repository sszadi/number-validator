package com.olx.number.african;

import com.olx.number.NumberRule;
import com.olx.number.NumberValidationResult;
import com.olx.number.PhoneNumber;
import com.olx.number.Status;

import static com.olx.number.Description.FIXED_PREFIX;
import static com.olx.number.african.AfricanNumberRules.NUMBER_WITH_WRONG_PREFIX;
import static com.olx.number.african.AfricanNumberRules.PREFIX;

public class ProperPrefixRule implements NumberRule {
	@Override
	public void validate(PhoneNumber phoneNumber) {
		if (phoneNumber.getNumber().matches(NUMBER_WITH_WRONG_PREFIX)) {
			String substring = phoneNumber.getNumber().substring(2);
			phoneNumber.setNumber(PREFIX + substring);
			phoneNumber.setStatus(Status.FIXED);
			NumberValidationResult result = new NumberValidationResult();
			result.setDescription(FIXED_PREFIX);
			phoneNumber.setResult(result);
		}
	}
}
