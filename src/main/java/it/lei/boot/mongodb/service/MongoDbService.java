package it.lei.boot.mongodb.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import it.lei.boot.mongodb.domain.BaiKe;

public interface MongoDbService {
    /**
     * 新增一条百科记录
     * @return
     */
    BaiKe addBaiKe(BaiKe baiKe);

    /**
     * 更新一条百科记录
     * @return
     */
    UpdateResult updateBaikeById(BaiKe baiKe);

    /**
     * 根据id获取一条百科记录
     * @return
     */
    BaiKe findBaiKeById(String  id);
    /**
     * 根据id删除一条百科记录
     * @return
     */
    DeleteResult deleteBaiKeById(String  id);
}
