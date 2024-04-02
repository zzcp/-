package com.zzz.gamecenter.mapper;

import com.zzz.gamecenter.entity.Game;
import com.zzz.gamecenter.entity.Wishlist;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Date;


@Mapper
public interface WishlistMapper {
    ArrayList<Wishlist> getOrderById(Long userId,int offset,int limit);

    Wishlist getOrderByUserIdAndGameId(Long userId, Long gameId);

    ArrayList<Game> getGameList(String sql);

    void payOrder(Long userId,Long gameId);
    int checkPay(Long userId,Long gameId);

    void addOrder(Long wishlistId,Long userId,Long gameId);

    void removeOrder(Long userId,Long gameId);

    ArrayList<Wishlist> getAllWishlist(int offset,int limit);

    int getWishlistNum();
}
