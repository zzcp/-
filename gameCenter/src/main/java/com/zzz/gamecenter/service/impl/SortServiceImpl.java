package com.zzz.gamecenter.service.impl;

import com.zzz.gamecenter.entity.Sort;
import com.zzz.gamecenter.mapper.SortMapper;
import com.zzz.gamecenter.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class SortServiceImpl implements SortService {
    @Autowired
    SortMapper sortMapper;
    @Override
    public Map<String, Object> getSortList(int limit) {
        ArrayList<Sort> list = sortMapper.getSortList(limit);
        Map<String,Object> map = new HashMap<>();
        map.put("sortlist",list);
        return map;
    }
}
