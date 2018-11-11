package it.lei.boot.cache;

import it.lei.boot.BaseTest;
import it.lei.boot.data.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleCacheTest extends BaseTest {
    @Autowired
    private UserService userService;
    @Test
    public void  cacheTest(){
        User user = new User();
        user.setSsmUserId(3);
        user.setUsername("小米");
        user.setUserSex("男");
        this.userService.updateUser(user);

        User user1 = userService.getUserById(3);
        System.out.println(user1);
        userService.deletUser(3);

        User user2 = userService.getUserById(3);
        System.out.println(user2);

    }
    @Test
    public void  levelTwocacheTest(){
        User user = new User();
        user.setSsmUserId(3);
        user.setUsername("小米");
        user.setUserSex("男");
        this.userService.updateUser(user);

        User user1 = userService.getUserById(3);
        user1 = userService.getUserById(3);
        System.out.println(user1);
        userService.deletUser(3);

        User user2 = userService.getUserById(3);
        System.out.println(user2);

    }
}
