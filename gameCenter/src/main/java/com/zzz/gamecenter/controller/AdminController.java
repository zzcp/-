package com.zzz.gamecenter.controller;

import com.zzz.gamecenter.bean.Result;
import com.zzz.gamecenter.common.Constants;
import com.zzz.gamecenter.service.AdminService;
import com.zzz.gamecenter.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/admin/login")
    public Result adminLogin(@RequestParam("adminAcc") String adminAcc,
                             @RequestParam("adminPwd") String adminPwd){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,adminService.adminLogin(adminAcc, adminPwd));
    }
}
