package com.liang;

import com.liang.entity.Note;
import com.liang.entity.Star;
import com.liang.service.NoteService;
import com.liang.service.StarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class NoteServiceTests {

    @Autowired
    private NoteService noteService;

    @Test
    public void save(){
        Note note = new Note(null,"admin","111","222","2023-02-06 12:01:12",null,null,null,null,null);
        noteService.save(note);
    }

    @Test
    public void addData(){
        Star star = new Star(null,"admin","aa","aa","aa","aa","aa");
//        noteService.addData(star);
    }

}
