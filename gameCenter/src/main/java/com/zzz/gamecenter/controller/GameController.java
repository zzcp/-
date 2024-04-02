package com.zzz.gamecenter.controller;

import com.zzz.gamecenter.bean.Result;
import com.zzz.gamecenter.common.Constants;
import com.zzz.gamecenter.entity.Game;
import com.zzz.gamecenter.service.GameService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")  //支持跨域
public class GameController {
    @Autowired
    GameService gameService;
    //根据sortId获取数据
    @GetMapping("/gamelist")
    public Result getGameList(@RequestParam("sortId") Long sortId,
                              @RequestParam(name = "offset",defaultValue = "0") int offset,
                              @RequestParam(name = "limit",defaultValue = "40") int limit){
        limit = limit>100?100:limit;
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.getGameList(sortId,offset,limit));
    }

    @GetMapping("/search")
    public Result getSearchList(@RequestParam("keyword") String keyword,
                                @RequestParam("type") String type,
                                @RequestParam(name = "limit",defaultValue = "40") int limit,
                                @RequestParam(name = "offset",defaultValue = "0") int offset,
                                @RequestParam(name = "tag",required = false) String tag){
        limit = limit>100?100:limit;
        if (type.equals("game")){
            return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.getSearchList(keyword,offset,limit,tag));
        }else if (type.equals("tag")){
            return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.getSearchListByTag(keyword,offset,limit));
        }else if (type.equals("user")){
            return Result.failResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.getSearchListByUserName(keyword,offset,limit));
        }else {
            return Result.failResult(Constants.FAIL_CODE,Constants.FAIL_MSG,null);
        }
    }

    @GetMapping("/newgame")
    public Result getNewGameOrder(){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.getNewGameOrder());
    }

    @GetMapping("/game")
    public Result getGameById(@RequestParam("gameId") Long gameId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.getGameById(gameId));
    }

    @GetMapping("/developer/games")
    public Result getGamesByDeveloper(@RequestParam("name")String developer){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.getGamesByDeveloper(developer));
    }

    @GetMapping("/related")
    public Result getRelatedList(@RequestParam("tag")String tag){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.getRelatedList(tag));
    }

    @GetMapping("/recommend")
    public  Result getRecommendList(){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.getRecommendList());
    }

    //后台管理
    @GetMapping("/game/list")
    public Result getAllGame(@RequestParam(name = "offset",defaultValue = "0") int offset,
                             @RequestParam(name = "limit",defaultValue = "40") int limit){
        limit = limit>100?100:limit;
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.getAllGame(offset,limit));
    }

    @PostMapping("/game/update")
    public Result updateGame(@Param("Game") Game game){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.updateGame(game));
    }

    @PostMapping("/game/add")
    public Result addGame(@RequestParam("gameName") String gameName,
                          @RequestParam("price") Double price,
                          @RequestParam("sortId") Long sortId,
                          @RequestParam("image") MultipartFile image){
        try {
            // 设置文件名
            String fileName = String.valueOf(new Date().getTime());
            // 获取静态资源目录的路径
            Path staticDir = Paths.get("src","/main/resources/static");
            String imgUrl = "img/user/" + fileName+"."+image.getContentType().split("/")[1];
            Path staticPath = staticDir.resolve(imgUrl);


            // 获取target/classes/static/资源目录的路径
            Path targetStaticDir = Paths.get(getClass().getClassLoader().getResource("static").toURI());
            Path targetPath = targetStaticDir.resolve(imgUrl);

            // 保存文件到本地
            Files.write(staticPath, image.getBytes());
            Files.write(targetPath, image.getBytes());
            return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.addGame(gameName,price,sortId,"http://localhost:8848/"+imgUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.successResult(Constants.FAIL_CODE,Constants.FAIL_MSG,new HashMap<>().put("msg","文件上传失败!"));
        }
    }

    @DeleteMapping("/game/delete")
    public Result deleteGame(@RequestParam("gameId") int gameId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,gameService.deleteGame(gameId));
    }

}
