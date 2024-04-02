package com.zzz.gamecenter.service.impl;

import com.zzz.gamecenter.entity.Carousel;
import com.zzz.gamecenter.mapper.CarouselMapper;
import com.zzz.gamecenter.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    CarouselMapper carouselMapper;

    @Override
    public Map<String, ArrayList<Carousel>> getCarousel() {
        ArrayList<Carousel> carouselList = carouselMapper.getCarousel();
        Map<String,ArrayList<Carousel>> map = new HashMap<>();

        map.put("carouselList",carouselList);
        return map;
    }
}
