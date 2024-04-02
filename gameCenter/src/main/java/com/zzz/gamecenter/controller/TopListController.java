package com.zzz.gamecenter.controller;

import com.zzz.gamecenter.bean.Result;
import com.zzz.gamecenter.common.Constants;
import com.zzz.gamecenter.service.GameService;
import com.zzz.gamecenter.service.TopListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class TopListController {
    @Autowired
    TopListService topListService;
    @Autowired
    GameService gameService;

    @GetMapping("/topListTitle")
    public Result getTopListTitle(){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,topListService.getTopListTitle());
    }

    @GetMapping("/toplist")
    public Result getTopList(@RequestParam("tlId") Long tlId) {
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,topListService.getTopListByTLId(tlId));
    }

}
