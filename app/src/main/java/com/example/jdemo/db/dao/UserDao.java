package com.example.jdemo.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.jdemo.db.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE user_account = :account AND user_pwd = :pwd")
    User login(String account, String pwd);

    @Query("SELECT * FROM user WHERE id=:id")
    User findUserById(long id);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert
    long insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);
}
