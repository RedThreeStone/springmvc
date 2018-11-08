package it.lei.boot.cache;
/**
 * @author lei
 */

import it.lei.boot.data.domain.User;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

//提供一个本类默认的的cacheNames
@CacheConfig(cacheNames = "users")
@Service
public class UserService {
    //@Caching(cacheable = {@Cacheable(cacheNames = "plateform:users",key = "#userId")})
    @Cacheable(key = "#userId")
    public User getUserById(Integer userId){
        System.out.println("没有执行缓存哦");
        User user = new User();
        user.setSsmUserId(3);
        user.setUsername("小米");
        user.setUserSex("男");
        return  user;
    }

    //@Caching(put={@CachePut(cacheNames = "plateform:users",key ="#user.ssmUserId" )})
    @CachePut(key ="#user.ssmUserId" )
    public User updateUser(User user){
        return  user;
    }
    @CacheEvict(key = "#userId")
   //@Caching(evict = {@CacheEvict(cacheNames = "plateform:users",key = "#userId")})
    public  void deletUser(int userId){
        System.out.println("清空缓存啦");
    }
}
