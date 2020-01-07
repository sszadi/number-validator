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



		phoneNumberRepository.saveAll(phoneNumbers);
	}

	private final PhoneNumberParser phoneNumberParser;

	private final PhoneNumberRepository phoneNumberRepository;
}