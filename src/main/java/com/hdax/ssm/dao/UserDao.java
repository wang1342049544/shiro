package com.hdax.ssm.dao;

import com.hdax.ssm.entity.User;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.security.Permission;
import java.util.List;

public interface UserDao <T, PK extends Serializable> {
   User Find(@Param("username") String username);


}
