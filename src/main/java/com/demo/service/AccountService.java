package com.demo.service;

import com.demo.dto.account.RegisterReqDto;

public interface AccountService {
    public boolean checkDuplicateEmail(String email);
    public boolean register(RegisterReqDto registerReqDto) throws Exception;
}
