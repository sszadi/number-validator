package com.olx.number;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PhoneNumberParserTest {

	@Test
	void shouldParsePhoneNumbersFromFile() throws IOException, FileParseException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("five_numbers.csv")).getFile());
		FileInputStream fi1eInputStream = new FileInputStream(file);

		MockMultipartFile multipartFile = new MockMultipartFile("user-file", "test.csv",
			"text/plain", fi1eInputStream);
		List<PhoneNumber> phoneNumbers = phoneNumberParser.parsePhoneNumbersFromCsv(multipartFile);
		assertEquals(phoneNumbers.size(), 5);
	}

	@Test
	void shouldThrowFileParseExceptionForWrongDataInLine() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("wrong_data_in_line.csv")).getFile());
		FileInputStream fi1eInputStream = new FileInputStream(file);

		MockMultipartFile multipartFile = new MockMultipartFile("user-file", "test.csv",
			"text/plain", fi1eInputStream);
		assertThrows(FileParseException.class, () ->
			phoneNumberParser.parsePhoneNumbersFromCsv(multipartFile)
		);
	}


	@Autowired
	private PhoneNumberParser phoneNumberParser;
}
