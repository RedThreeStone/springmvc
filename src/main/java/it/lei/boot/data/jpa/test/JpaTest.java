package it.lei.boot.data.jpa.test;

import it.lei.boot.BaseTest;
import it.lei.boot.data.domain.User;
import it.lei.boot.data.jpa.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class JpaTest extends BaseTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void findAllUser(){
        System.out.println(userRepository.findAll());
    }
    @Test
    public void  findAllUserAsc(){
        Sort sort = new Sort(Sort.Direction.DESC, "ssmUserId");
        System.out.println(userRepository.findAll(sort));
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<User> userPage = userRepository.findAll(pageRequest);
        //总页数
        System.out.println(userPage.getTotalPages());
        //总数量
        System.out.println(userPage.getTotalElements());
        //目标内容
        System.out.println(userPage.getContent());
        //模糊查询
        System.out.println(userRepository.findAllByUsernameLikeOrderBySsmUserIdDesc("%黄%"));
        //@Query 自定义查询,并手写orderby 实现分页加排序同时使用
        PageRequest pageRequest1 = PageRequest.of(0, 2, Sort.Direction.DESC,"ssm_user_id");
        System.out.println(userRepository.findUsersByUsernameAndUserAgePagebleAndSort("测试小明账号2",18,pageRequest1));

    }
}
