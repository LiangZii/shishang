package com.liang.controller;

import com.liang.config.State;
import com.liang.entity.Star;
import com.liang.lang.Result;
import com.liang.service.StarService;
import com.liang.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/stars")
public class StarController {

    @Autowired
    private StarService starService;

    @PostMapping()
    public Result collect(@RequestBody Star star, HttpServletRequest request){
        //通过token获取userId
        String token = request.getHeader("token");
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
//        设置userId
        star.setUserId(userId);

        System.out.println(star);

        starService.addData(star);

        return Result.success(State.OK,"收藏成功",null);
    }

    @DeleteMapping
    public Result delete(@RequestBody Star star, HttpServletRequest request){
        //通过token获取userId
        String token = request.getHeader("token");
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
//        设置userId
        star.setUserId(userId);

        System.out.println(star);

        starService.deleteData(star);

        return Result.success(State.OK,"取消收藏成功",null);
    }

    @GetMapping
    public Result selectAll(HttpServletRequest request){
        //通过token获取userId
        String token = request.getHeader("token");
        String userId = JwtUtils.getClaimsByToken(token).getSubject();

        System.out.println(userId);

        List<Star> stars = starService.selectAll(userId);

        return Result.success(State.OK,"查询成功",stars);
    }
}
