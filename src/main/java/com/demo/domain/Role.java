package com.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

<<<<<<< HEAD
@NoArgsConstructor //NoArgsConstructor를 왜 하는지??????????????????????????????
=======
@NoArgsConstructor
>>>>>>> af23d26 (AccountRepository, mappers, Service,)
@AllArgsConstructor
@Data
public class Role {
    private int id;
    private String role;
    private String role_name;

}
