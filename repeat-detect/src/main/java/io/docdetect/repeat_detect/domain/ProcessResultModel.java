package io.docdetect.repeat_detect.domain;

import java.util.List;

public class ProcessResultModel {
	private boolean success;
	private List<CompareModel> result;
	public ProcessResultModel(boolean success) {
		this.success = success;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<CompareModel> getResult() {
		return result;
	}
	public void setResult(List<CompareModel> result) {
		this.result = result;
	}
}
