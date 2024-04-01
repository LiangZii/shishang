package com.liang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select COUNT(star_id) from star where user_id = #{userId}")
    String selectStarNum(String userId);

    @Select("select COUNT(note_id) from note where user_id = #{userId}")
    String selectNoteNum(String userId);

//    @Update("update user set password =#{password} where user_id=#{userId}")
//    boolean updatePwdById(@Param("userId") String userId, @Param("password") String password);
//
//    @Update("update user set name=#{name} where user_id=#{userId}")
//    boolean updateNameById(@Param("userId") String userId, @Param("name") String name);

}
