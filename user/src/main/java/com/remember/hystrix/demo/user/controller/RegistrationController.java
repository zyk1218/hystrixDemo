package com.remember.hystrix.demo.user.controller;

import com.remember.hystrix.demo.user.entity.User;
import com.remember.hystrix.demo.user.serivce.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private ActivityService activityService;



    @PostMapping("registry")
    String registry(@RequestBody User user){
        System.out.println("用户注册成功" + user.getName());
        return activityService.firstLogin(user.getId());

    }


    @PostMapping("registryTimeout")
    String registryTimeout(@RequestBody User user){
        System.out.println("用户注册成功" + user.getName());
        return activityService.registryTimeout(user.getId());
    }

    @PostMapping("registryError")
    String registryError(@RequestBody User user){
        return activityService.registryError(user.getId());
    }
}
