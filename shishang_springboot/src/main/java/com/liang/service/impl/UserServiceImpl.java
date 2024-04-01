package com.liang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.liang.config.State;
import com.liang.dao.UserMapper;
import com.liang.entity.User;
import com.liang.exception.BusinessException;
import com.liang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String userId, String password) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserId, userId);
        lqw.eq(User::getPassword, password);
        User user = userMapper.selectOne(lqw);
        if (user == null) {
            throw new BusinessException(State.ERROR, "用户不存在或密码错误");
        }
        return user;
    }

    @Override
    public User loginByPhone(User user) {
        User result = userMapper.selectById(user.getUserId());
        if (result != null) {
//            用户ID已存在
            return result;
        }else{
            Integer rows = userMapper.insert(user);
            if (rows != 1) {
                throw new BusinessException(State.ERROR, "登录失败，请重试");
            }
        }

        return user;
    }

    @Override
    public void register(User user) {
        User result = userMapper.selectById(user.getUserId());
        if (result != null) {
//            用户ID已存在
            throw new BusinessException(State.ERROR, "用户已存在");
        }

        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new BusinessException(State.ERROR, "注册失败，请重试");
        }
    }

    @Override
    public Boolean updateHead(String userId, String photoSrc) {
        User user = new User();
        user.setUserId(userId);
        user.setHead(photoSrc);

        int flag = userMapper.updateById(user);
        if (flag != 1) {
            throw new BusinessException(State.ERROR, "错误，请重试");
        }
        return true;
    }

    @Override
    public String selectStarAndNoteNum(String userId) {
        String note = userMapper.selectNoteNum(userId);
        String star = userMapper.selectStarNum(userId);

        return "收藏：" + star + "      笔记：" + note;
    }


    public boolean updatePwdById(String userId, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        int flag = userMapper.updateById(user);
        if (flag != 1) {
            throw new BusinessException(State.ERROR, "错误，请重试");
        }
        return true;
    }


    public boolean updateNameById(String userId, String name) {
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        int flag = userMapper.updateById(user);
        if (flag != 1) {
            throw new BusinessException(State.ERROR, "错误，请重试");
        }
        return true;
    }

}
