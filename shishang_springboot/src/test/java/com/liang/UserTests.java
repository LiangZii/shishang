package com.liang;

import com.liang.dao.UserMapper;
import com.liang.entity.User;
import com.liang.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void Test() {
        System.out.println(userMapper.selectStarNum("admin"));
    }

    //@Test
    //public  void  test1()
    //{
      //  System.out.println(userUpdateMapper.updateById("1","12345"));
    //}

    @Test
    public void loginByPhoneTest(){
        User user = new User();
        user.setUserId("17343113642");
        user.setName("17343113642");
//        user.setPassword("17343113642");
        user.setRegister("2023-1-17");
//        userService.register(user);
        userService.loginByPhone(user);
    }



}
