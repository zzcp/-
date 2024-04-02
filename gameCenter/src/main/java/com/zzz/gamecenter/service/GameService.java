package com.zzz.gamecenter.service;

import com.zzz.gamecenter.entity.Game;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

public interface GameService {
    // 搜索
    Map<String, Object> getSearchList(String keyword,int offset,int limit,String tag);
    Map<String, Object> getSearchListByTag(String tag,int offset,int limit);
    Map<String, Object> getSearchListByUserName(String userName,int offset,int limit);
    //多条数据
    Map<String, ArrayList<Game>> getGameList(Long sortId,int offset,int limit);
    Map<String,ArrayList<Game>> getNewGameOrder();

    Map<String,Game> getGameById(Long gameId);

    Map<String,ArrayList<Game>> getRelatedList(String tag);

    Map<String,ArrayList<Game>> getGamesByDeveloper(String developer);

    Map<String,ArrayList<Game>> getRecommendList();

    Map<String,Object> getAllGame(int offset,int game);

    Map<String,String> updateGame(Game game);

    Map<String,Object> deleteGame(int gameId);
    Map<String,Object> addGame(String gameName,Double price,Long sortId,String imgUrl);

}
