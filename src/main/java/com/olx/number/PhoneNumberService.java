package com.olx.number;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
class PhoneNumberService {
	void processPhoneNumbers(MultipartFile file) throws FileParseException {
		List<PhoneNumber> phoneNumbers = phoneNumberParser.parsePhoneNumbersFromCsv(file);
		List<PhoneNumber> phoneNumbersAfterValidation = validator.validatePhoneNumbers(phoneNumbers);
		phoneNumberRepository.saveAll(phoneNumbersAfterValidation);
	}

	private final PhoneNumberParser phoneNumberParser;

	private final PhoneNumberRepository phoneNumberRepository;

	private final AfricanPhoneNumberValidator validator;
}
