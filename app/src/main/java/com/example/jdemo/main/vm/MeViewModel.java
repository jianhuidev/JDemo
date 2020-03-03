package com.example.jdemo.main.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdemo.AppPrefsUtils;
import com.example.jdemo.BaseConstant;
import com.example.jdemo.Pool;
import com.example.jdemo.XAsync;
import com.example.jdemo.db.entity.User;
import com.example.jdemo.db.repository.UserRepository;

public class MeViewModel extends ViewModel {

    private User user;

    private UserRepository userRepository;

    public void setRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        long id = (long) AppPrefsUtils.get(BaseConstant.SP_USER_ID,0L);
        user = userRepository.findUserById(id);
        return user;
    }
}
