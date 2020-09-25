package com.hdax.ssm.service.impl;

import com.hdax.ssm.dao.UserDao;
import com.hdax.ssm.entity.User;
import com.hdax.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Permission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username) throws RuntimeException {
        return  userDao.Find(username);
    }



}
