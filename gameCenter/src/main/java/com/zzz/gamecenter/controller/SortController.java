package com.zzz.gamecenter.controller;

import com.zzz.gamecenter.bean.Result;
import com.zzz.gamecenter.common.Constants;
import com.zzz.gamecenter.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class SortController {
    @Autowired
    SortService sortService;

    @RequestMapping("/sortlist")
    public Result getSortList(@RequestParam(name = "limit",defaultValue = "10") int limit){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,sortService.getSortList(limit));
    }


}
