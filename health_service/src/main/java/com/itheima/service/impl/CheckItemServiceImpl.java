package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;


    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }


    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        // 条件处理，实现的是模糊，
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            // 查询条件不为空，null ""
            // 拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
            System.out.println(queryPageBean.getCurrentPage());
        }
        // 使用分页插件
        //第二种，Mapper接口方式的调用，推荐这种使用方式。
        // 第一个参数是页码，第二个参数是大小
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 紧接着的查询语句会被分页
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        // page.getResult获取分页的结果集
        return new PageResult<CheckItem>(page.getTotal(), page.getResult());
    }


    @Override
    public void deleteById(int id) {
        // 查询检查项的ID是否被检查组引用
        int count = checkItemDao.findCountById(id);
        if (count > 0) {
            // 被引用了
            // 抛出自定义异常
            throw new HealthException(MessageConstant.DELETE_CHECKITEM_FAIL_USED);
        }
        // 删除
        checkItemDao.deleteById(id);
    }


    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }


    @Override
    @Transactional
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
