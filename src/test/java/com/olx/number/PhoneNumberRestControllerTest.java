package com.olx.number;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(PhoneNumberRestController.class)
public class PhoneNumberRestControllerTest {

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void shouldUploadFile() throws Exception {
		// given
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("South_African_Mobile_Numbers.csv")).getFile());
		FileInputStream fi1eInputStream = new FileInputStream(file);
		MockMultipartFile multipartFile = new MockMultipartFile("user-file", "test.csv",
			"text/plain", fi1eInputStream);

		// when // then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/phone-number/upload")
			.file("file", multipartFile.getBytes())
			.characterEncoding("UTF-8"))
			.andExpect(status().isCreated())
			.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void shouldReturnExceptionForEmptyFile() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/phone-number/upload")
			.file("file", null)
			.characterEncoding("UTF-8"))
			.andExpect(status().isNoContent());
	}

	@Test
	public void shouldGetInformationAboutFile() throws Exception {
		// given
		String fileIdentifier = "nananan";
		List<PhoneNumber> numberList = Collections.singletonList(PhoneNumber.builder().number("test").build());
		when(statisticsRepository.findById(fileIdentifier)).thenReturn(
			Optional.of(
				Statistics.builder()
					.numberList(numberList)
					.build())
		);

		// when // then
		mockMvc.perform(get("/api/phone-number/upload/" + fileIdentifier)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()));
	}

	public void shouldTestSingleNumber() throws Exception {
		// given
		String number = "232322323";
		when(phoneNumberService.testSingleNumber(number)).thenReturn(PhoneNumber.builder().build());

		// when // then
		mockMvc.perform(get("/api/phone-number/" + number)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()));
	}


	@Autowired
	private WebApplicationContext webApplicationContext;
	@MockBean
	private PhoneNumberService phoneNumberService;
	@MockBean
	private StatisticsRepository statisticsRepository;
	private MockMvc mockMvc;
}
