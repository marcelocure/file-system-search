package com.ilegra.lucene.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	public List<File> getFiles(File path) {
		List<File> files = new ArrayList<File>();
		File items[] = path.listFiles();
		System.out.println(items.length+" files found");
		
		for (File i : items) files.add(i);

		return files;
	}
}
