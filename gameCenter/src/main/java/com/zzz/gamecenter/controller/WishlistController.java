package com.zzz.gamecenter.controller;

import com.zzz.gamecenter.bean.Result;
import com.zzz.gamecenter.common.Constants;
import com.zzz.gamecenter.entity.Wishlist;
import com.zzz.gamecenter.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class WishlistController {
    @Autowired
    WishlistService wishlistService;
    @GetMapping("/wishlist")
    public Result getOrderById(@RequestParam("userId") Long userId,
                               @RequestParam(name = "limit",defaultValue = "40") int limit,
                               @RequestParam(name = "offset",defaultValue = "0") int offset){
        limit = limit>100?100:limit;
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,wishlistService.getOrderById(userId,offset,limit));
    }


    @GetMapping("/game/pay")
    public Result payGame(@RequestParam("userId") Long userId,@RequestParam("gameIdList") ArrayList<Long> gameIdList){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,wishlistService.payGame(userId,gameIdList));
    }

    @GetMapping("/check/pay")
    public Result checkPay(@RequestParam("userId") Long userId,@RequestParam("gameIdList") ArrayList<Long> gameIdList){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,wishlistService.checkPay(userId,gameIdList));
    }

    @PostMapping("/wishlist/pay")
    public Result payOrder(@RequestParam("userId") Long userId,@RequestParam("gameIdList") ArrayList<Long> gameIdList){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,wishlistService.payOrder(userId,gameIdList));
    }

    @GetMapping("/wishlist/find")
    public Result findOrder(@RequestParam("userId") Long userId,@RequestParam("gameId") Long gameId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,wishlistService.findOrder(userId,gameId));
    }

    @PostMapping("/wishlist/add")
    public Result addOrder(@RequestParam("userId") Long userId,@RequestParam("gameId") Long gameId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,wishlistService.addOrder(userId,gameId));
    }

    @PostMapping("/wishlist/remove")
    public Result removeOrder(@RequestParam("userId") Long userId,@RequestParam("gameId") Long gameId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,wishlistService.removeOrder(userId,gameId));
    }

    @GetMapping("/wishlist/all")
    public Result getAllWishlist(@RequestParam(name = "offset",defaultValue = "0") int offset,
                                 @RequestParam(name = "limit",defaultValue = "40") int limit){
        limit = limit>100?100:limit;
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,wishlistService.getAllWishlist(offset,limit));
    }
}
