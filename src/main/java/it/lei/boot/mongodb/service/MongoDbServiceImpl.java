package it.lei.boot.mongodb.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import it.lei.boot.mongodb.domain.BaiKe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "mongoDbService")
public class MongoDbServiceImpl implements MongoDbService {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public BaiKe addBaiKe(BaiKe baiKe) {
        mongoTemplate.save(baiKe,"baike");
        return  baiKe;
    }

    @Override
    public UpdateResult updateBaikeById(BaiKe baiKe) {
        Criteria criteria=Criteria.where("_id").is(baiKe.getId());
        Query query = Query.query(criteria);
        Update update = new Update();
        update.set("updateDate",new Date());
        UpdateResult result = mongoTemplate.updateFirst(query, update, BaiKe.class);
        return result;
    }

    @Override
    public BaiKe findBaiKeById(String id) {
        BaiKe baiKe = mongoTemplate.findById(id, BaiKe.class);
        return baiKe;
    }

    @Override
    public DeleteResult deleteBaiKeById(String id) {
        Criteria criteria = Criteria.where("_id").is(id);
        Query query = Query.query(criteria);
        DeleteResult remove = mongoTemplate.remove(query,"baiKe");
        return remove;
    }
}
