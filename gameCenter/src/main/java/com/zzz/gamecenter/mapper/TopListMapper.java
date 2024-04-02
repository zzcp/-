package com.zzz.gamecenter.mapper;

import com.zzz.gamecenter.entity.Game;
import com.zzz.gamecenter.entity.TopListTitle;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface TopListMapper {
    ArrayList<TopListTitle> getTopListTitle();

    //根据排行榜类型id查询相关榜单
    ArrayList<Game> getTopListByTLId(Long tlId);
}
