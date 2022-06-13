package com.douglas.SAMC.controller.Exception;

import java.io.Serializable;

public class StandartError implements Serializable {

	private static final long serialVersionUID = 1L;

	private long timestamp;
	private Integer status;
	private String error;

	public StandartError() {
		super();

	}

	public StandartError(long timestamp, Integer status, String error) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
