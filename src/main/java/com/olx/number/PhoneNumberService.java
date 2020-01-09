package com.olx.number;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
class PhoneNumberService {
	PhoneNumberUploadResponse processPhoneNumbers(MultipartFile file) throws FileParseException {
		List<PhoneNumber> phoneNumbers = phoneNumberParser.parsePhoneNumbersFromCsv(file);
		List<PhoneNumber> phoneNumbersAfterValidation = validator.validatePhoneNumbers(phoneNumbers);
		Statistics statistics = statisticsCollector.calculateStatistics(phoneNumbersAfterValidation);
		phoneNumbersAfterValidation.forEach(n -> n.setStatistic(statistics));
		statisticsRepository.save(statistics);
		return PhoneNumberUploadResponse.builder()
			.result(phoneNumbersAfterValidation)
			.statistics(statistics)
			.build();
	}

	PhoneNumber testSingleNumber(String number) {
		List<PhoneNumber> validationResult = validator.validatePhoneNumbers(Collections.singletonList(PhoneNumber.builder().number(number).build()));
		return validationResult.size() >= 1 ? validationResult.get(0) : null;
	}


	private final PhoneNumberStatisticsCollector statisticsCollector;

	private final StatisticsRepository statisticsRepository;

	private final PhoneNumberParser phoneNumberParser;

	private final AfricanPhoneNumberValidator validator;
}
