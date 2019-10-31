package com.roger.springcloudGreenwich.mapper;

import com.roger.springcloudGreenwich.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2019/10/31.
 */
@Component
@Mapper
public interface UserMapper {
    List<User> selectUsers(User user);
}
