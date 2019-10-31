package com.roger.springcloudGreenwich.service;

import com.roger.springcloudGreenwich.User;

import java.util.List;

/**
 * Created by admin on 2019/10/31.
 */
public interface UserService {
    List<User> selectUsers(User user);

    void addUser(User user);
}
