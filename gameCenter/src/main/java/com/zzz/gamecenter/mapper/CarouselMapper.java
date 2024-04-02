package com.zzz.gamecenter.mapper;

import com.zzz.gamecenter.entity.Carousel;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CarouselMapper {
    ArrayList<Carousel> getCarousel();
}
