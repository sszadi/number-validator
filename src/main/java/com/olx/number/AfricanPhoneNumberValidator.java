package com.olx.number;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.olx.number.Description.ADD_PREFIX;
import static com.olx.number.Description.FIXED_PREFIX;
import static com.olx.number.Description.NOT_ENOUGH_NUMBERS;
import static com.olx.number.Description.TOO_MUCH_NUMBERS;

@RequiredArgsConstructor
@Component
@Slf4j
class AfricanPhoneNumberValidator {

	List<PhoneNumber> validatePhoneNumbers(List<PhoneNumber> phoneNumbers) {
		for (PhoneNumber phoneNumber : phoneNumbers) {
			if (phoneNumber.getNumber().matches(AFRICAN_PHONE_NUMBER_REGEX)) {
				phoneNumber.setStatus(Status.VALID);
			}
		}

		List<PhoneNumber> validNumbers = phoneNumbers.stream().filter(n -> n.getNumber().matches(AFRICAN_NUMBER_WITHOUT_PREFIX)).collect(Collectors.toList());
		validNumbers.forEach(n -> n.setStatus(Status.VALID));
		List<PhoneNumber> numbersToValidate =
			phoneNumbers.stream().filter(n -> !validNumbers.contains(n)).collect(Collectors.toList());

		normalizationProcessor.normalizeNumbers(numbersToValidate);

		for (PhoneNumber phoneNumber : numbersToValidate) {
			if (phoneNumber.getNumber().matches(AFRICAN_NUMBER_WITHOUT_PREFIX)) {
				phoneNumber.setNumber(PREFIX + phoneNumber.getNumber());
				phoneNumber.setStatus(Status.FIXED);
				NumberValidationResult result = new NumberValidationResult();
				result.setDescription(ADD_PREFIX);
				phoneNumber.setResult(result);
			} else if (phoneNumber.getNumber().length() <= 10) {
				phoneNumber.setStatus(Status.INVALID);
				NumberValidationResult result = new NumberValidationResult();
				result.setDescription(NOT_ENOUGH_NUMBERS);
				phoneNumber.setResult(result);
			} else if (phoneNumber.getNumber().length() >= 12) {
				phoneNumber.setStatus(Status.INVALID);
				NumberValidationResult result = new NumberValidationResult();
				result.setDescription(TOO_MUCH_NUMBERS);
				phoneNumber.setResult(result);
			} else if (phoneNumber.getNumber().matches(NUMBER_WITH_WRONG_PREFIX)) {
				String substring = phoneNumber.getNumber().substring(2);
				phoneNumber.setNumber(PREFIX + substring);
				phoneNumber.setStatus(Status.FIXED);
				NumberValidationResult result = new NumberValidationResult();
				result.setDescription(FIXED_PREFIX);
				phoneNumber.setResult(result);
			}
		}

		return Stream.concat(validNumbers.stream(), numbersToValidate.stream())
			.collect(Collectors.toList());
	}

	private static final String AFRICAN_PHONE_NUMBER_REGEX = "^27[0-9]{9}$";
	private static final String AFRICAN_NUMBER_WITHOUT_PREFIX = "^[0-9]{9}$";
	private static final String NUMBER_WITH_WRONG_PREFIX = "^(?!27)[0-9]{11}$";
	private static final String PREFIX = "27";

	private final NumberNormalizationProcessor normalizationProcessor;

}
