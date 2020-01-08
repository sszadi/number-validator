package com.olx.number;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.olx.number.Description.REDUNDANT_CHARS_DELETED;

@Component
@Slf4j
class NumberNormalizationProcessor {

	void normalizeNumbers(List<PhoneNumber> phoneNumbers) {
		List<PhoneNumber> numbersToAdd = new ArrayList<>();
		List<PhoneNumber> numbersToRemove = new ArrayList<>();

		for (PhoneNumber phoneNumber : phoneNumbers) {
			if (phoneNumber.getNumber().contains(DELETED)) {
				NumberValidationResult result = new NumberValidationResult();
				result.setDescription(REDUNDANT_CHARS_DELETED);
				if (phoneNumber.getNumber().matches(NUMBERS_TO_SPLIT)) {
					log.debug("Number to split= {}", phoneNumber);
					String[] splitNumbers = phoneNumber.getNumber().split(DELETED);
					numbersToRemove.add(phoneNumber);
					PhoneNumber firstNumber = PhoneNumber.builder().number(splitNumbers[0]).status(Status.FIXED)
						.result(result).numberId(UUID.randomUUID().toString()).build();
					numbersToAdd.add(firstNumber);
					PhoneNumber secondNumber = PhoneNumber.builder().status(Status.FIXED)
						.result(result).number(splitNumbers[1]).numberId(UUID.randomUUID().toString()).build();
					numbersToAdd.add(secondNumber);
				} else {
					log.debug("Number to normalize= {}", phoneNumber);
					phoneNumber.setNumber(phoneNumber.getNumber().replace(DELETED, ""));
					phoneNumber.setResult(result);
					phoneNumber.setStatus(Status.FIXED);
				}
			}
		}

		phoneNumbers.removeAll(numbersToRemove);
		phoneNumbers.addAll(numbersToAdd);
	}

	private static final String DELETED = "_DELETED_";
	private static final String NUMBERS_TO_SPLIT = "^[0-9]+_DELETED_[0-9]+$";

}
