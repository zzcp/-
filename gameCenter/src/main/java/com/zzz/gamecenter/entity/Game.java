package com.zzz.gamecenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Game {
    private Long gameId;
    private Long sortId;
    private Long tlId;
    private String gameName;
    private String imgUrl;
    private String videoUrl;
    private String gameLogo;
    private String gameBg;
    private String score;
    private String introduction;
    private String bIntroduction;
    private Double price;
    private String iSrc;
    private String tag;
    private String developer;
    private Object tags;
    private Object screenshot;
    private Object updateInfo;
    private Object gameInfo;
}
