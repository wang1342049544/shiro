package com.hdax.ssm.dao;



import java.util.List;

public interface RolePermissionDao {
    List<Integer> getPermissionByRoleId(Integer roleid);
}
