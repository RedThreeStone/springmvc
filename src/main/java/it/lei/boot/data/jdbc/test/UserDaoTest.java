package it.lei.boot.data.jdbc.test;

import it.lei.boot.BaseTest;
import it.lei.boot.data.jdbc.dao.UserDao;
import it.lei.boot.data.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends BaseTest {
    @Autowired
    private UserDao userDao;
    @Test
    public void  allTest(){
       // System.out.println(userDao.getUserNum());
     //   System.out.println(userDao.getUserByUserId("2"));
       // System.out.println(userDao.getUserByUsername("黄磊"));
      //  System.out.println(userDao.findAllUser());
        User user = new User();
        user.setUsername("测试小明账号");
        user.setPassword("测试小明密码");
        user.setUserAge(18);
        user.setUserSex("男");
        int id=userDao.insertUser(user);
        user.setSsmUserId(id);
        user.setUsername("测试小明账号2");
       userDao.updateUser(user);
       userDao.deleteUserById(id);
    }
    @Test
    public void enumTest(){
        MyEnum color1=MyEnum.RED;
        System.out.println(color1.ordinal());
        System.out.println(MyEnum.getColorByNum(2));
    }
}
