package com.example.jdemo.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.jdemo.db.entity.FavouriteShoe;

import java.util.List;

@Dao
public interface FavouriteShoeDao {

    // 查询用户下面的FavouriteShoe
    @Query("SELECT * From fav_shoe WHERE user_id = :userId")
    LiveData<List<FavouriteShoe>> findFavouriteShoesByUserId(String userId);

    // 查询单个FavouriteShoe
    @Query("SELECT * FROM fav_shoe WHERE user_id = :userId AND shoe_id =:shoeId")
    LiveData<FavouriteShoe> findFavouriteShoeByUserIdAndShoeId(long userId, long shoeId);

    // 插入单个FavouriteShoe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavouriteShoe(FavouriteShoe favouriteShoe);

    // 删除单个FavouriteShoe
    @Delete
    void deleteFavouriteShoe(FavouriteShoe favouriteShoe);

    // 删除多个FavouriteShoe
    @Delete
    void deleteFavouriteShoes(List<FavouriteShoe> favouriteShoes);
}
