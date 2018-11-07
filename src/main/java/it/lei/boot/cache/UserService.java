package it.lei.boot.cache;
/**
 * @author lei
 */

import it.lei.boot.data.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

//@CacheConfig(cacheNames = "plateform:users")提供一个本类默认的的cacheNames
public class UserService {

    @Cacheable(cacheNames = "plateform:users",key = "#userId")
    //@Caching(cacheable = {@Cacheable(cacheNames = "plateform:users",key = "#userId")})
    public User getUserById(String userId){
        System.out.println("没有执行缓存哦");
        User user = new User();
        user.setSsmUserId(3);
        user.setUsername("小米");
        user.setUserSex("男");
        return  user;
    }
    @CachePut(cacheNames = "plateform:users",key ="#user.ssmUserId" )
    //@Caching(put={@CachePut(cacheNames = "plateform:users",key ="#user.ssmUserId" )})

    public User updateUser(User user){
        return  user;
    }
    @CacheEvict(cacheNames = "plateform:users",key = "#userId")
   //@Cachin
    // g(evict = {@CacheEvict(cacheNames = "plateform:users",key = "#userId")})
    public  void deletUser(String userId){
        System.out.println("清空缓存啦");
    }
}
