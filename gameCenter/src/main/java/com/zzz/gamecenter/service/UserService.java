package com.zzz.gamecenter.service;

import com.zzz.gamecenter.entity.User;

import java.util.ArrayList;
import java.util.Map;

public interface UserService {
    User findUser(String phone);
    Map<String, Object> userLogin(String phone,String pwd);

//    void addUser(String phone, String pwd,Integer imgSign,String name);
    Map<String,Object> addUser(User user);
    Map<String,Object> findUserByName(String keyword);
    Map<String,Object> updateUser(User user);
    Map<String,Object> deleteUser(int userId);
    Map<String,Object> getUserList(int offset, int limit);

    Map<String,Object> getUserInfo(Long userId);

}
