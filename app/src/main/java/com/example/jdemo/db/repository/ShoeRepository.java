package com.example.jdemo.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.example.jdemo.db.AppDataBase;
import com.example.jdemo.db.dao.ShoeDao;
import com.example.jdemo.db.entity.Shoe;

import java.util.List;

public class ShoeRepository {

    /**
     * 通过id的范围寻找鞋子
     */
    public List<Shoe> getPageShoes(long startIndex, long endIndex) {
        return shoeDao.findShoesByIndexRange(startIndex, endIndex);
    }

    public DataSource.Factory<Integer, Shoe> getAllShoes() {
        return shoeDao.getAllShoesLD();
    }

    /**
     * 通过品牌查询鞋子
     */
    public DataSource.Factory<Integer, Shoe> getShoesByBrand(String[] brands) {
        return shoeDao.findShoesByBrands(brands);
    }

    /**
     * 通过Id查询一双鞋
     */
    public Shoe getShoeById(long id) {
        return shoeDao.findShoeByIdLD(id);
    }

    public LiveData<Shoe> findShoeByIdLiveData(long id) {
        return shoeDao.findShoeByIdLiveData(id);
    }
    /**
     * 查询用户收藏的鞋
     */
    public LiveData<List<Shoe>> getShoesByUserId(long userId) {
        return shoeDao.findShoesByUserId(userId);
    }

    /**
     * 插入鞋子的集合
     */
    public void insertShoes(List<Shoe> shoes) {
        shoeDao.insertShoes(shoes);
    }

    private ShoeDao shoeDao;

    private ShoeRepository(ShoeDao shoeDao) {
        this.shoeDao = shoeDao;
    }

    private static ShoeRepository INSTANCE;

    public synchronized static ShoeRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ShoeRepository(AppDataBase.getDatabase(context).getShoeDao());
        }
        return INSTANCE;
    }
}
