package it.lei.boot.lucene.test;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class LuceneTest {
    @Test
    public void addTest() throws IOException {
        String  dirPath="D:\\LuceneRepository\\firstTest";
        String targetDir="E:\\upload\\ticketsystem";
        IndexWriter writer = getWriter(dirPath);
        File target = new File(targetDir);
        File[] listFiles = target.listFiles();
        for(File file:listFiles){
            Document document = new Document();
            String fileName=file.getName();
            String filePath=file.getAbsolutePath();
            String content = FileUtils.readFileToString(file, "UTF-8");
            long size = FileUtils.sizeOf(file);
            document.add(new StringField("filePath",filePath, Field.Store.YES));
            document.add(new LongField("size",size, Field.Store.YES));
            document.add(new TextField("fileName",fileName, Field.Store.YES));
            document.add(new TextField("content",content, Field.Store.YES));
            writer.addDocument(document);
        }
        writer.close();
    }
    @Test
    public void deleteTest() throws IOException {
        String  dirPath="D:\\LuceneRepository\\firstTest";
        IndexWriter writer = getWriter(dirPath);
        TermQuery termQuery = new TermQuery(new Term("size","8888"));
        writer.deleteDocuments(termQuery);
        writer.close();
    }
    @Test
    public void deleteAllTest() throws IOException{
        String  dirPath="D:\\LuceneRepository\\firstTest";
        IndexWriter writer = getWriter(dirPath);
        writer.deleteAll();
        writer.close();
    }
    @Test
    public void updateTest() throws IOException{
        String  dirPath="D:\\LuceneRepository\\firstTest";
        IndexWriter writer = getWriter(dirPath);
        Document document = new Document();
        document.add(new TextField("name","666", Field.Store.YES));
        writer.updateDocument(new Term("fileName","chrome_child.dll.sig"),document);
        writer.close();
    }

    private IndexWriter getWriter(String dirPath) throws IOException {
        File file = new File(dirPath);
        FSDirectory directory = FSDirectory.open(file);
        IKAnalyzer ikAnalyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_4,ikAnalyzer);
        return new IndexWriter(directory,config);
    }

}
