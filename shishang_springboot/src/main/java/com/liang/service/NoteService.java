package com.liang.service;

import com.liang.entity.Note;

import java.util.List;

public interface NoteService {

    //    添加记录
    void save(Note note);

    //    删除记录
    void delete(String noteId,String userId);

    //    修改记录
    void update(Note note);

    //    查询全部
    List<Note> selectAll(String userId);


}
