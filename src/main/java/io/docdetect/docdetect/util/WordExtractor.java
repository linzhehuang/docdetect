package io.docdetect.docdetect.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordExtractor {
	static public void extract(String wordFile, String outputFile) throws FileNotFoundException, IOException {
		// Judge file format.
		String suffixName = wordFile.substring(wordFile.length()-4, wordFile.length());
		if ("docx".equals(suffixName)) {
			extractDocx(wordFile, outputFile);
		} else {
			extractDoc(wordFile, outputFile);
		}
	}
	
	static private void extractDoc(String wordFile, String outputFile) throws FileNotFoundException, IOException {
		File file = new File(wordFile);
		FileWriter writer = new FileWriter(outputFile);
		HWPFDocument document = new HWPFDocument(new FileInputStream(file));
		writer.write(document.getDocumentText());
		writer.flush();
		writer.close();
		document.close();
	}
	
	static private void extractDocx(String wordFile, String outputFile) throws IOException {
		File file = new File(wordFile);
		FileWriter writer = new FileWriter(outputFile);
		XWPFDocument document = new XWPFDocument(new FileInputStream(file));
		XWPFWordExtractor extractor = new XWPFWordExtractor(document);
		System.out.println(extractor.getText());
		writer.write(extractor.getText());
		writer.flush();
		writer.close();
		extractor.close();
	}
}
