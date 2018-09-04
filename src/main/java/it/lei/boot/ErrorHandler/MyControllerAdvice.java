package it.lei.boot.ErrorHandler;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 被controllerAdvice注解的类相当于在其他controller外包裹了一层代码
 */
@ControllerAdvice
public class MyControllerAdvice{
    @ModelAttribute
    public  void  addAttribute(Model model){
        model.addAttribute("title","mvc学习");
    }
    @InitBinder
    public void  initBinder(WebDataBinder webDataBinder){
        webDataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }
    /**
     * 这里接收所有的controller的异常,实际开发中可以自定义接收的异常,这里处理之后将不会有异常抛出了,会影响BasicErrorController,由此可见这个在basicError之前执行
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public @ResponseBody Map jsonErrorHandler(Exception e){
        Map map = new HashMap();
        map.put("message",e.getMessage());
        map.put("code","100");
        return  map;
    }
}
