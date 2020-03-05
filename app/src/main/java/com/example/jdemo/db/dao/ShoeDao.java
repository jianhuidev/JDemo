package com.example.jdemo.db.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.jdemo.db.entity.Shoe;

import java.util.List;

@Dao
public interface ShoeDao {

    // 通过鞋子的范围寻找Index
    @Query("SELECT * FROM shoe WHERE id between :startIndex AND :endIndex ORDER BY id ASC")
    List<Shoe> findShoesByIndexRange(long startIndex, long endIndex);

    // 配合LiveData 返回所有的鞋子
    @Query("SELECT * FROM shoe")
    DataSource.Factory<Integer,Shoe> getAllShoesLD();

    // 配合LiveData 通过Id查询单款鞋子
    @Query("SELECT * FROM shoe WHERE id=:id")
    LiveData<Shoe> findShoeByIdLiveData(long id);

    @Query("SELECT * FROM shoe WHERE id=:id")
    Shoe findShoeByIdLD(long id);

    // 通过品牌查询鞋子
    @Query("SELECT * FROM shoe WHERE shoe_brand IN (:brands)")
    DataSource.Factory<Integer, Shoe> findShoesByBrands(String[] brands);

    // 根据收藏结合 查询用户喜欢的鞋的集合
    @Query(
            "SELECT shoe.id,shoe.shoe_name,shoe.shoe_description,shoe.shoe_price,shoe.shoe_brand,shoe.shoe_imgUrl " +
                    "FROM shoe " +
                    "INNER JOIN fav_shoe ON fav_shoe.shoe_id = shoe.id " +
                    "WHERE fav_shoe.user_id = :userId"
    )
    LiveData<List<Shoe>> findShoesByUserId(long userId);

    // 增加一双鞋子
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShoe(Shoe shoe);

    // 增加多双鞋子;除了List之外，也可以使用数组
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShoes(List<Shoe> shoes);

    // 删除一双鞋子
    @Delete
    void deleteShoe(Shoe shoe);

    // 删除多个鞋子，参数也可以使用数组
    @Delete
    void deleteShoes(List<Shoe> shoes);

    // 更新一双鞋
    @Update
    void updateShoe(Shoe shoe);

    // 更新多双鞋，参数也可以是集合
    @Update
    void updateShoes(Shoe[] shoes);
}
