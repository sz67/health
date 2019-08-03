package com.itheima.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PackageDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = PackageService.class)
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;

    @Override
    @Transactional
    public void add(Package pkg, Integer[] checkgroupIds) {
        // 添加套餐，插入套餐表
        packageDao.add(pkg);
        // 获取套餐的ID
        Integer pkgId = pkg.getId();
        // 循环遍历检查组的编号，插入关系
        if(null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                packageDao.addPackageAndCheckGroup(pkgId, checkgroupId);
            }
        }
    }
    @Override
    public PageResult<Package> findPage(QueryPageBean queryPageBean) {
        // 模糊查询，拼接%%
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 使用pageHelper
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 分页
        Page<Package> page = packageDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<Package>(page.getTotal(), page.getResult());
    }

    @Override
    public Package findById(int id) {
        return packageDao.findById(id);
    }

    @Override
    public List<Integer> findPackageById(int checkPackageId) {
        return packageDao.findPackageById(checkPackageId);
    }

    @Override
    public List<Package> findAll() {
        return packageDao.findAll();
    }

    @Override
    public Package getPackageDetail(int id) {
        return packageDao.getPackageDetail(id);
    }

    @Override
    public List<Map<String, Object>> findPackageCount() {
        return packageDao.findPackageCount();
    }
}
