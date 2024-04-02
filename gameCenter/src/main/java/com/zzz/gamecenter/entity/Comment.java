package com.zzz.gamecenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    private Long commentId;
    private Long userId;
    private Long gameId;
    private Long replyId;
    private String content;
    private String time;
    private String score;
    private String imgUrl;
    private String name;
    private int liked;
    private int unliked;
    private String gameName;
    private String gameImgUrl;
    private Boolean isLiked;
    private Boolean isUnliked;
    private ArrayList<Comment> replyList;
}
