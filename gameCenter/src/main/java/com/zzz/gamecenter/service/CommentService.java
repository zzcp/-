package com.zzz.gamecenter.service;

import com.zzz.gamecenter.entity.Comment;

import java.util.ArrayList;
import java.util.Map;

public interface CommentService {
    Map<String, Object> getCommentList(Long userId,Long gameId,int offset,int limit);
    Map<String,Object> getCommentByUserId(Long userId);
    void addComment(Comment comment);

    Map<String,Object> getAllComment(int offset,int limit);

    Map<String,Object> likedComment(Long commentId,Long userId,Long gameId);
    Map<String,Object> unlikedComment(Long commentId,Long userId,Long gameId);
    Map<String,Object> delComment(Long commentId,Long userId,Long gameId);
    Map<String,Object> replyComment(Comment comment);
    Map<String,Object> getNewCommentId(Long gameId,Long userId);
}
