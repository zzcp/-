package com.zzz.gamecenter.mapper;

import com.zzz.gamecenter.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CommentMapper {
    ArrayList<Comment> getCommentList(Long gameId,int offset,int limit);

    ArrayList<Comment> getCommentByUserId(Long userId);
    void addComment(Comment comment);

    ArrayList<Comment> getAllComment(int offset,int limit);
    int getAllCommentById(Long gameId);
    int getCommentNum();
    int getNewCommentId(Long gameId,Long userId);
    int findLikedComment(Long commentId,Long userId,Long gameId);
    int findUnlikedComment(Long commentId,Long userId,Long gameId);

    int likedComment(Long commentId,Long userId,Long gameId);

    int unlikedComment(Long commentId,Long userId,Long gameId);

    int updateLiked(Long commentId,int num);
    int updateUnliked(Long commentId,int num);

    int cancelLiked(Long commentId,Long userId,Long gameId);

    int cancelUnliked(Long commentId,Long userId,Long gameId);

    int delComment(Long commentId,Long userId,Long gameId);
    int delLikedComment(Long commentId);
    int delUnlikedComment(Long commentId);

    int replyComment(Comment comment);

    ArrayList<Comment> getReplyList(Long commentId,Long gameId);



}
