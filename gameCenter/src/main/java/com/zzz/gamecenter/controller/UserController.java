package com.zzz.gamecenter.controller;

import com.zzz.gamecenter.bean.Result;
import com.zzz.gamecenter.common.Constants;
import com.zzz.gamecenter.entity.Game;
import com.zzz.gamecenter.entity.User;
import com.zzz.gamecenter.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public Result login(@RequestParam("phone") String phone,
                        @RequestParam("pwd") String pwd){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,userService.userLogin(phone, pwd));
    }

    @PostMapping("/register")
    public Result register(@Param("User") User user){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,userService.addUser(user));
    }


    @GetMapping("/user/list")
    public Result getUserList(
                              @RequestParam(name = "offset",defaultValue = "0") int offset,
                              @RequestParam(name = "limit",defaultValue = "40") int limit){
        limit = limit>100?100:limit;
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,userService.getUserList(offset,limit));
    }

    @GetMapping("/user/find")
    public Result findUser(@RequestParam("keyword") String keyword){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,userService.findUserByName(keyword));
    }

    @GetMapping("/user/info")
    public Result getUserInfo(@RequestParam("userId") Long userId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,userService.getUserInfo(userId));
    }

    @PostMapping("/user/update")
    public Result updateUser(@Param("User") User user){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,userService.updateUser(user));
    }

    @PostMapping("/user/add")
    public Result addUser(@Param("User") User user){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,userService.addUser(user));
    }

    @DeleteMapping("/user/delete")
    public Result deleteUser(@RequestParam("userId") int userId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,userService.deleteUser(userId));
    }
}
