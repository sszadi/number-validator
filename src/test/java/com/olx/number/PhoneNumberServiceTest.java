package com.olx.number;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@SpringBootTest
class PhoneNumberServiceTest {

	@Test
	void shouldnanana() throws IOException, FileParseException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("South_African_Mobile_Numbers.csv")).getFile());
		FileInputStream fi1eInputStream = new FileInputStream(file);
		MockMultipartFile multipartFile = new MockMultipartFile("user-file", "test.csv",
			"text/plain", fi1eInputStream);

		phoneNumberService.processPhoneNumbers(multipartFile);

	}

	@Autowired
	private PhoneNumberService phoneNumberService;
	@Autowired
	private PhoneNumberParser phoneNumberParser;
	@Autowired
	private AfricanPhoneNumberValidator validator;
	@Autowired
	private NumberNormalizationProcessor normalizationProcessor;


}
