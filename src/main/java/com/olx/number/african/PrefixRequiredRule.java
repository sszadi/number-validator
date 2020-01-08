package com.olx.number.african;

import com.olx.number.NumberRule;
import com.olx.number.NumberValidationResult;
import com.olx.number.PhoneNumber;
import com.olx.number.Status;

import static com.olx.number.Description.ADDED_PREFIX;
import static com.olx.number.african.AfricanNumberRules.NUMBER_WITHOUT_PREFIX;
import static com.olx.number.african.AfricanNumberRules.PREFIX;

public class PrefixRequiredRule implements NumberRule {
	@Override
	public void validate(PhoneNumber phoneNumber) {
		if (phoneNumber.getNumber().matches(NUMBER_WITHOUT_PREFIX)) {
			phoneNumber.setNumber(PREFIX + phoneNumber.getNumber());
			phoneNumber.setStatus(Status.FIXED);
			NumberValidationResult result = new NumberValidationResult();
			result.setDescription(ADDED_PREFIX);
			phoneNumber.setResult(result);
		}
	}


}
