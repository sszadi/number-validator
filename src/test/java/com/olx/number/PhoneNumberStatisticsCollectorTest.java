package com.olx.number;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(Parameterized.class)
public class PhoneNumberStatisticsCollectorTest {

	@Parameterized.Parameters
	public static Collection input() {
		return Arrays.asList(new Object[][]{
			{
				Arrays.asList(
					PhoneNumber.builder().status(Status.FIXED).build(),
					PhoneNumber.builder().status(Status.INVALID).build()),
				0, 1, 1, 2
			},
			{
				Collections.singletonList(PhoneNumber.builder().status(Status.FIXED).build()),
				0, 1, 0, 1
			},
			{
				Arrays.asList(
					PhoneNumber.builder().status(Status.VALID).build(),
					PhoneNumber.builder().status(Status.VALID).build(),
					PhoneNumber.builder().status(Status.VALID).build()),
				3, 0, 0, 3
			},
			{
				Arrays.asList(
					PhoneNumber.builder().status(Status.VALID).build(),
					PhoneNumber.builder().status(Status.INVALID).build(),
					PhoneNumber.builder().status(Status.FIXED).build()),
				1, 1, 1, 3
			},
		});
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		statisticsCollector = new PhoneNumberStatisticsCollector();
	}

	@Test
	public void shouldCalculateStatistics() {
		// begin

		// when
		Statistics statistics = statisticsCollector.calculateStatistics(numbers);

		// then
		assertEquals(statistics.getCreatedAmount(), createdAmount);
		assertEquals(statistics.getInvalidAmount(), invalidAmount);
		assertEquals(statistics.getValidAmount(), validAmount);
		assertEquals(statistics.getFixedAmount(), fixedAmount);
		assertNotNull(statistics.getFileIdentifier());
	}

	@Parameterized.Parameter
	public List<PhoneNumber> numbers;
	@Parameterized.Parameter(1)
	public int validAmount;
	@Parameterized.Parameter(2)
	public int fixedAmount;
	@Parameterized.Parameter(3)
	public int invalidAmount;
	@Parameterized.Parameter(4)
	public int createdAmount;
	private PhoneNumberStatisticsCollector statisticsCollector;
}
