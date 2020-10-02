package com.hdax.ssm.dao;
import com.hdax.ssm.entity.Permission;
import com.hdax.ssm.pojo.PermissionBean;

import java.util.List;
public interface PermissionDao {

    List<Permission> queryByPermissionIds(List<Integer> ids);

    public List<PermissionBean> query();

    public List<PermissionBean> queryId(long parentid);
}
