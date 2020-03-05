package com.example.jdemo.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.jdemo.db.AppDataBase;
import com.example.jdemo.db.dao.UserDao;
import com.example.jdemo.db.entity.User;

import java.util.List;

public class UserRepository {

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User findUserById(long id) {
        return userDao.findUserById(id);
    }

    public LiveData<User> getUserByIdLiveData(long id) {
        return userDao.getUserByIdLiveData(id);
    }

    public User login(String account, String pwd) {
        return userDao.login(account,pwd);
    }

    public LiveData<User> loginLD(String account, String pwd) {
        return userDao.loginLD(account,pwd);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public long register(String email, String account, String pwd) {
        String imageUrl = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=186602880,1005592543&fm=26&gp=0.jpg";
        return userDao.insertUser(new User(account,pwd,email,imageUrl));
    }

    private UserDao userDao;

    private UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    private static UserRepository INSTANCE;
    public synchronized static UserRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(AppDataBase.getDatabase(context).getUserDao());
        }
        return INSTANCE;
    }
}
