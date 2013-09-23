package com.ilegra.lucene.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtil {
	public List<String> getTitles(File file) {
		return getText(file,0);
	}

	public List<String> getContents(File file) {
		return getText(file,1);
	}
	
	private List<String> getText(File file,int index) {
		List<String> values = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file.getCanonicalPath()));
			
			String line = br.readLine();
			while (line != null) {
				String currentLine = line.split(";")[index];
				values.add(currentLine);
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
}
