package com.itheima.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckGroupServiceImpl
 * @Description TODO
 * @Author
 * @Date 2019/7/21 10:54
 * @Version 1.0
 **/
@Service(interfaceClass = CheckGroupService.class)

public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 新增检查组
        checkGroupDao.add(checkGroup);
        // 获取检查的编号
        Integer checkGroupId = checkGroup.getId();
        // 遍历检查项的ID集合，循环插入
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
    }


     // 分页查询

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        // 模糊查询，拼接%%
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 使用pageHelper
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 分页
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(), page.getResult());
    }


     //通过编号查询检查组信息

    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }


     //通过检查组查询选中的检查项编号

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }


     //更新查检组信息

    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 更新检查组信息
        checkGroupDao.update(checkGroup);
        // 获得检查组的编号
        Integer checkGroupId = checkGroup.getId();
        // 维护关系，先删除后添加
        checkGroupDao.deleteByCheckGroupId(checkGroupId);
        // 再添加关系
        if(null != checkitemIds){
            // 循环添加新关系
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
    }

    @Override
    public void deleteByCheckGroupId(int id) {
        // 删除
        checkGroupDao.deleteByCheckGroupId(id);
    }

    @Override
    public void deleteCheckGroupById(int id) {
        checkGroupDao.deleteCheckGroupById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
