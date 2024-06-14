package com.example.demo.service;

import com.example.demo.po.NormalUser;
import com.example.demo.po.UserPage;
//实体类

import java.util.List;//使用List，要引入的库

public interface NormalUserService  {

    boolean sendMimeMail(String email);

    void addUser(NormalUser normalUser);

    NormalUser getUserByEmail(String email);

    boolean isEmailTaken(String email);

    boolean authenticateUser(String email, String password);

}
