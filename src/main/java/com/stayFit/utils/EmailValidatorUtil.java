package com.stayFit.utils;

import org.apache.commons.validator.routines.EmailValidator;

public class EmailValidatorUtil {
	public static boolean isValid(String email) {
		EmailValidator validator = EmailValidator.getInstance();
        return (validator.isValid(email));
	}
}
