package com.liang;

import com.liang.controller.ImageRecognitionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class baiduAITest {

    @Autowired
    private ImageRecognitionController imageRecognitionController;

    @Test
    public void getAccessToken(){
        try {
            imageRecognitionController.getAccessToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
