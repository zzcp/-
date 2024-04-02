package com.zzz.gamecenter.service.impl;

import com.zzz.gamecenter.entity.Admin;
import com.zzz.gamecenter.mapper.AdminMapper;
import com.zzz.gamecenter.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Map<String, Object> adminLogin(String adminAcc, String adminPwd) {
        int isExist = adminMapper.findAdmin(adminAcc);
        Admin adminInfo = adminMapper.adminLogin(adminAcc, adminPwd);
        Map<String,Object> map = new HashMap<>();

        if (isExist==0){
            map.put("msg","该管理员不存在！");
            map.put("code",404);
        }else if (isExist == 1 && adminInfo != null){
            map.put("msg","登录成功");
            map.put("code",200);
            map.put("info",adminInfo);
        }else if (isExist == 1 && adminInfo == null){
            map.put("msg","登录失败，账号或密码错误");
            map.put("code",202);
        }

        return map;
    }
}
