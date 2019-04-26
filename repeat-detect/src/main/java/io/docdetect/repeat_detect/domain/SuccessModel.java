package io.docdetect.repeat_detect.domain;

public class SuccessModel {
	private boolean success = true;
	public SuccessModel(boolean success) {
		this.success = success;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
