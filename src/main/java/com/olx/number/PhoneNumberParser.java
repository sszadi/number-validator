package com.olx.number;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
class PhoneNumberParser {

	List<PhoneNumber> parsePhoneNumbersFromCsv(MultipartFile file) throws FileParseException {
		BufferedReader reader;
		List<PhoneNumber> result = new ArrayList<>();
		try {

			InputStream is = file.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			reader.readLine();
			String line;
			while ((line = reader.readLine()) != null) {
				parseLineFromFile(result, line);
			}
		} catch (IOException e) {
			log.error("There was problem with file parsing", e);
		}
		return result;
	}

	private void parseLineFromFile(List<PhoneNumber> result, String line) throws FileParseException {
		List<String> splitLine = Arrays.asList(line.split(","));
		if (splitLine.size() == 2) {
			result.add(PhoneNumber.builder().numberId(splitLine.get(0)).number(splitLine.get(1)).build());
		} else {
			throw new FileParseException();
		}
	}
}
