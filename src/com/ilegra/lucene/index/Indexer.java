package com.ilegra.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import com.ilegra.lucene.file.FileManager;
import com.ilegra.lucene.file.FileReaderUtil;

public class Indexer{
	public void index () {
		try {
			IndexWriter writer = getIndexWriter();
			List<File> files = getFileList();
			writer.addDocuments(getDocuments(files));
			
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<Document> getDocuments(List<File> files) {
		List<Document> docs = new ArrayList<Document>();
		for (File f : files) docs.addAll(getDocument(f));
		return docs;
	}

	private void logDocument(File f) {
		try {
			System.out.println("  indexing document "+ f.getCanonicalFile() + " " + f.length() + " bytes");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO - one doc per file row
	private List<Document> getDocument(File f) {
		logDocument(f);
		FileReaderUtil fileReader = new FileReaderUtil();
		
		List<Document> docs = new ArrayList<Document>();
		List<String> titles = fileReader.getTitles(f);
		List<String> contents = fileReader.getContents(f);
		for (int i = 0 ; i < titles.size() ; i++) {
			System.out.println(titles.get(i) + " - " + contents.get(i));
			Document doc = new Document();
			doc.add(new Field("title",titles.get(i),Store.YES,Index.ANALYZED));
			doc.add(new Field("content",contents.get(i),Store.YES,Index.ANALYZED));
			docs.add(doc);
		}
		
		return docs;
	}

	private List<File> getFileList() {
		return new FileManager().getFiles(new File("c:/Temp"));
	}

	private IndexWriter getIndexWriter() throws CorruptIndexException, LockObtainFailedException, IOException {
		System.out.println("creating IndexWriter");
		return new IndexWriter(FSDirectory.open(getPath()),getIndexWriterConfig());
	}

	private IndexWriterConfig getIndexWriterConfig() {
		return new IndexWriterConfig(Version.LUCENE_36, new SimpleAnalyzer(Version.LUCENE_36));
	}

	private File getPath() {
		return new File("c:/luceneindexes");
	}
}