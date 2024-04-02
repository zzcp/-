package com.zzz.gamecenter.mapper;

import com.zzz.gamecenter.entity.Game;
import com.zzz.gamecenter.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface GameMapper {
    //获取搜索结果
    ArrayList<Game> getSearchList(String keyword,int offset,int limit,String tag);
    //根据标签搜索
    ArrayList<Game> getSearchListByTag(String tag,int offset,int limit);
    ArrayList<User> getSearchListByUserName(String userName, int offset, int limit);
    //根据分类id查询游戏列表
    ArrayList<Game> getGameList(Long sortId,int offset,int limit);
    //获取新游列表
    ArrayList<Game> getNewGameOrder();
    //根据游戏id获取游戏信息
    Game getGameById(Long gameId);
    //获取相关厂商的游戏
    ArrayList<Game> getGamesByDeveloper(String developer);
    //获取游戏信息页面的相关推荐
    ArrayList<Game> getRelatedList(String tag);
    //获取推荐列表
    ArrayList<Game> getRecommendList();

    int getSearchListOfTotal(String keyword,String tag);

    int getSearchListByTagOfTotal(String tag);

    int getSearchListByUserNameOfTotal(String userName);

    ArrayList<Game> getAllGame(int offset,int limit);

    int getGameNum();
    int updateGame(Game game);
    int deleteGameInfo(int gameId);
    int deleteGame(int gameId);
    int addGameDetail(String gameName, Double price, Long sortId, String imgUrl);
    int addGame(String gameName, Long sortId, String imgUrl);
}
