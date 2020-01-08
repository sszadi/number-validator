package com.olx.number;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.olx.number.Description.ADD_PREFIX;
import static com.olx.number.Description.NOT_ENOUGH_NUMBERS;

@RequiredArgsConstructor
@Component
@Slf4j
class AfricanPhoneNumberValidator {

	void validatePhoneNumbers(List<PhoneNumber> phoneNumbers) {
		List<PhoneNumber> negative = new ArrayList<>();
		normalizationProcessor.normalizeNumbers(phoneNumbers);

		for (PhoneNumber phoneNumber : phoneNumbers) {
			if (phoneNumber.getNumber().matches(AFRICAN_PHONE_NUMBER_REGEX)) {
				phoneNumber.setStatus(Status.VALID);
			} else if (phoneNumber.getNumber().matches(AFRICAN_NUMBER_WITHOUT_PREFIX)) {
				phoneNumber.setNumber(PREFIX + phoneNumber.getNumber());
				phoneNumber.setStatus(Status.FIXED);
				NumberValidationResult result = new NumberValidationResult();
				result.setDescription(ADD_PREFIX);
				phoneNumber.setResult(result);
			} else if (phoneNumber.getNumber().length() < 9) {
				phoneNumber.setStatus(Status.INVALID);
				NumberValidationResult result = new NumberValidationResult();
				result.setDescription(NOT_ENOUGH_NUMBERS);
			} else if (phoneNumber.getNumber().length() >= 12) {
				phoneNumber.setStatus(Status.INVALID);
				NumberValidationResult result = new NumberValidationResult();
				result.setDescription(NOT_ENOUGH_NUMBERS);
			} else {
				negative.add(phoneNumber);
			}
		}

		negative.forEach(n-> log.info(n.getNumber()));
	}


	// TODO
	// bledny prefix, zmiana na 27

	private static final String AFRICAN_PHONE_NUMBER_REGEX = "^27[0-9]{9}$";
	private static final String AFRICAN_NUMBER_WITHOUT_PREFIX = "^[0-9]{9}$";
	private static final String PREFIX = "27";

	private final NumberNormalizationProcessor normalizationProcessor;

}
