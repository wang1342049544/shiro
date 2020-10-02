package com.hdax.ssm.dao;



import com.hdax.ssm.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionDao {
    List<Integer> getPermissionByRoleId(Integer roleid);


    //根据roleId查询
    public List<Permission> queryByRoleId(long roleId);


    /**
     * 点击分配
     * @param roleId
     */
    public void del(int roleId);
    //保存
    public void save(@Param("roleId") long roleId, @Param("permissionId") long permissionId);
}
