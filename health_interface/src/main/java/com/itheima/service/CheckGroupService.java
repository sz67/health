package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    CheckGroup findById(int id);

    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteByCheckGroupId(int id);

    void deleteCheckGroupById(int id);

    List<CheckGroup> findAll();
}
