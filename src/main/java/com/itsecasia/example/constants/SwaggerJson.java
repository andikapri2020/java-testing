package com.itsecasia.example.constants;

public class SwaggerJson {

	private SwaggerJson() {
		throw new IllegalStateException("Utility class");
	}

	// Error
	public static final String INTERNAL_SERVER_ERROR_JSON = """
			{
			  "success": false,
			  "message": "Internal Server Error"
			}
			""";

}
