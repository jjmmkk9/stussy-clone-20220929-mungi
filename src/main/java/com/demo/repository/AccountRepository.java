package com.demo.repository;

import com.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository {

    public int save(User user);
    public User findUserByEmail(String email);

}
