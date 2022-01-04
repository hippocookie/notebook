package com.search;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class IndexingTest {

    private String[] ids = {"1", "2"};

    private String[] unindexed = {"Netherlands", "Italy"};

    private String[] unstored = {"Amsterdam has lots of bridges", "Venice has lots of canals"};

    private String[] text = {"Amsterdam", "Venice"};

    private Directory directory;

    @Before
    public void setup() throws IOException {
        this.directory = new RAMDirectory();

        IndexWriter writer = getWriter();
        for (int i = 0 ; i < ids.length ; i++) {
            Document doc = new Document();
            doc.add(new StringField("id", ids[i], Field.Store.YES));
            doc.add(new StringField("country", unindexed[i], Field.Store.YES));
            doc.add(new TextField("contents", unstored[i], Field.Store.NO));
            doc.add(new TextField("city", text[i], Field.Store.NO));
            writer.addDocument(doc);
        }
        writer.commit();
        writer.close();
    }

    private IndexWriter getWriter() throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(new WhitespaceAnalyzer());
        return new IndexWriter(directory, config);
    }

    public long getHitCount(String fieldName, String searchString) throws IOException {
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        Term term = new Term(fieldName, searchString);
        TermQuery query = new TermQuery(term);
        TopDocs docs = indexSearcher.search(query, 10);
        return docs.totalHits.value;
    }

    @Test
    public void test_count() throws IOException {
        long hitCount = getHitCount("content", "lots");
        Assert.assertEquals(ids.length, hitCount);
    }

    @Test
    public void test_deleteDocument() throws IOException {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term("id", "1"));
        writer.commit();
        long hitCount = getHitCount("content", "lots");
        Assert.assertEquals(1, hitCount);
        writer.close();
    }

    @Test
    public void test_updateDocument() throws IOException {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", "1", Field.Store.YES));
        doc.add(new StringField("country", "Netherlands", Field.Store.YES));
        doc.add(new TextField("contents", "test contents", Field.Store.NO));
        writer.updateDocument(new Term("id", "1"), doc);
        writer.close();
    }
}
