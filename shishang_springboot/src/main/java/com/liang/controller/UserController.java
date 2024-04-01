package com.liang.controller;

import com.liang.config.State;
import com.liang.entity.User;
import com.liang.entity.UserLoginReturn;
import com.liang.lang.Result;
import com.liang.service.UserService;
import com.liang.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService
            userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        System.out.println(user);
        User result = userService.login(user.getUserId(), user.getPassword());
        String token = JwtUtils.generateToken(user.getUserId());
        UserLoginReturn loginReturn = new UserLoginReturn(result.getUserId(), result.getName(), result.getHead(), token);
        return Result.success(State.OK, "登陆成功", loginReturn);
    }

    @PostMapping("/loginByPhone")
    public Result loginByPhone(@RequestBody User user) {
        System.out.println(user);
        User result = userService.loginByPhone(user);
        String token = JwtUtils.generateToken(user.getUserId());
        UserLoginReturn loginReturn = new UserLoginReturn(result.getUserId(), result.getName(), result.getHead(), token);
        return Result.success(State.OK, "登陆成功", loginReturn);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        System.out.println(user);
        userService.register(user);
        return Result.success(State.OK, "注册成功", null);
    }

    @GetMapping("/number")
    public Result starAndNoteNum(HttpServletRequest request) {
        //通过token获取userId
        String token = request.getHeader("token");
        String userId = JwtUtils.getClaimsByToken(token).getSubject();

        String data = userService.selectStarAndNoteNum(userId);

        return Result.success(State.OK, "查询成功", data);
    }

    @PostMapping("/updateHead")
    public Result updateHead(MultipartFile photo, HttpServletRequest request) throws IOException {
        String path = request.getServletContext().getRealPath("/headPhoto/");

        //通过token获取userId
        String token = request.getHeader("token");
        String userId = JwtUtils.getClaimsByToken(token).getSubject();

        saveFile(userId, photo, path);

        return Result.success(State.OK, "修改成功", userId + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")));
    }

    public void saveFile(String userId, MultipartFile photo, String path) throws IOException {
//        判断目录是否存在，不存在创建目录
        File dir = new File(path);
        if (!dir.exists()) {
//            创建目录
            dir.mkdir();
        }

        File file = new File(path + userId + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")));
        photo.transferTo(file);
        userService.updateHead(userId, userId + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")));
    }

    @PostMapping("/updateNameById")
    public Result updateNameById(@RequestParam(name = "userId") String userId, @RequestParam(name = "name") String name) {
        userService.updateNameById(userId, name);


        return Result.success(State.OK, "修改成功", "");
    }

    @PostMapping("/updatePwdById")
    public Result updateById(@RequestParam(name = "userId") String userId, @RequestParam(name = "password") String password) {
        userService.updatePwdById(userId, password);

        return Result.success(State.OK, "修改成功", "");
    }

}
