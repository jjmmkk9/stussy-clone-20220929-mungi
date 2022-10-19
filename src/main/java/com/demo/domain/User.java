package com.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD

=======
import org.apache.tomcat.jni.Local;

import javax.validation.constraints.NotBlank;
>>>>>>> af23d26 (AccountRepository, mappers, Service,)
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private int id;
    private String username;
    private String oauth_username;
    private String password;

    private String name;
    private String email;
    private String provider;
    private int role_id;//join해서 들고와야됨
    private Role role;

    private LocalDateTime create_date;
    private LocalDateTime update_date;
}
