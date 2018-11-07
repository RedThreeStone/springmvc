package it.lei.boot.solr;

import it.lei.boot.BaseTest;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
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
    @Test
    public  void  jdSearchTest() throws IOException, SolrServerException {
        String keyWords="黄";
        int currentPage=0;
        int pageSize=20;
        //分类
        String filter1="15";
        //价格区间
        String filter2="10-80";
        //按照价格排序
        boolean ascPrice=true;
        //关键词及默认域设置
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(keyWords);
        solrQuery.set("df","product_keywords");
        //当前页及每页的大小
        solrQuery.setStart(currentPage);
        solrQuery.setRows(pageSize);
        //过滤条件
        solrQuery.addFilterQuery("product_catalog:"+filter1);
        String[] split = StringUtils.split(filter2, "-");
        solrQuery.addFilterQuery("product_catalog_price:["+Double.parseDouble(split[0])+"TO" +Double.parseDouble(split[1])+"]");

        //排序
        if(ascPrice){
            solrQuery.setSort("product_catalog_price", SolrQuery.ORDER.asc);
        }else {
            solrQuery.setSort("product_catalog_price", SolrQuery.ORDER.desc);
        }

        //显示字段
        solrQuery.set("fl","id,product_keywords,product_catalog_price,product_picture,product_name");

        //高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font style = 'color:red'>");
        solrQuery.setHighlightSimplePre("</font");
        //执行查询
        QueryResponse queryResponse = solrClient.query(solrQuery);
        SolrDocumentList results = queryResponse.getResults();

        long numFound = results.getNumFound();
        ResultModel resultModel = new ResultModel();
        //总数目
        resultModel.setTotalNum((int) numFound);
        //总页数
        int pageTotal= (int) (numFound/pageSize);
        if(numFound%pageSize>0){
            pageTotal++;
        }
        resultModel.setPageTotal(pageTotal);
        //内容
        ArrayList<Product> products = new ArrayList<>();
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for(SolrDocument solrDocument :results){
            Product product = new Product();
            product.setPid((Integer) solrDocument.get("id"));
            product.setKeywords((String) solrDocument.get("product_keywords"));
            product.setPicturePath((String) solrDocument.get("product_picture"));
            product.setPrice((Double) solrDocument.get("product_catalog_price"));
            //如果高亮就给高亮的产品名,不然就给普通的
            Map<String, List<String>> idMap = highlighting.get(solrDocument.get("id"));
            if(idMap.size()>0){
                product.setName((String) solrDocument.get("product_name"));
            }else {
                product.setName(idMap.get("product_name").get(0));
            }
            products.add(product);
        }
    }
}
