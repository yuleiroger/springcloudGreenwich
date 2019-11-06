package com.roger.springcloudGreenwich.service.impl;

import com.roger.springcloudGreenwich.User;
import com.roger.springcloudGreenwich.mapper.UserMapper;
import com.roger.springcloudGreenwich.service.UserService;
import com.roger.springcloudGreenwich.util.RedisUtil;
import com.roger.springcloudGreenwich.utils.MD5Util;
import com.roger.springcloudGreenwich.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2019/10/31.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private RedisUtil userServiceRedisUtil;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public long batchTest() throws Exception{
        long begin = System.currentTimeMillis();
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user;
        for (int i = 0; i < 100; i++) {
            Long id = userServiceRedisUtil.generateId("id", 1);
            user = new User();
            user.setId(id);
            user.setUserNo(StringUtil.getRandomString(5));
            user.setPassword(MD5Util.md5Encode("123456"));
            mapper.addUser(user);
            //每 50 条提交一次
            if ((i + 1) % 10 == 0) {
                sqlSession.flushStatements();
            }
        }
        long end = System.currentTimeMillis();
        long period = end - begin;
        log.info("use batch total time is:{}" , period);
        return period;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public long noBatchTest() throws Exception{
        long begin = System.currentTimeMillis();
        User user;
        for (int i = 0; i < 100; i++) {
            Long id = userServiceRedisUtil.generateId("id", 1);
            user = new User();
            user.setId(id);
            user.setUserNo(StringUtil.getRandomString(5));
            user.setPassword(MD5Util.md5Encode("123456"));
            userMapper.addUser(user);
        }
        long end = System.currentTimeMillis();
        long period = end - begin;
        log.info("no use batch total time is:{}" , period);
        return period;
    }

}
