package com.olx.number;

import com.olx.number.african.MaximumNumbersRule;
import com.olx.number.african.MinimumNumbersRule;
import com.olx.number.african.PrefixRequiredRule;
import com.olx.number.african.ProperPrefixRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.olx.number.african.AfricanNumberRules.NUMBER_WITHOUT_PREFIX;

@RequiredArgsConstructor
@Component
@Slf4j
class AfricanPhoneNumberValidator {

	List<PhoneNumber> validatePhoneNumbers(List<PhoneNumber> phoneNumbers) {
		List<PhoneNumber> validNumbers = markValidNumbers(phoneNumbers);

		List<PhoneNumber> numbersToValidate =
			phoneNumbers.stream().filter(n -> !validNumbers.contains(n)).collect(Collectors.toList());

		normalizationProcessor.normalizeNumbers(numbersToValidate);
		runValidations(numbersToValidate);

		return Stream.concat(validNumbers.stream(), numbersToValidate.stream())
			.collect(Collectors.toList());
	}

	private void runValidations(List<PhoneNumber> numbersToValidate) {
		List<NumberRule> rules = new ArrayList<>();
		rules.add(new PrefixRequiredRule());
		rules.add(new MinimumNumbersRule());
		rules.add(new MaximumNumbersRule());
		rules.add(new ProperPrefixRule());

		for (PhoneNumber phoneNumber : numbersToValidate
		) {
			for (NumberRule rule : rules) {
				rule.validate(phoneNumber);
			}
		}
	}

	private List<PhoneNumber> markValidNumbers(List<PhoneNumber> phoneNumbers) {
		List<PhoneNumber> validNumbers = phoneNumbers.stream().
			filter(n -> n.getNumber().matches(NUMBER_WITHOUT_PREFIX)).collect(Collectors.toList());
		validNumbers.forEach(n -> n.setStatus(Status.VALID));
		return validNumbers;
	}


	private final NumberNormalizationProcessor normalizationProcessor;

}
