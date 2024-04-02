package com.zzz.gamecenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.zzz.gamecenter.entity.Game;
import com.zzz.gamecenter.entity.Wishlist;
import com.zzz.gamecenter.mapper.WishlistMapper;
import com.zzz.gamecenter.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    WishlistMapper wishlistMapper;
    public ArrayList<Wishlist> jsonParseArray(ArrayList<Wishlist> list, String type){
        if (type.equals("tags")){
            for (Wishlist i:list) {
                List<String> temp = JSON.parseArray((String)i.getTags(),String.class);
                i.setTags(temp);
            }
        }
        return list;
    }
    @Override
    public Map<String,ArrayList<Wishlist>> getOrderById(Long userId,int offset,int limit) {
        ArrayList<Wishlist> wishlistList = wishlistMapper.getOrderById(userId,offset,limit);

        wishlistList = jsonParseArray(wishlistList,"tags");

        Map<String,ArrayList<Wishlist>> map = new HashMap<>();
        map.put("wishList", wishlistList);
        return map;
    }

    @Override
    public Map<String, Object> addOrder(Long userId, Long gameId) {
        Wishlist wishlist = wishlistMapper.getOrderByUserIdAndGameId(userId, gameId);
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> infoMap = new HashMap<>();
        if (wishlist ==null){
            Long wishlistId = new Date().getTime();
            wishlistMapper.addOrder(wishlistId,userId,gameId);
            infoMap.put("code",200);
            infoMap.put("msg","已添加至心愿单！");
            infoMap.put("title","移出心愿单");
        }else if (wishlist!=null){
            infoMap.put("code",404);
            infoMap.put("msg","该商品已在您的心愿单中！");
        }else if(wishlist.isPurchase()){
            infoMap.put("code",200);
            infoMap.put("title","已购买");
        }

        map.put("info",infoMap);
        return map;
    }

    @Override
    public Map<String, Object> payOrder(Long userId, ArrayList<Long> gameIdList) {
        for(Long i : gameIdList){
            wishlistMapper.payOrder(userId,i);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("payMsg","支付成功！正在跳转...");
        return map;
    }

    @Override
    public Map<String, Object> payGame(Long userId,ArrayList<Long> gameIdList) {
        for(Long i : gameIdList){
            wishlistMapper.payOrder(userId,i);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("payMsg","支付成功！正在跳转...");
        return map;
    }

    @Override
    public Map<String, Object> checkPay(Long userId, ArrayList<Long> gameIdList) {
        Map<String,Object> map = new HashMap<>();
        int flag = 0;
        for(Long i : gameIdList){
            flag = wishlistMapper.checkPay(userId,i);
            if (flag == 0){
                break;
            }
        }
        if (flag==0){
            map.put("msg","未支付或支付失败，请重新尝试！");
            map.put("payCode",400);
        }else{
            map.put("msg","支付成功！正在跳转...");
            map.put("payCode",200);
        }

        return map;
    }

    @Override
    public Map<String, Object> removeOrder(Long userId, Long gameId) {
        Wishlist wishlist = wishlistMapper.getOrderByUserIdAndGameId(userId, gameId);
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> infoMap = new HashMap<>();
        if (wishlist ==null){
            infoMap.put("code",404);
            infoMap.put("msg","您未把该商品至心愿单！");
        }else{
            wishlistMapper.removeOrder(userId,gameId);
            infoMap.put("code",200);
            infoMap.put("msg","已将该商品移出您的心愿单！");
            infoMap.put("title","添加至心愿单");
        }
        map.put("info",infoMap);
        return map;
    }

    @Override
    public Map<String, Object> findOrder(Long userId, Long gameId) {
        Wishlist wishlist = wishlistMapper.getOrderByUserIdAndGameId(userId, gameId);
        Map<String,Object> map = new HashMap<>();
        if(wishlist == null){
            map.put("isInOrder",false);
            map.put("isPurchase",false);
            map.put("title","添加至心愿单");
        }else if (wishlist != null && !wishlist.isPurchase()){
            map.put("isInOrder",true);
            map.put("isPurchase",false);
            map.put("title","移出心愿单");
        }else if (wishlist.isPurchase()){
            map.put("isInOrder",true);
            map.put("isPurchase",wishlist.isPurchase());
            map.put("title","已购买");
        }

        return map;
    }

    @Override
    public Map<String, Object> getAllWishlist(int offset, int limit) {
        ArrayList<Wishlist> allWishlist = wishlistMapper.getAllWishlist(offset,limit);
        int wishlistNum = wishlistMapper.getWishlistNum();
        Map<String,Object> map = new HashMap<>();
        map.put("total",wishlistNum);
        map.put("wishlist",allWishlist);
        return map;
    }
}
