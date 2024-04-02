package com.zzz.gamecenter.service;


import java.util.Map;

public interface AdminService {
    Map<String,Object> adminLogin(String adminAcc, String adminPwd);
}
