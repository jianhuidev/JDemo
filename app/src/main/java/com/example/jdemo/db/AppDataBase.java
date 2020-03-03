package com.example.jdemo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.jdemo.db.dao.FavouriteShoeDao;
import com.example.jdemo.db.dao.ShoeDao;
import com.example.jdemo.db.dao.UserDao;
import com.example.jdemo.db.entity.FavouriteShoe;
import com.example.jdemo.db.entity.Shoe;
import com.example.jdemo.db.entity.User;

@Database(entities = {User.class, Shoe.class, FavouriteShoe.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase INSTANCE;
    public synchronized static AppDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,"jdemo_database")
                    .build();
        }
        return INSTANCE;
    }

    // 得到UserDao
    public abstract UserDao getUserDao();
    // 得到ShoeDao
    public abstract ShoeDao getShoeDao();
    // 得到FavouriteShoeDao
    public abstract FavouriteShoeDao getFavouriteShoeDao();
}
