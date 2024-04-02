package com.zzz.gamecenter.service;

import com.zzz.gamecenter.entity.Wishlist;

import java.util.ArrayList;
import java.util.Map;

public interface WishlistService {
    Map<String,ArrayList<Wishlist>> getOrderById(Long userId,int offset,int limit);

    Map<String,Object> addOrder(Long userId,Long gameId);
    Map<String,Object> removeOrder(Long userId,Long gameId);
    Map<String,Object> payOrder(Long userId,ArrayList<Long> gameIdList);

    Map<String,Object> payGame(Long userId,ArrayList<Long> gameIdList);
    Map<String,Object> checkPay(Long userId,ArrayList<Long> gameIdList);
    Map<String,Object> findOrder(Long userId,Long gameId);

    Map<String,Object> getAllWishlist(int offset,int limit);
}
