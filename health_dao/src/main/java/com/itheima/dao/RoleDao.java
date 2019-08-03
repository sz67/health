package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.Set;

public interface RoleDao {
    /**
     * 根据uid查询角色
     * @param uid
     * @return
     */
    Set<Role> findByUid(Integer uid);
}
