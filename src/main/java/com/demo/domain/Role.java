package com.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor //NoArgsConstructor를 왜 하는지??????????????????????????????
@AllArgsConstructor
@Data
public class Role {
    private int id;
    private String role;
    private String role_name;

}
