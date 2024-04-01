package com.liang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Star {
    @TableId(type = IdType.AUTO)
    private Integer starId;
    private String userId;
    private String text;
    private String imgUrl;
    private String ingredient;
    private String score;
    private String toDetailSrc;
}
