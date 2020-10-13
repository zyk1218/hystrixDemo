package com.remember.hystrix.demo.user.serivce;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActivityServiceBulkhead {

    @Autowired
    private RestTemplate restTemplate;

    public String firstLogin(Long id) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://activity/firstLogin", id, String.class);
        return stringResponseEntity.getBody();
    }


    /**
     * 线程池配置的相关属性可以在这个类下找：HystrixThreadPoolProperties
     * @param id
     * @return
     */
    @HystrixCommand(
            threadPoolKey = "registryTimeout",//新建一个线程池，若该线程池已在别处定义则直接使用已定义的。
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize",value = "2"), //核心线程数
                    @HystrixProperty(name = "maxQueueSize",value = "20") //队列最大等待数
            },
            /**
             * 可以在HystrixCommandProperties下查找相关配置
            */
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000") //设置超时时间
            }
    )
    public String registryTimeout(Long id) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://activity/registryTimeout", id, String.class);
        return stringResponseEntity.getBody();
    }

    @HystrixCommand(
            threadPoolKey = "registryError",//新建一个线程池，若该线程池已在别处定义则直接使用已定义的。
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize",value = "1"), //核心线程数
                    @HystrixProperty(name = "maxQueueSize",value = "20") //队列最大等待数
            },
            fallbackMethod = "registryFallback"
    )
    public String registryError(Long id) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://activity/registryError", id, String.class);
        return stringResponseEntity.getBody();
    }

    public String registryFallback(Long id) {
        return "备用方案";
    }

    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "3000"), //时间窗
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "2"),//最少请求数
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),//请求失败率
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "3000")//活动窗口
            }
    )
    public String registryBreak(Long id) {
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://activity/registryError", id, String.class);
        return stringResponseEntity.getBody();
    }
}
