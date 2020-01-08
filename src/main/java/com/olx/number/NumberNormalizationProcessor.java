package com.olx.number;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.olx.number.Description.DELETED_REDUNDANT_CHARS;

@Component
@Slf4j
class NumberNormalizationProcessor {

	List<PhoneNumber> normalizeNumbers(List<PhoneNumber> phoneNumbers) {
		List<PhoneNumber> numbersToNormalize = new ArrayList<>(phoneNumbers);
		List<PhoneNumber> numbersToAdd = new ArrayList<>();
		List<PhoneNumber> numbersToRemove = new ArrayList<>();

		for (PhoneNumber phoneNumber : numbersToNormalize) {
			if (phoneNumber.getNumber().contains(DELETED)) {
				NumberValidationResult result = new NumberValidationResult();
				result.setDescription(DELETED_REDUNDANT_CHARS);
				if (phoneNumber.getNumber().matches(NUMBERS_TO_SPLIT)) {
					splitNumbers(numbersToAdd, numbersToRemove, phoneNumber, result);
				} else {
					normalizeNumber(phoneNumber, result);
				}
			}
		}

		numbersToNormalize.removeAll(numbersToRemove);
		numbersToNormalize.addAll(numbersToAdd);
		return numbersToNormalize;
	}

	private static final String DELETED = "_DELETED_";
	private static final String NUMBERS_TO_SPLIT = "^[0-9]+_DELETED_[0-9]+$";

	private void normalizeNumber(PhoneNumber phoneNumber, NumberValidationResult result) {
		log.debug("Number to normalize= {}", phoneNumber);
		phoneNumber.setNumber(phoneNumber.getNumber().replace(DELETED, ""));
		phoneNumber.setResult(result);
		phoneNumber.setStatus(Status.FIXED);
	}

	private void splitNumbers(List<PhoneNumber> numbersToAdd, List<PhoneNumber> numbersToRemove, PhoneNumber phoneNumber, NumberValidationResult result) {
		log.debug("Number to split= {}", phoneNumber);
		String[] splitNumbers = phoneNumber.getNumber().split(DELETED);
		numbersToRemove.add(phoneNumber);
		PhoneNumber firstNumber = PhoneNumber.builder().number(splitNumbers[0]).status(Status.FIXED)
			.result(result).numberId(UUID.randomUUID().toString()).build();
		numbersToAdd.add(firstNumber);
		PhoneNumber secondNumber = PhoneNumber.builder().status(Status.FIXED)
			.result(result).number(splitNumbers[1]).numberId(UUID.randomUUID().toString()).build();
		numbersToAdd.add(secondNumber);
	}

}
