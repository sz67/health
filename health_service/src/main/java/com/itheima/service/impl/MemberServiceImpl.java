package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MemberServiceImpl
 * @Description TODO
 * @Author
 * @Date 2019/7/27 15:22
 * @Version 1.0
 **/
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    //根据手机号查询会员
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }
    //新增会员
    public void add(Member member) {
        if(member.getPassword() != null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> list) {
        List<Integer> list1 = new ArrayList<>();
        for(String m : list){
            m = m.replace(".", "-") + "-32";
            Integer count = memberDao.findMemberCountBeforeDate(m);
            list1.add(count);
        }
        return list1;
    }
}
