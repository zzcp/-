package com.zzz.gamecenter.controller;

import com.zzz.gamecenter.bean.Result;
import com.zzz.gamecenter.common.Constants;
import com.zzz.gamecenter.entity.Comment;
import com.zzz.gamecenter.service.CommentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/comment/list")
    public Result getCommentList(@RequestParam("gameId") Long gameId,
                                 @RequestParam(name = "userId",defaultValue = "0") Long userId,
                                 @RequestParam(name = "limit",defaultValue = "5") int limit,
                                 @RequestParam(name = "offset",defaultValue = "0") int offset){
        limit = limit>10?10:limit;
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,commentService.getCommentList(userId,gameId,offset,limit));
    }

    @PostMapping("/comment/add")
    public void addComment(@Param("Comment")Comment comment){
        commentService.addComment(comment);
    }

    @GetMapping("/comment/all")
    public Result getAllComment(@RequestParam(name="offset",defaultValue = "0") int offset,
                                @RequestParam(name = "limit",defaultValue = "40") int limit){
        limit = limit>100?100:limit;
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,commentService.getAllComment(offset,limit));
    }

    @GetMapping("/user/comment")
    public Result getCommentByUserId(@RequestParam("userId") Long userId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,commentService.getCommentByUserId(userId));
    }

    @GetMapping("/user/new/comment")
    public Result getNewComment(@RequestParam("gameId") Long gameId,
                                @RequestParam("userId") Long userId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,commentService.getNewCommentId(gameId,userId));
    }

    @PostMapping("/comment/liked")
    public Result likedComment(@RequestParam("commentId") Long commentId,
                               @RequestParam(name = "userId",defaultValue = "0") Long userId,
                               @RequestParam("gameId") Long gameId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,commentService.likedComment(commentId,userId,gameId));
    }

    @PostMapping("/comment/unliked")
    public Result unlikedComment(@RequestParam("commentId") Long commentId,
                                 @RequestParam(name = "userId",defaultValue = "0") Long userId,
                               @RequestParam("gameId") Long gameId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,commentService.unlikedComment(commentId,userId,gameId));
    }

    @DeleteMapping("/comment/del")
    public Result delComment(@RequestParam("commentId") Long commentId,
                             @RequestParam(name = "userId",defaultValue = "0") Long userId,
                             @RequestParam("gameId") Long gameId){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,commentService.delComment(commentId,userId,gameId));
    }

    @PostMapping("/comment/reply")
    public Result replyComment(@Param("Comment") Comment comment){
        return Result.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MSG,commentService.replyComment(comment));
    }

}
