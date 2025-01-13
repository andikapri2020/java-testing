package com.itsecasia.example.constants;

public class ErrorMessage {

	private ErrorMessage() {
		throw new IllegalStateException("Utility class");
	}

	public static final String ERROR_MESSAGE_NOT_FOUND = "Data not found";
	public static final String ERROR_MESSAGE_UNAUTHORIZED = "Unauthorized";
	public static final String ERROR_MESSAGE_INTERNAL_SERVER_ERROR = "Internal Server Error";

}
