package com.liang.service;

import com.liang.entity.Star;

import java.util.List;

public interface StarService {

    void addData(Star star);

    void deleteData(Star star);

    List<Star> selectAll(String userId);

}
