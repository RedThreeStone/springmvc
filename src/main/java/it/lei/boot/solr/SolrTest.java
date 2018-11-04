package it.lei.boot.solr;

import it.lei.boot.BaseTest;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SolrTest extends BaseTest {
    @Autowired
    private SolrClient solrClient ;

    @Test
    public void  addTest() throws  Exception{
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","20181104");
        document.addField("product_name","这是我用来测试solr的记录");
        solrClient.add(document);
        solrClient.commit();
    }
    @Test
    public void deleteTest() throws Exception{
        solrClient.deleteById("20181104");
        solrClient.deleteByQuery("id:20181104");
        solrClient.commit();
    }
    @Test
    public  void  queryTest() throws  Exception{
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.setQuery("id:20181104");
        QueryResponse query = solrClient.query(solrQuery);
        SolrDocumentList results = query.getResults();
        for(SolrDocument document:results){
            System.out.println(document);
        }
    }
    @Test
    public void  queryByClaseTest() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery("product_name:小黄人");
        query.addFilterQuery("product_catalog:17");
        query.addFilterQuery("product_catalog_price:[10 TO 100]");
        query.addSort("product_catalog_price", SolrQuery.ORDER.desc);
        query.setStart(0);
        query.setRows(10);
        query.setFields("product_catalog,product_catalog_price,id,product_name,product_keywords");
        query.set("df","product_keywords");
        query.setHighlight(true);
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePre("</em>");
        query.addHighlightField("product_name");
        QueryResponse response = solrClient.query(query);
        SolrDocumentList results = response.getResults();
        // 获取匹配的数目
        System.out.println(results.getNumFound());
        //   id          字段名
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (SolrDocument solrDocument :results){
            String id = (String) solrDocument.getFieldValue("id");
            Map<String, List<String>> listMap = highlighting.get(id);
            List<String> product_names = listMap.get("product_name");
            if(product_names!=null){
                for (String product_name:product_names){
                    System.out.println("高亮:"+product_name);
                }
            }
        }

    }
}
