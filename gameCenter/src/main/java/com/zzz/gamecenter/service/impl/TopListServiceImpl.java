package com.zzz.gamecenter.service.impl;

import com.zzz.gamecenter.common.Tools;
import com.zzz.gamecenter.entity.Game;
import com.zzz.gamecenter.entity.TopListTitle;
import com.zzz.gamecenter.mapper.TopListMapper;
import com.zzz.gamecenter.service.TopListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TopListServiceImpl implements TopListService {
    @Autowired
    TopListMapper topListMapper;

    @Override
    public Map<String, ArrayList<TopListTitle>> getTopListTitle() {
        ArrayList<TopListTitle> topListTitle = topListMapper.getTopListTitle();
        Map<String,ArrayList<TopListTitle>> map = new HashMap<>();
        map.put("toplistTitle",topListTitle);
        return map;
    }
    @Override
    public Map<String, ArrayList<Game>> getTopListByTLId(Long tlId) {
        ArrayList<Game> topList = topListMapper.getTopListByTLId(tlId);

        topList = Tools.jsonParseArray(topList,"tags");

        Map<String,ArrayList<Game>> map = new HashMap<>();

        map.put("toplist",topList);
        return map;
    }

}
