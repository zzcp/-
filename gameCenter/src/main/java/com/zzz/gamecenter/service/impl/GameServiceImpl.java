package com.zzz.gamecenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.zzz.gamecenter.common.Tools;
import com.zzz.gamecenter.entity.Game;
import com.zzz.gamecenter.entity.User;
import com.zzz.gamecenter.service.GameService;
import com.zzz.gamecenter.mapper.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    GameMapper gameMapper;
    @Override
    public Map<String, Object> getSearchList(String keyword,int offset,int limit,String tag) {
        ArrayList<Game> searchList = gameMapper.getSearchList(keyword,offset,limit,tag);
        int total = gameMapper.getSearchListOfTotal(keyword,tag);
        Map<String,Object> map = new HashMap<>();

        searchList = Tools.jsonParseArray(searchList,"tags");

        map.put("total",total);
        map.put("searchList",searchList);
        return map;
    }

    @Override
    public Map<String,Object> getSearchListByTag(String tag,int offset,int limit) {
        ArrayList<Game> searchList = gameMapper.getSearchListByTag(tag,offset,limit);
        int total = gameMapper.getSearchListByTagOfTotal(tag);

        Map<String,Object> map = new HashMap<>();

        searchList = Tools.jsonParseArray(searchList,"tags");

        map.put("total",total);
        map.put("searchList",searchList);
        return map;
    }

    @Override
    public Map<String, Object> getSearchListByUserName(String userName, int offset, int limit) {
        ArrayList<User> searchList = gameMapper.getSearchListByUserName(userName,offset,limit);
        int total = gameMapper.getSearchListByUserNameOfTotal(userName);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("searchList",searchList);
        return map;
    }

    @Override
    public Map<String, ArrayList<Game>> getGameList(Long sortId,int offset,int limit) {
        ArrayList<Game> gameList = gameMapper.getGameList(sortId,offset,limit);
        Map<String,ArrayList<Game>> map = new HashMap<>();
        gameList = Tools.jsonParseArray(gameList,"tags");

        map.put("GameList",gameList);
        return map;
    }

    @Override
    public Map<String, ArrayList<Game>> getNewGameOrder() {
        ArrayList<Game> newGameOrder = gameMapper.getNewGameOrder();

        newGameOrder = Tools.jsonParseArray(newGameOrder,"tags");

        Map<String,ArrayList<Game>> map = new HashMap<>();

        map.put("newGameOrder",newGameOrder);
        return map;
    }

    @Override
    public  Map<String,Game> getGameById(Long gameId) {
        Game game = gameMapper.getGameById(gameId);

        List<String> tempTags = JSON.parseArray((String)game.getTags(),String.class);
        game.setTags(tempTags);

        List<String> tempScreenshot = JSON.parseArray((String)game.getScreenshot(),String.class);
        game.setScreenshot(tempScreenshot);

        List<String> tempGameIndo = JSON.parseArray((String)game.getGameInfo(),String.class);
        game.setGameInfo(tempGameIndo);

        List<String> tempUpdateInfo = JSON.parseArray((String)game.getUpdateInfo(),String.class);
        game.setUpdateInfo(tempUpdateInfo);

        Map<String,Game> map = new HashMap<>();
        map.put("gameInfo",game);
        return map;
    }

    @Override
    public Map<String, ArrayList<Game>> getGamesByDeveloper(String developer) {
        ArrayList<Game> gameList = gameMapper.getGamesByDeveloper(developer);
        gameList = Tools.jsonParseArray(gameList,"tags");

        ArrayList<Game> relatedGames = new ArrayList<>();
        if (gameList.size()<=5){
            relatedGames = gameList;
        }else{
            int len = gameList.size();
            ArrayList<Integer> list = new ArrayList<>();
            Random random = new Random();
            int count = 0;
            while(count<5){
                int randNum = random.nextInt(len);
                if (!list.contains(randNum)){
                    list.add(randNum);
                    relatedGames.add(gameList.get(randNum));
                    count++;
                }else{
                    continue;
                }
            }
        }

        Map<String,ArrayList<Game>> map = new HashMap<>();
        map.put("games",relatedGames);
        return map;
    }

    @Override
    public Map<String, ArrayList<Game>> getRelatedList(String tag) {
        ArrayList<Game> gameList = gameMapper.getRelatedList(tag);
        gameList = Tools.jsonParseArray(gameList,"tags");

        ArrayList<Game> relatedGames = new ArrayList<>();
        if (gameList.size()<=10){
            relatedGames = gameList;
        }else{
            int len = gameList.size();
            ArrayList<Integer> list = new ArrayList<>();
            Random random = new Random();
            int count = 0;
            while(count<10){
                int randNum = random.nextInt(len);
                if (!list.contains(randNum)){
                    list.add(randNum);
                    relatedGames.add(gameList.get(randNum));
                    count++;
                }else{
                    continue;
                }
            }
        }

        Map<String,ArrayList<Game>> map = new HashMap<>();
        map.put("relatedList",relatedGames);
        return map;
    }

    @Override
    public Map<String, ArrayList<Game>> getRecommendList() {
        ArrayList<Game> recommendList = gameMapper.getRecommendList();
        Map<String,ArrayList<Game>> map = new HashMap<>();
        map.put("recommendList",recommendList);
        return map;
    }

    @Override
    public Map<String, Object> getAllGame(int offset, int game) {
        ArrayList<Game> allGame = gameMapper.getAllGame(offset, game);
        int gameNum = gameMapper.getGameNum();
        Map<String,Object> map = new HashMap<>();
        map.put("total",gameNum);
        map.put("gameList",allGame);
        return map;
    }

    @Override
    public Map<String,String> updateGame(Game game) {
        gameMapper.updateGame(game);

        Map<String,String> map = new HashMap<>();
        map.put("msg","修改成功");
        return map;
    }

    @Override
    public Map<String, Object> deleteGame(int gameId) {
        int flag = gameMapper.deleteGameInfo(gameId);
        gameMapper.deleteGame(gameId);
        Map<String,Object> map = new HashMap<>();
        map.put("msg",flag);
        return map;
    }

    @Override
    public Map<String, Object> addGame(String gameName, Double price, Long sortId, String imgUrl) {
        gameMapper.addGameDetail(gameName,price,sortId,imgUrl);
        gameMapper.addGame(gameName,sortId,imgUrl);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","添加成功");
        return map;
    }
}
