package it.lei.boot.restful.configure;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyRestFul implements RestTemplateCustomizer {
    @Override
    public void customize(RestTemplate restTemplate) {
        OkHttp3ClientHttpRequestFactory requestFactory = (OkHttp3ClientHttpRequestFactory) restTemplate.getRequestFactory();
        /*设置链接时间*/
        requestFactory.setConnectTimeout(1000);
//        读时间
        requestFactory.setReadTimeout(1000);
//        写时间
        requestFactory.setWriteTimeout(1000);
    }
}
