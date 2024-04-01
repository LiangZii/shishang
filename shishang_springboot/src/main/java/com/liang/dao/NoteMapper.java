package com.liang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.entity.Note;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoteMapper extends BaseMapper<Note> {
}
