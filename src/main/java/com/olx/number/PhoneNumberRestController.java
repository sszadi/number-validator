package com.olx.number;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
class PhoneNumberRestController {

	@PostMapping("/phone-number")
	ResponseEntity uploadPhoneNumberFile(
		@RequestParam("file") MultipartFile file) throws FileParseException {
		log.info("Uploaded phone number file");

		if (file.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		phoneNumberService.processPhoneNumbers(file);

		return new ResponseEntity(HttpStatus.OK);

	}

	//TODO controllerAdvice

	private final PhoneNumberService phoneNumberService;
}
