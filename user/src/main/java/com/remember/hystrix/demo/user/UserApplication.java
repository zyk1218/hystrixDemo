package com.remember.hystrix.demo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableHystrix //开启断路器
@SpringCloudApplication //包含了以上的注解
public class UserApplication {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}


	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
