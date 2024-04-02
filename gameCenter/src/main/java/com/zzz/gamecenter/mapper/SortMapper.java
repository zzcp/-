package com.zzz.gamecenter.mapper;

import com.zzz.gamecenter.entity.Sort;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface SortMapper {
    ArrayList<Sort> getSortList(int limit);
}
