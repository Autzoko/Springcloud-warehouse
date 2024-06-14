package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.mapper.UserMapper;
import com.example.demo.po.UserDo;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    /**
     * 测试单条查询
     */
    @Test
    public void testSelectOne() {
        UserDo user = (UserDo) userMapper.selectById(1L);
        System.out.println(user);
    }

    /**
     * 测试新增
     */
    @Test
    public void testInsert() {
        UserDo user = new UserDo();
        user.setName("kk");
        user.setAge(3);
        user.setEmail("kk@qq.com");
        assertThat(userMapper.insert(user)).isGreaterThan(0);
        assertThat(user.getId()).isNotNull();
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        assertThat(userMapper.deleteById(3L)).isGreaterThan(0);
        assertThat(userMapper.delete(new QueryWrapper<UserDo>()
                .lambda().eq(UserDo::getName, "kk"))).isGreaterThan(0);
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        UserDo user = (UserDo) userMapper.selectById(2);
        assertThat(user.getAge()).isEqualTo(20);
        assertThat(user.getName()).isEqualTo("Jack");

        userMapper.update(
                null,
                Wrappers.<UserDo>lambdaUpdate().set(UserDo::getEmail, "zz@qq.com").eq(UserDo::getId, 1)
        );
        assertThat(userMapper.selectById(2).getClass()).isEqualTo("11@qq.com");
    }

    /**
     * 测试查询列表
     */
    @Test
    public void testSelect() {
        List<UserDo> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
    }

    /**
     * 测试分页查询
     */
    @Test
    public void testPage() {
        Page<UserDo> page = new Page<>(1, 2);
        IPage<UserDo> userIPage = userMapper.selectPage(page, new QueryWrapper<UserDo>()
                .gt("age", 1));
        assertThat(page).isSameAs(userIPage);
        System.out.println("总条数: " + ((IPage<?>) userIPage).getTotal());
        System.out.println("当前页数: " + userIPage.getCurrent());
        System.out.println("当前每页显示数: " + userIPage.getSize());
        System.out.println("记录列表: " + userIPage.getRecords());
    }

}
