package com.itsecasia.example.constants;

public class LogMessage {

	private LogMessage() {
		throw new IllegalStateException("Utility class");
	}

	public static final String LOG_MESSAGE_INVALID_JWT_SIGNATURE = "Invalid JWT signature {}";
	public static final String LOG_MESSAGE_INVALID_JWT_TOKEN = "Invalid JWT token {}";
	public static final String LOG_MESSAGE_JWT_TOKEN_EXPIRED = "JWT token is expired: {}";
	public static final String LOG_MESSAGE_JWT_TOKEN_UNSUPPORTED = "JWT token is unsupported: {}";
	public static final String LOG_MESSAGE_JWT_CLAIMS_EMPTY = "JWT claims string is empty: {}";
	public static final String LOG_MESSAGE_FAILED_TO_START_APP = "Failed to start the application...";
	public static final String LOG_MESSAGE_CANNOT_SET_AUTHENTICATION = "Cannot set user authentication";

}
