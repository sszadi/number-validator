package com.olx.number;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
class PhoneNumberStatisticsCollector {
	Statistics calculateStatistics(List<PhoneNumber> numbers) {
		Statistics statistics = Statistics.builder()
			.fileIdentifier(UUID.randomUUID().toString())
			.createdAmount(numbers.size())
			.validAmount(Math.toIntExact(numbers.stream().filter(n -> Status.VALID.equals(n.getStatus())).count()))
			.fixedAmount(Math.toIntExact(numbers.stream().filter(n -> Status.FIXED.equals(n.getStatus())).count()))
			.invalidAmount(Math.toIntExact(numbers.stream().filter(n -> Status.INVALID.equals(n.getStatus())).count()))
			.numberList(numbers)
			.build();
		log.debug("Calculated statistics= {}", statistics);
		return statistics;
	}

}
