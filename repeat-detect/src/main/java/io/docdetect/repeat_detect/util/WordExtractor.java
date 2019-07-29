package io.docdetect.repeat_detect.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordExtractor {
	static public String extract(String wordFile) {
		// Judge file format.
		String suffixName = wordFile.substring(wordFile.length()-4, wordFile.length());
		if ("docx".equals(suffixName)) {
			return extractDocx(wordFile);
		} else {
			return extractDoc(wordFile);
		}
	}
	
	static private String extractDoc(String wordFile) {
		HWPFDocument document = null;
		try {
			File file = Paths.get(new URI(wordFile)).toFile();
			document = new HWPFDocument(new FileInputStream(file));
			return document.getDocumentText();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				document.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static private String extractDocx(String wordFile) {
		XWPFWordExtractor extractor = null;
		try {
			File file = Paths.get(new URI(wordFile)).toFile();
			XWPFDocument document = new XWPFDocument(new FileInputStream(file));
			extractor = new XWPFWordExtractor(document);
			return extractor.getText();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				extractor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
