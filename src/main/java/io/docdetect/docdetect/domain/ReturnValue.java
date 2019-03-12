package io.docdetect.docdetect.domain;

public class ReturnValue {
	private boolean success = true;
	public ReturnValue(boolean success) {
		this.success = success;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
