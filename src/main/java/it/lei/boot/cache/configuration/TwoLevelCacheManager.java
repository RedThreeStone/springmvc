package it.lei.boot.cache.configuration;
/**
 * @author lei
 */

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;

public class TwoLevelCacheManager extends RedisCacheManager {
    RedisTemplate redisTemplate;

    public TwoLevelCacheManager(RedisTemplate redisTemplate, RedisCacheWriter redisCacheWriter, RedisCacheConfiguration redisCacheConfiguration){
        super(redisCacheWriter,redisCacheConfiguration);
        this.redisTemplate=redisTemplate;
    }

    @Override
    protected Cache decorateCache(Cache cache) {
        return new  RedisAndLocalCache(this,(RedisCache)cache);
    }
    //发布消息
    public void publishMessage(String topicName,String cacheName){
        this.redisTemplate.convertAndSend(topicName,cacheName);
    }

    public void receiver(String name){
        RedisAndLocalCache redisAndLocalCache=(RedisAndLocalCache)this.getCache(name);
        if(redisAndLocalCache!=null){
            redisAndLocalCache.clearLocal();
        }
    }

}
