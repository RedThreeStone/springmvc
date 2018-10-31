package it.lei.boot.lucene.test;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
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

    /**
     * termquery精确查找
     */
    private void termQueryTest() throws IOException {
        String indexPath="D:\\studyDatas\\Lucene\\2";
        DirectoryReader reader=null;
        //获取查找对象
        IndexSearcher indexSearcher = getIndexSearcher(indexPath);
        try {
            //精确匹配类
            Query query = new TermQuery(new Term("fileName","chrome_child.dll.sig"));
            TopDocs topDocs = indexSearcher.search(query, 10);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            //打印内容
            printContent(indexSearcher, scoreDocs);
        }catch (Exception e){
            indexSearcher.getIndexReader().close();
            e.printStackTrace();
        }
    }

    /**
     * 查询所有
     * @throws IOException
     */
    private void  allQuery()throws  IOException{
        String indexPath="D:\\studyDatas\\Lucene\\2";
        DirectoryReader reader=null;
        //获取查找对象
        IndexSearcher indexSearcher = getIndexSearcher(indexPath);
        try {
            //查询所有类
            MatchAllDocsQuery query = new MatchAllDocsQuery();
            TopDocs topDocs = indexSearcher.search(query, 10);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            //打印内容
            printContent(indexSearcher, scoreDocs);
        }catch (Exception e){
            indexSearcher.getIndexReader().close();
            e.printStackTrace();
        }
    }

    private void  numberQueryTest()throws  Exception{
        String indexPath="D:\\studyDatas\\Lucene\\2";
        DirectoryReader reader=null;
        //获取查找对象
        IndexSearcher indexSearcher = getIndexSearcher(indexPath);
        try {
            //范围查询
            NumericRangeQuery<Long> size = NumericRangeQuery.newLongRange("size", 100L, 100000L, false, true);
            TopDocs topDocs = indexSearcher.search(size, 10);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            //打印内容
            printContent(indexSearcher, scoreDocs);
        }catch (Exception e){
            indexSearcher.getIndexReader().close();
            e.printStackTrace();
        }
    }

    private IndexSearcher getIndexSearcher(String indexPath) throws IOException {
        DirectoryReader reader;
        FSDirectory dir = FSDirectory.open(new File(indexPath));
        reader = DirectoryReader.open(dir);
        return new IndexSearcher(reader);
    }

    private void printContent(IndexSearcher indexSearcher, ScoreDoc[] scoreDocs) throws IOException {
        for(ScoreDoc scoreDoc:scoreDocs){
            //id
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            System.out.println(document.get("filePath"));
            System.out.println(document.get("fileName"));
            System.out.println(document.get("content"));
        }
    }


    private IndexWriter getWriter(String dirPath) throws IOException {
        File file = new File(dirPath);
        FSDirectory directory = FSDirectory.open(file);
        IKAnalyzer ikAnalyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_4,ikAnalyzer);
        return new IndexWriter(directory,config);
    }


}
