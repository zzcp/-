package com.zzz.gamecenter.service;

import com.zzz.gamecenter.entity.Carousel;

import java.util.ArrayList;
import java.util.Map;


public interface CarouselService {
    Map<String, ArrayList<Carousel>> getCarousel();
}
