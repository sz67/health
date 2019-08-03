package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Package;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PackageDao {
    /**
     * 添加套餐
     * @param pkg
     */
    void add(Package pkg);

    /**
     * 添加套餐与检查组的关系
     * @param pkgId
     * @param checkgroupId
     */
    void addPackageAndCheckGroup(@Param("pkgId") Integer pkgId, @Param("checkgroupId") Integer checkgroupId);

    Page<Package> findByCondition(String queryString);

    Package findById(int id);

    List<Integer> findPackageById(int checkPackageId);

    List<Package> findAll();

    Package getPackageDetail(int id);

    List<Map<String,Object>> findPackageCount();
}
