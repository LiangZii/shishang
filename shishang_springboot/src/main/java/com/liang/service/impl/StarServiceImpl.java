package com.liang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liang.config.State;
import com.liang.dao.StarMapper;
import com.liang.entity.Star;
import com.liang.entity.User;
import com.liang.exception.BusinessException;
import com.liang.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarServiceImpl implements StarService {

    @Autowired
    private StarMapper starMapper;

    @Override
    public void addData(Star star) {
        int flag = starMapper.insert(star);
        if(flag != 1){
            throw new BusinessException(State.ERROR,"错误，请重试");
        }
    }

    @Override
    public void deleteData(Star star) {
        LambdaQueryWrapper<Star> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Star::getUserId,star.getUserId());
        lqw.eq(Star::getToDetailSrc,star.getToDetailSrc());
        int flag = starMapper.delete(lqw);
        if(flag != 1){
            throw new BusinessException(State.ERROR,"未找到该收藏记录");
        }
    }

    @Override
    public List<Star> selectAll(String userId) {
        LambdaQueryWrapper<Star> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Star::getUserId,userId);
        lqw.orderByDesc(Star::getStarId);
        List<Star> result = starMapper.selectList(lqw);

        if(result == null){
            throw new BusinessException(State.ERROR,"查询错误，请重试");
        }
        return result;
    }
}
