package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

public interface PermissionDao {

    /**
     * 根据角色Id查询权限
     * @param roleId
     * @return
     */
    Set<Permission> findByRoleId(Integer roleId);
}
