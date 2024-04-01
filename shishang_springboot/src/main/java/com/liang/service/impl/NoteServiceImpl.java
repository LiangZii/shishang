package com.liang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liang.config.State;
import com.liang.dao.NoteMapper;
import com.liang.entity.Note;
import com.liang.exception.BusinessException;
import com.liang.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteMapper noteMapper;

    @Override
    public void save(Note note) {
        Integer flag = 0;
//        如果有，先删除，再添加，达到修改的效果
        Note n = noteMapper.selectById(note.getNoteId());
        if(n != null){
            flag = noteMapper.updateById(note);
        }else {
            flag = noteMapper.insert(note);
        }

        if(flag != 1){
            throw new BusinessException(State.ERROR,"错误，请重试");
        }
    }

    @Override
    public void delete(String noteId,String userId) {
        Note note = noteMapper.selectById(noteId);
        if(note.getUserId().equals(userId)){
            int flag = noteMapper.deleteById(noteId);
            if(flag != 1){
                throw new BusinessException(State.ERROR,"错误，请重试");
            }
        }else {
            throw new BusinessException(State.ERROR,"异常操作，已被取消");
        }


    }

    @Override
    public void update(Note note) {
        int flag = noteMapper.updateById(note);
        if(flag != 1){
            throw new BusinessException(State.ERROR,"错误，请重试");
        }
    }

    @Override
    public List<Note> selectAll(String userId) {
        LambdaQueryWrapper<Note> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Note::getUserId,userId);
        lqw.orderByDesc(Note::getPublishDate);
        List<Note> result = noteMapper.selectList(lqw);

        if(result == null){
            throw new BusinessException(State.ERROR,"查询错误，请重试");
        }
        return result;
    }
}
