package com.olx.number;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/phone-number")
@RequiredArgsConstructor
@Slf4j
class PhoneNumberRestController {

	@PostMapping("/upload")
	ResponseEntity<PhoneNumberUploadResponse> uploadPhoneNumberFile(@RequestParam("file") MultipartFile file)
		throws FileParseException {
		log.info("Uploaded phone number file");

		if (file.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		PhoneNumberUploadResponse phoneNumberUploadResponse = phoneNumberService.processPhoneNumbers(file);

		return new ResponseEntity<>(phoneNumberUploadResponse, HttpStatus.CREATED);

	}

	@GetMapping("/upload/{uuid}")
	ResponseEntity<PhoneNumberUploadResponse> getInformationAboutFile(@PathVariable String uuid) throws FileNotFoundException {
		log.info("Get information about file={}", uuid);
		Statistics statistics = statisticsRepository.findById(uuid).orElseThrow(FileNotFoundException::new);
		PhoneNumberUploadResponse phoneNumberUploadResponse = PhoneNumberUploadResponse.builder()
			.result(statistics.getNumberList())
			.statistics(statistics).build();
		return new ResponseEntity<>(phoneNumberUploadResponse, HttpStatus.OK);

	}

	@GetMapping("/{number}")
	ResponseEntity<PhoneNumber> testSingleNumber(@PathVariable String number) {
		log.info("Test number={}", number);
		PhoneNumber validationResult = phoneNumberService.testSingleNumber(number);
		return new ResponseEntity<>(validationResult, HttpStatus.OK);
	}

	private final PhoneNumberService phoneNumberService;

	private final StatisticsRepository statisticsRepository;
}
