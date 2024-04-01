package com.liang.controller;

import com.liang.config.State;
import com.liang.entity.Note;
import com.liang.lang.Result;
import com.liang.service.NoteService;
import com.liang.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public Result publish(@RequestBody Note note, HttpServletRequest request){
        //通过token获取userId
        String token = request.getHeader("token");
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
//        设置userId
        note.setUserId(userId);

        noteService.save(note);

        return Result.success(State.OK,"发布成功",null);
    }

    @PutMapping
    public Result update(@RequestBody Note note, HttpServletRequest request){
        //通过token获取userId
        String token = request.getHeader("token");
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
//        设置userId
        note.setUserId(userId);

        noteService.update(note);

        return Result.success(State.OK,"修改成功",null);
    }

    @DeleteMapping("/{noteId}")
    public Result delete(@PathVariable String noteId,HttpServletRequest request){
        //通过token获取userId
        String token = request.getHeader("token");
        String userId = JwtUtils.getClaimsByToken(token).getSubject();

        noteService.delete(noteId,userId);

        return Result.success(State.OK,"删除成功",null);
    }

    @GetMapping
    public Result selectAll(HttpServletRequest request){
        //通过token获取userId
        String token = request.getHeader("token");
        String userId = JwtUtils.getClaimsByToken(token).getSubject();

        System.out.println(userId);

        List<Note> notes = noteService.selectAll(userId);

        return Result.success(State.OK,"查询成功",notes);
    }



}
