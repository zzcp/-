package com.zzz.gamecenter.controller;

import com.zzz.gamecenter.bean.Result;
import com.zzz.gamecenter.common.Constants;
import com.zzz.gamecenter.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
public class CarouselController {
    @Autowired
    CarouselService carouselService;
    @GetMapping("/carousel")
    public Result getCarousel(){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,carouselService.getCarousel());
    }
}
