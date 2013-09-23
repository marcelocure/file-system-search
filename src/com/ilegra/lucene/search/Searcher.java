package com.ilegra.lucene.search;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Searcher {
	private int maxResults = 10;
	public void search(String queryText) {
		try {
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(FSDirectory.open(new File("C:/luceneindexes"))));
			
			QueryParser queryParser = new QueryParser(Version.LUCENE_36, "content", new SimpleAnalyzer(Version.LUCENE_36));
			
			printResults(searcher, searcher.search(queryParser.parse(queryText), maxResults));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void printResults(IndexSearcher searcher, TopDocs topDocs)
			throws CorruptIndexException, IOException {
		ScoreDoc[] hits = topDocs.scoreDocs;
		
		for(ScoreDoc hit : hits) {
			Document doc = searcher.doc(hit.doc);
			System.out.println(doc.get("title"));
			System.out.println(doc.get("content"));
		}
	}
}