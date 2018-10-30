package it.lei.boot.restful.controller;

import it.lei.boot.data.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class RestController {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private String apiUri="http://192.168.2.38:9901/api";
    public ModelAndView getUser(String userId){
        RestTemplate template = restTemplateBuilder.build();
        String uri=apiUri+"/user/{userId}";
        User user = template.getForObject(uri, User.class,userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        //或者
        ResponseEntity<User> entity = template.getForEntity(uri, User.class, userId);
        User body = entity.getBody();
        HttpHeaders headers = entity.getHeaders();
        return  modelAndView;
    }
    public ModelAndView addUser(User user){
        RestTemplate build = restTemplateBuilder.build();
        String uri=apiUri+"/user/addUser";
        String s = build.postForObject(uri, user, String.class);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("num",s);
        return  modelAndView;
    }
    public ModelAndView getUserListBy(String username){
        RestTemplate restTemplate = restTemplateBuilder.build();
        User user=new User();
        user.setUsername("小明");
        HttpEntity<User> httpEntity = new HttpEntity<>(user);
        ParameterizedTypeReference<List<User>> typeReference = new ParameterizedTypeReference<List<User>>() {
        };
        String uri=apiUri+"/user/getUserList";
        //第一页 最大十个
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, typeReference, 1, 10);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userList",responseEntity.getBody());
        return  modelAndView;


    }
}
