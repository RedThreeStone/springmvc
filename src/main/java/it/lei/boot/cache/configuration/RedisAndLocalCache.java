package it.lei.boot.cache.configuration;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class RedisAndLocalCache implements Cache {
    /**
     * 本地缓存提供
     */
    ConcurrentHashMap local =new ConcurrentHashMap<>();
    RedisCache redisCache;
    TwoLevelCacheManager twoLevelCacheManager;
    public RedisAndLocalCache(TwoLevelCacheManager twoLevelCacheManager, RedisCache cache) {
        this.redisCache=cache;
        this.twoLevelCacheManager=twoLevelCacheManager;
    }

    @Override
    public String getName() {
        return redisCache.getName();
    }

    @Override
    public Object getNativeCache() {
        return redisCache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        //先拿一级缓存的
        ValueWrapper wrapper=(ValueWrapper) local.get(key);
        if(wrapper!=null){
            return  wrapper;
        }else {
            wrapper = redisCache.get(key);
            //更新一级缓存
            if(wrapper!=null){
                local.put(key,wrapper);
            }
            return wrapper;
        }
    }

    @Override
    public <T> T get(Object o, Class<T> aClass) {
        return redisCache.get(o,aClass);
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return redisCache.get(o,callable);
    }
    @Override
    public void clear() {
        redisCache.clear();
    }
    @Override
    public void put(Object o, Object o1) {
        redisCache.put(o,o1);
        //通知其他节点更新
        clearOtherJVM();
    }

    @Override
    public ValueWrapper putIfAbsent(Object o, Object o1) {
        clearOtherJVM();
        return redisCache.putIfAbsent(o,o1);
    }

    @Override
    public void evict(Object o) {
        redisCache.evict(o);
        clearOtherJVM();
    }
    protected  void  clearOtherJVM(){
        //小弟们请清空缓存
        twoLevelCacheManager.publishMessage("twoLevelClear",redisCache.getName());
    }
    public  void  clearLocal(){
        System.out.println("啊啊啊啊 我要清空缓存了");
        this.local.clear();
    }
}
