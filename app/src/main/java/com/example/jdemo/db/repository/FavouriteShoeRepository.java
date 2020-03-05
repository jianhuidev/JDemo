package com.example.jdemo.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.jdemo.db.AppDataBase;
import com.example.jdemo.db.dao.FavouriteShoeDao;
import com.example.jdemo.db.entity.FavouriteShoe;

import java.util.Calendar;

public class FavouriteShoeRepository {

    /**
     * 查看某个用户是否有喜欢记录
     */
    public LiveData<FavouriteShoe> findFavouriteShoe(long userId, long shoeId) {
        return favouriteShoeDao.findFavouriteShoeByUserIdAndShoeId(userId, shoeId);
    }
//    fun findFavouriteShoe(userId:Long,shoeId:Long):LiveData<FavouriteShoe?>
//            = favouriteShoeDao.findFavouriteShoeByUserIdAndShoeId(userId, shoeId)

    /**
     * 收藏一双鞋
     */
    public void createFavouriteShoe(long userId, long shoeId) {
        favouriteShoeDao.insertFavouriteShoe(new FavouriteShoe(shoeId, userId, Calendar.getInstance()));
    }

    private FavouriteShoeDao favouriteShoeDao;

    private FavouriteShoeRepository(FavouriteShoeDao favouriteShoeDao) {
        this.favouriteShoeDao = favouriteShoeDao;
    }

    private static FavouriteShoeRepository INSTANCE;

    public synchronized static FavouriteShoeRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FavouriteShoeRepository(AppDataBase.getDatabase(context).getFavouriteShoeDao());
//            INSTANCE = new FavouriteShoeRepository(AppDataBase.getDatabase(context).getFavouriteShoeDao());
        }
        return INSTANCE;
    }
}
