package com.roger.springcloudGreenwich.service.impl;

import com.roger.springcloudGreenwich.User;
import com.roger.springcloudGreenwich.mapper.UserMapper;
import com.roger.springcloudGreenwich.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2019/10/31.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUsers(User user) {
        log.info("query user");
        return userMapper.selectUsers(user);
    }

    @Override
    public void addUser(User user) {
        log.info("query user");
        userMapper.addUser(user);
    }
}
