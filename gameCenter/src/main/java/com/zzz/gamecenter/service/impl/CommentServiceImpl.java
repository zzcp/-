package com.zzz.gamecenter.service.impl;

import com.zzz.gamecenter.entity.Comment;
import com.zzz.gamecenter.mapper.CommentMapper;
import com.zzz.gamecenter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public Map<String, Object> getCommentList(Long userId,Long gameId,int offset,int limit) {
        Map<String,Object> map = new HashMap<>();
        ArrayList<Comment> commentList = commentMapper.getCommentList(gameId,offset,limit);
        for (Comment comment : commentList){
//            获取回复列表
            ArrayList<Comment> replyList = commentMapper.getReplyList(comment.getCommentId(),gameId);
            comment.setReplyList(replyList);
//            检查当前用户是否对该评论点赞过，踩同理
            int flagLiked = commentMapper.findLikedComment(comment.getCommentId(),userId,gameId);
            comment.setIsLiked(flagLiked==1?true:false);
            int flagUnliked = commentMapper.findUnlikedComment(comment.getCommentId(),userId,gameId);
            comment.setIsUnliked(flagUnliked==1?true:false);
        }
        int total = commentMapper.getAllCommentById(gameId);
        map.put("commentList",commentList);
        map.put("total",total);
        return map;
    }

    @Override
    public Map<String, Object> getCommentByUserId(Long userId) {
        ArrayList<Comment> commentList = commentMapper.getCommentByUserId(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("commentList",commentList);
        return map;
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }

    @Override
    public Map<String, Object> getNewCommentId(Long gameId, Long userId) {
        int commentId = commentMapper.getNewCommentId(gameId,userId);
        Map<String,Object> map = new HashMap<>();
        map.put("commentId",commentId);
        return map;
    }

    @Override
    public Map<String, Object> getAllComment(int offset, int limit) {
        ArrayList<Comment> allComment = commentMapper.getAllComment(offset, limit);
        int commentNum = commentMapper.getCommentNum();
        Map<String,Object> map = new HashMap<>();
        map.put("total",commentNum);
        map.put("commentList",allComment);
        return map;
    }

    @Override
    public Map<String,Object> likedComment(Long commentId, Long userId,Long gameId) {
//        检查是否点过赞该评论，如果点过则取消赞，否则点赞
        int count = commentMapper.findLikedComment(commentId,userId,gameId);
        String msg = "";
        Boolean flag = false;
        if (count==0){
            commentMapper.likedComment(commentId,userId,gameId);
            msg = "点赞成功！";
            flag = true;
        }else{
            commentMapper.cancelLiked(commentId,userId,gameId);
            msg = "已取消点赞！";
            flag = false;
        }
//        更新评论点赞数
        commentMapper.updateLiked(commentId,count==0?1:-1);

        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("flag",flag);
        return map;
    }

    @Override
    public Map<String,Object> unlikedComment(Long commentId, Long userId,Long gameId) {
        int count = commentMapper.findUnlikedComment(commentId,userId,gameId);
        String msg = "";
        Boolean flag = false;
        if (count==0){
            commentMapper.unlikedComment(commentId,userId,gameId);
            msg = "已点踩！";
            flag = true;
        }else{
            commentMapper.cancelUnliked(commentId,userId,gameId);
            msg = "已取消踩！";
            flag = false;
        }
        commentMapper.updateUnliked(commentId,count==0?1:-1);

        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        map.put("flag",flag);
        return map;
    }

    @Override
    public Map<String, Object> delComment(Long commentId, Long userId, Long gameId) {
        int count = commentMapper.delComment(commentId,userId,gameId);
        commentMapper.delUnlikedComment(commentId);
        commentMapper.delLikedComment(commentId);
        Map<String,Object> map = new HashMap<>();
        map.put("msg",count);
        return map;
    }

    @Override
    public Map<String, Object> replyComment(Comment comment) {
        commentMapper.replyComment(comment);

        Map<String,Object> map = new HashMap<>();
        map.put("msg","回复成功！");
        return map;
    }

}
