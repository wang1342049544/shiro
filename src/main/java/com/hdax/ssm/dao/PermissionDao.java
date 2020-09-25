package com.hdax.ssm.dao;
import com.hdax.ssm.entity.Permission;

import java.util.List;
public interface PermissionDao {

    List<Permission> queryByPermissionIds(List<Integer> ids);


}
