package com.olx.number.african;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AfricanNumberRules {

	public static final String PHONE_NUMBER_REGEX = "^27[0-9]{9}$";
	static final String NUMBER_WITHOUT_PREFIX = "^[0-9]{9}$";
	static final String NUMBER_WITH_WRONG_PREFIX = "^(?!27)[0-9]{11}$";
	static final String PREFIX = "27";
}
