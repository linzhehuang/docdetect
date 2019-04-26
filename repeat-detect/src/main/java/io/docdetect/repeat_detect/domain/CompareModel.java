package io.docdetect.repeat_detect.domain;

public class CompareModel {
	private String[] files;
	private float result;
	public String[] getFiles() {
		return files;
	}
	public void setFiles(String[] files) {
		this.files = files;
	}
	public float getResult() {
		return result;
	}
	public void setResult(float result) {
		this.result = result;
	}
}
