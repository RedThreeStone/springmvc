package it.lei.boot.lucene.test;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
            //设置权重
            StringField filePathTerm = new StringField("filePath", filePath, Field.Store.YES);
            filePathTerm.setBoost(100L);
            document.add(filePathTerm);
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
            Term fileNameTerm = new Term("fileName", "chrome_child.dll.sig");
            Query query = new TermQuery(fileNameTerm);
            //设置权重
            query.setBoost(200L);
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

    /**
     * 范围查找
     * @throws Exception
     */
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

    /**
     * 多条件查找
     * @throws Exception
     */
    @Test
    private void boolQueryTest()throws  Exception{
        String indexPath="D:\\studyDatas\\Lucene\\2";
        DirectoryReader reader=null;
        //获取查找对象
        IndexSearcher indexSearcher = getIndexSearcher(indexPath);
        try {

            Query query = new TermQuery(new Term("fileName","chrome_child.dll.sig"));
            NumericRangeQuery<Long> size = NumericRangeQuery.newLongRange("size", 100L, 100000L, false, true);
            BooleanQuery booleanQuery = new BooleanQuery();
            //设置boost
            size.setBoost(200L);
            booleanQuery.add(size, BooleanClause.Occur.MUST);
            booleanQuery.add(query, BooleanClause.Occur.MUST);
//            1、MUST和MUST表示“与”的关系，即“并集”。
//            2、MUST和MUST_NOT前者包含后者不包含。
//            3、MUST_NOT和MUST_NOT没意义
//            4、SHOULD与MUST表示MUST，SHOULD失去意义；
//            5、SHOUlD与MUST_NOT相当于MUST与MUST_NOT。
//            6、SHOULD与SHOULD表示“或”的概念。


            TopDocs topDocs = indexSearcher.search(booleanQuery, 10);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            //打印内容
            printContent(indexSearcher, scoreDocs);
        }catch (Exception e){
            indexSearcher.getIndexReader().close();
            e.printStackTrace();
        }

    }

    /**
     * parser多域查询
     */
    @Test
    public  void multiFieldQueryParserTest() throws IOException {
        String indexPath="D:\\studyDatas\\Lucene\\2";
        DirectoryReader reader=null;
        //获取查找对象
        IndexSearcher indexSearcher = getIndexSearcher(indexPath);
        try {
            String[] fields = {"fileName", "content"};
            HashMap<String, Float> boostMap = new HashMap<>();
            boostMap.put("fileName",100f);
            boostMap.put("content",200f);
            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_4_10_4,
                    fields,new IKAnalyzer(),boostMap);
            //fileName='6666' or content='6666'
            Query query = queryParser.parse("666");
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
     * 多条件parser查询
     */
    @Test
    public void boolParserTest() throws Exception{
        String indexPath="D:\\studyDatas\\Lucene\\2";
        DirectoryReader reader=null;
        //获取查找对象
        IndexSearcher indexSearcher = getIndexSearcher(indexPath);
        try {
            //第二个参数为默认域
            QueryParser queryParser = new QueryParser(Version.LUCENE_4_10_4,"fileName",new IKAnalyzer());
            String queryString ="fileName:6666 and fileSize:[20 TO 100]" ;
            Query query = queryParser.parse(queryString);
            TopDocs topDocs = indexSearcher.search(query, 10);
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
