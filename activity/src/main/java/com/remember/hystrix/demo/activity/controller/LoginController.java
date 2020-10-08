package com.remember.hystrix.demo.activity.controller;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @PostMapping("firstLogin")
    String LoginService(@RequestBody Long id) {
        System.out.println("注册成功");
        return "succ";
    }

    @PostMapping("registryTimeout")
    String registryTimeout(@RequestBody Long id) {
        try {
            TimeUnit.SECONDS.sleep(RandomUtils.nextInt(5)+1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "succ";
    }


    @PostMapping("registryError")
    String registryError(@RequestBody Long id) {
        throw new RuntimeException("注册失败" + id);
    }
}
