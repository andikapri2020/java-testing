package com.itsecasia.example.dto.responses;

import java.util.List;

public class MessageResponseWithData<T> extends MessageResponse {
    
	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public MessageResponseWithData(boolean success, String message, List<T> data) {
		super(success, message);
		this.data = data;
	}

}
