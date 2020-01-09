package com.olx.number;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
class PhoneNumberUploadResponse {

	private List<PhoneNumber> result;
	private Statistics statistics;
}
