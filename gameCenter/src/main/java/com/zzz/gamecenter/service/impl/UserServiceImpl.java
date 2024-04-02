package com.zzz.gamecenter.service.impl;

import com.zzz.gamecenter.entity.User;
import com.zzz.gamecenter.mapper.UserMapper;
import com.zzz.gamecenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findUser(String phone) {
        return userMapper.findUser(phone);
    }

    @Override
    public Map<String, Object> userLogin(String phone, String pwd) {
        User user = userMapper.findUser(phone);
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> infoMap = new HashMap<>();
        if (user!=null) {
            User userInfo = userMapper.userLogin(phone, pwd);
            if(userInfo!=null){
                infoMap.put("code", 200);
                infoMap.put("msg","登录成功，正在跳转，请稍等！");
                infoMap.put("userInfo", userInfo);
            }else{
                infoMap.put("code",404);
                infoMap.put("msg","登录失败，密码或账号输入错误！");
                infoMap.put("userInfo", null);
            }
        }
        else{
            infoMap.put("code",9402);
            infoMap.put("msg","登录失败，该账号或未注册！");
            infoMap.put("userInfo", null);
        }
        map.put("info",infoMap);
        return map;
    }

    @Override
    public Map<String,Object> addUser(User user) {
        Map<String,Object> map = new HashMap<>();
        Long userPhone = Long.parseLong("0");
        String userName = "";
        try{
            userPhone = Long.parseLong(user.getPhone());
            userName = user.getName();
            if(userName.trim().equals("")){
                map.put("msg","用户名格式错误！");
                return map;
            }
            user.setName(user.getName().trim());

            Map<String,Object> infoMap = new HashMap<>();
//        查找该手机号是否被注册过
            User TepUser = findUser(user.getPhone());
            if (TepUser==null) {
                userMapper.addUser(user);
                infoMap.put("code", 200);
                infoMap.put("msg", "注册成功，正在前往登录页！");
            }
            else{
                infoMap.put("code",404);
                infoMap.put("msg","注册失败，该账号已被注册，请前往登录！");
            }
            map.put("info",infoMap);
        }catch (Exception e){
            map.put("msg","注册失败，手机号格式错误！");
        }finally {
            return map;
        }
    }

    @Override
    public Map<String, Object> getUserInfo(Long userId) {
        User user = userMapper.getUserInfo(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("userInfo",user);
        return map;
    }

    @Override
    public Map<String, Object> findUserByName(String keyword) {
        ArrayList<User> resultList = userMapper.findUserByName(keyword);
        int userNum = userMapper.getUserNum(keyword);
        Map<String,Object> map = new HashMap<>();
        map.put("total",userNum);
        map.put("userList",resultList);
        return map;
    }

    @Override
    public Map<String, Object> updateUser(User user) {
        Map<String,Object> map = new HashMap<>();
        map.put("msg",userMapper.updateUser(user));
        return map;
    }

    @Override
    public Map<String, Object> deleteUser(int userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("msg",userMapper.deleteUser(userId));
        return map;
    }

    @Override
    public Map<String,Object> getUserList(int offset, int limit) {
        ArrayList<User> userList = userMapper.getUserList(offset, limit);
        int userNum = userMapper.getUserNum(null);
        Map<String,Object> map = new HashMap<>();
        map.put("total",userNum);
        map.put("userList",userList);
        return map;
    }
}
