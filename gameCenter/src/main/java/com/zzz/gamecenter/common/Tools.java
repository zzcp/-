package com.zzz.gamecenter.common;

import com.alibaba.fastjson.JSON;
import com.zzz.gamecenter.entity.Game;

import java.util.ArrayList;
import java.util.List;

public class Tools {
    // json字符串转换
    public static ArrayList<Game> jsonParseArray(ArrayList<Game> list, String type){
        if (type.equals("tags")){
            for (Game i:list) {
                List<String> temp = JSON.parseArray((String)i.getTags(),String.class);
                i.setTags(temp);
            }
        } else if (type.equals("screenshot")) {
            for (Game i:list) {
                List<String> temp = JSON.parseArray((String)i.getScreenshot(),String.class);
                i.setScreenshot(temp);
            }
        } else if (type.equals("updateInfo")) {
            for (Game i:list) {
                List<String> temp = JSON.parseArray((String)i.getUpdateInfo(),String.class);
                i.setUpdateInfo(temp);
            }
        } else if (type.equals("gameInfo")) {
            for (Game i:list) {
                List<String> temp = JSON.parseArray((String)i.getGameInfo(),String.class);
                i.setGameInfo(temp);
            }
        }

        return list;
    }
}
