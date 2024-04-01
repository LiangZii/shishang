package com.liang.service;

import com.liang.entity.User;
import org.apache.ibatis.annotations.Mapper;

public interface UserService {
    User login(String userId, String password);

    void register(User user);

    User loginByPhone(User user);

    Boolean updateHead(String userId,String photoSrc);

    String selectStarAndNoteNum(String userId);

    boolean updatePwdById(String userId,String password);

    boolean updateNameById(String userId,String name);
}
