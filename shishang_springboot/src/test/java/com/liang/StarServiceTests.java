package com.liang;

import com.liang.entity.Star;
import com.liang.service.StarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class StarServiceTests {

    @Autowired
    private StarService starService;

    @Test
    public void deleteData(){
        Star star = new Star(null,"admin","aa","aa","aa","aa","aa");
        starService.deleteData(star);
    }

    @Test
    public void addData(){
        Star star = new Star(null,"admin","aa","aa","aa","aa","aa");
        starService.addData(star);
    }

}
