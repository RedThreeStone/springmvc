package it.lei.boot.mvc.valid.action;

import it.lei.boot.mvc.valid.NormalValid;
import it.lei.boot.mvc.valid.domain.ValidUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ValidTestAction  {
    @RequestMapping("/validTest")
    @ResponseBody
    public String testValid(@Validated({NormalValid.class}) ValidUser validUser, BindingResult result){
        System.out.println(validUser);
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
        }
        return "1111";
    }
    @RequestMapping("/viewSource")
    @ResponseBody
    public String viewSource(){
        return "111";
    }
}
