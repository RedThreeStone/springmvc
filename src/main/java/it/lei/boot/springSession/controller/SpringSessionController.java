package it.lei.boot.springSession.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/springSession")
public class SpringSessionController {

    @RequestMapping("/putSession")
    public @ResponseBody String putSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(session.getClass());
        System.out.println(session.getId());
        session.setAttribute("user","黄磊");

        return "hey,huangl";
    }
}
