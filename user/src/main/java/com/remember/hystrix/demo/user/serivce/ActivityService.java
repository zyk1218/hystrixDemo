package com.remember.hystrix.demo.user.serivce;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActivityService {

    @Autowired
    private RestTemplate restTemplate;

    public String firstLogin(Long id) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://activity/firstLogin", id, String.class);
        return stringResponseEntity.getBody();
    }

    /**
     * 可以在HystrixCommandProperties下查找相关配置
     */
    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
            }
    )
    public String registryTimeout(Long id) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://activity/registryTimeout", id, String.class);
        return stringResponseEntity.getBody();
    }

    @HystrixCommand(fallbackMethod = "registryFallback")
    public String registryError(Long id) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://activity/registryError", id, String.class);
        return stringResponseEntity.getBody();
    }

    public String registryFallback(Long id) {
        return "備用方案";
    }
}
