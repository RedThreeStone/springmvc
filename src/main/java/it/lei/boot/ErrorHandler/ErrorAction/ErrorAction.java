package it.lei.boot.ErrorHandler.ErrorAction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorAction {
    @RequestMapping("/errorTestJson")
    public @ResponseBody Model errorTestJson(Model model){
        int a=5/0;
        return model;
    }
    @RequestMapping("/errorTestHtml")
    public  ModelAndView errorTestHtml(ModelAndView modelAndView){
        int a=5/0;
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
