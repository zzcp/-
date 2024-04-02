package com.zzz.gamecenter.service;

import com.zzz.gamecenter.entity.Game;
import com.zzz.gamecenter.entity.TopListTitle;

import java.util.ArrayList;
import java.util.Map;

public interface TopListService {
    Map<String, ArrayList<TopListTitle>> getTopListTitle();

    //获取热度排行榜前十
    Map<String,ArrayList<Game>> getTopListByTLId(Long tlId);
}
