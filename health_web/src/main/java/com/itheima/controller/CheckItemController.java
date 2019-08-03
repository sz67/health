package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @PostMapping("/add.do")

    //@RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody CheckItem checkItem) {
        // 调用业务服务
        checkItemService.add(checkItem);
        // 返回结果给前端
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findPage.do")

    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        // 调用业务服务查询
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        // 返回给浏览器
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    @PostMapping("/deleteById.do")

    public Result deleteById(int id) {
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @GetMapping("/findById.do")

    public Result findById(int id) {
        CheckItem checkitem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkitem);
    }

    @PostMapping("/update.do")

    //@RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result update(@RequestBody CheckItem checkItem) {
        // 调用业务服务更新
        checkItemService.update(checkItem);
        // 返回结果给前端
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
    @RequestMapping("/findAll.do")

    public Result findAll(){
        List<CheckItem> checkItemList = checkItemService.findAll();
        if(checkItemList != null && checkItemList.size() > 0){
            Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS);
            result.setData(checkItemList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }
}
