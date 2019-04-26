package io.docdetect.repeat_detect.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apdplat.word.segmentation.Segmentation;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.SegmentationFactory;
import org.apdplat.word.segmentation.Word;

public class Tokenizer {
	private Segmentation segmentation = null;
	
	public void load() {
		segmentation = SegmentationFactory
				.getSegmentation(SegmentationAlgorithm.MaxNgramScore);
		segmentation.seg("loading...");
	}
	
	public InputStream seg(String text) {
		StringBuffer buf = new StringBuffer();
		for (Word word : segmentation.seg(text)) {
			buf.append(word.toString()).append(" ");
		}
		return new ByteArrayInputStream(buf.toString().getBytes());
	}
}
