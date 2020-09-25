package com.hdax.ssm.service.impl;

import com.hdax.ssm.dao.UserDao;
import com.hdax.ssm.dao.UserRoleDao;
import com.hdax.ssm.entity.UserRole;
import com.hdax.ssm.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserRole getUserRoleId(Integer userId) {
        return userRoleDao.getUserRoleId(userId);
    }
}
