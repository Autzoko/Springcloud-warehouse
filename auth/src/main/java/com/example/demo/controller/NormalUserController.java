package com.example.demo.controller;

//引入项目中的类

import com.example.demo.po.NormalUser;
import com.example.demo.po.Result;
import com.example.demo.po.UserPage;
import com.example.demo.service.NormalUserService;
//注解引用的库
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/user")
public class NormalUserController {
    @Autowired
    private NormalUserService userService;

    @PostMapping("/sendEmail")
    @ResponseBody
    public Result sendEmail(@RequestParam String email) {
        if (!userService.sendMimeMail(email)) {
            return Result.error(201, "邮箱已被占用");
        }
        return Result.success();
    }

    @PostMapping("/register")
    @ResponseBody
    public Result addUser(@RequestBody NormalUser normalUser) {
        userService.addUser(normalUser);
        String code = normalUser.getCode();
        if (!code.equals(userService.getUserByEmail(normalUser.getEmail()).getCode())) {
            return Result.error(201, "验证码错误");
        }
        return Result.success();
    }


}
