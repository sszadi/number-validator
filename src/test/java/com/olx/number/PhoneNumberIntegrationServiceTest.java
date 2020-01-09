package com.olx.number;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SpringBootTest
class PhoneNumberIntegrationServiceTest {

	@Test
	void shouldProcessNumbers() throws IOException, FileParseException {
		// given
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("simple_numbers_file.csv")).getFile());
		FileInputStream fi1eInputStream = new FileInputStream(file);
		MockMultipartFile multipartFile = new MockMultipartFile("user-file", "test.csv",
			"text/plain", fi1eInputStream);

		// when
		PhoneNumberUploadResponse response = phoneNumberService.processPhoneNumbers(multipartFile);

		// then
		assertEquals(9, response.getResult().size());
		assertEquals(9, response.getStatistics().getCreatedAmount());
		assertEquals(2, response.getStatistics().getValidAmount());
		assertEquals(4, response.getStatistics().getFixedAmount());
		assertEquals(3, response.getStatistics().getInvalidAmount());
		assertNotNull(response.getStatistics().getFileIdentifier());
	}

	@Test
	void testSingleValidNumber() {
		// given
		String validNumber = "27443456458";

		// when
		PhoneNumber result = phoneNumberService.testSingleNumber(validNumber);

		// then
		assertEquals(Status.VALID, result.getStatus());
		assertNull(result.getResult());
	}

	@Test
	void testSingleInvalidNumber() {
		// given
		String validNumber = "2744544445645";

		// when
		PhoneNumber result = phoneNumberService.testSingleNumber(validNumber);

		// then
		assertEquals(Status.INVALID, result.getStatus());
		assertNotNull(result.getResult());

	}

	@Autowired
	private PhoneNumberService phoneNumberService;
	@Autowired
	private PhoneNumberParser phoneNumberParser;
	@Autowired
	private AfricanPhoneNumberValidator validator;
	@Autowired
	private NumberNormalizationProcessor normalizationProcessor;
	@Autowired
	private PhoneNumberStatisticsCollector statisticsCollector;
	@Autowired
	private StatisticsRepository statisticsRepository;


}
