package it.lei.boot.data.jpa.test;


import it.lei.boot.BaseTest;
import it.lei.boot.data.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本类主要展示了jpa进行分页的全过程
 * @author lei
 *
 */
public class PagebleTest extends BaseTest {

    @Autowired
    private EntityManager entityManager;
    private void  setQueryParams(Query queryParams, Map<String,Object> params){
        for(Map.Entry<String,Object> entry:params.entrySet()){
            queryParams.setParameter(entry.getKey(),entry.getValue());
        }
    }
    private long getQueryCount(StringBuilder baseSql,Map<String,Object> paras){
        Query query = entityManager.createQuery("select count(*) "+baseSql.toString());
        setQueryParams(query,paras);
        Number singleResult = (Number) query.getSingleResult();
        return  singleResult.longValue();
    }
    private List getQueryResult(StringBuilder baseSql, Map<String,Object> paras, Pageable page){
        Query query=entityManager.createQuery("select u "+baseSql.toString());
        setQueryParams(query,paras);
        query.setFirstResult(Integer.parseInt(page.getOffset()+""));
        query.setMaxResults(page.getPageSize());
        List resultList = query.getResultList();
        return resultList;
    }
    @Test
    public void queryUser(){
        Integer userId=24;
        PageRequest pageRequest = PageRequest.of(1, 1);
        StringBuilder baseSql = new StringBuilder("from User u where 1=1 ");
        HashMap<String, Object> paras = new HashMap<>();
        if(userId!=null){
            baseSql.append(" and u.ssmUserId =:ssmUserId ");
            paras.put("ssmUserId",userId);
        }
        //统计总数
        long count=getQueryCount(baseSql,paras);
        //获取数据
        List queryResult = getQueryResult(baseSql, paras, pageRequest);
        //组装page对象
        Page<User> userPage = new PageImpl(queryResult,pageRequest,count);

        System.out.println(userPage);

    }
}