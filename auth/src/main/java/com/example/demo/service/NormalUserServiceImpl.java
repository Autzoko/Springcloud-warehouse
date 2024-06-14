package com.example.demo.service;

//项目自建的实体和对应的映射
import com.example.demo.mapper.NormalUserMapper;
import com.example.demo.po.NormalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class NormalUserServiceImpl implements NormalUserService {
    @Autowired
    private NormalUserMapper userMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    @Override
    public boolean sendMimeMail(String email) {
        if (isEmailTaken(email)){
            return false;
        }
        NormalUser normalUser = new NormalUser();
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("验证码邮件");
            String code = randomCode();
            normalUser.setEmail(email);
            normalUser.setCode(code);
            mailMessage.setText("您收到的验证码是：" + code);
            mailMessage.setTo(email);
            mailMessage.setFrom(from);
            mailSender.send(mailMessage);
            userMapper.addUser(normalUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }


    @Override
    public void addUser(NormalUser normalUser) {
        String email = normalUser.getEmail();
        String newCode = normalUser.getCode();
        String password = normalUser.getPassword();
        String code = userMapper.getByEmail(email).getCode();
        if(newCode.equals(code)) {
            String userId = generateUniqueUserId();
            normalUser.setUid(userId);
            normalUser.setPassword(password);
            normalUser.setCreatetime(LocalDateTime.now());
            userMapper.updateUserByEmail(normalUser);
        }
    }


    private String generateUniqueUserId() {
        String userId;
        boolean exists;
        do {
            UUID uuid = UUID.randomUUID();
            long uuidDecimal = Math.abs(uuid.getMostSignificantBits()); // 取UUID的高64位作为长整型
            userId = String.valueOf(uuidDecimal).substring(0, 8); // 将长整型转换为字符串，并截取前八位
            exists = userMapper.existsById(userId);
        } while (exists);
        return userId;
    }


    @Override
    public NormalUser getUserByEmail(String email) {
        return userMapper.getByEmail(email);
    }
    @Override
    public boolean isEmailTaken(String email) {
        NormalUser normalUser = getUserByEmail(email);
        return normalUser != null;
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        NormalUser normalUser = getUserByEmail(email);
        if (normalUser != null && normalUser.getPassword().equals(password)) {
            // 更新最后登录时间
            userMapper.updateLastLoginTime(normalUser.getEmail(), LocalDateTime.now()); // 使用用户邮箱来更新最后登录时间
            return true;
        }
        return false;
    }
}
