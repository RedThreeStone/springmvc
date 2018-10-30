package it.lei.boot.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import it.lei.boot.BaseTest;
import it.lei.boot.mongodb.domain.BaiKe;
import it.lei.boot.mongodb.service.MongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Date;

public class Test extends BaseTest {
    @Autowired
    private MongoDbService mongoDbService;

    @org.junit.Test
    @Rollback(value = false)
    public  void  addTest(){
        BaiKe baiKe = new BaiKe();
        baiKe.setId("springBoot");
        baiKe.setDesc("超便捷的spring开发框架");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("开源的");
        tags.add("容易入门");
        baiKe.setTag(tags);
        baiKe.setCreateDate(new Date());
        baiKe.setUpdateDate(new Date());
        mongoDbService.addBaiKe(baiKe);
    }
    @org.junit.Test
    @Rollback(value = false)
    public  void  findByIdTest(){
        BaiKe springBoot = mongoDbService.findBaiKeById("springBoot");
        System.out.println(springBoot);
    }
    @org.junit.Test
    @Rollback(value = false)
    public  void  updateByIdTest(){
        BaiKe baiKe = new BaiKe();
        baiKe.setId("springBoot");
        UpdateResult result = mongoDbService.updateBaikeById(baiKe);
        System.out.println("---------------------------"+result.getMatchedCount());
    }
    @org.junit.Test
    @Rollback(value = false)
    public  void  deleteByIdTest(){
        DeleteResult result = mongoDbService.deleteBaiKeById("springBoot");
        System.out.println("---------------------------"+result.getDeletedCount());
    }
}
