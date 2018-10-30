package it.lei.boot.data.jdbc.test;

import it.lei.boot.BaseTest;
import it.lei.boot.mvc.ErrorHandler.ErrorAction.ErrorAction;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import javax.servlet.http.Cookie;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//待测试controller,可以是多个
@WebMvcTest(ErrorAction.class)
public class MockTest extends BaseTest {
    private MockMvc mockMvc;

    @Test
    public  void  testRequst() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/errorTestJson/{id}","1")
        .param("name","xiaoming"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/errorTestJson/{id}","1")
                .param("name","xiaoming"))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/errorTestJson")
                .file("file","666".getBytes("UTF-8")));
        //map构造参数
        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        ArrayList<String> params = new ArrayList<>();
        params.add("leizi");
        multiValueMap.put("name",params);
        mockMvc.perform(post("/errorTestJson").params(multiValueMap));
        //在session设置值
        mockMvc.perform(post("/errorTestJson").sessionAttr("name","leizi"));
        //模拟cookie值
        mockMvc.perform(post("/errorTestJson").cookie(new Cookie("name","leizi")));
        //设置请求头
        mockMvc.perform(post("/errorTestJson").contentType("application/x-www-form-urlencoded")
        .accept("application/json")
        .header("name","lei"));
        //返回的modelAndView数据监测
        mockMvc.perform(post("/errorTestJson"))
                .andExpect(view().name("/success.html"))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attribute("name","leizi"))
                .andExpect(forwardedUrl("/success"))
                .andExpect(redirectedUrl("/success"))
                .andExpect(content().json("{'name':'lei'}"));

    }
}

