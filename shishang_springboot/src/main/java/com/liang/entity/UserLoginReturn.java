package com.liang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginReturn {
    private String userId;
    private String name;
    private String head;
    private String token;
}
