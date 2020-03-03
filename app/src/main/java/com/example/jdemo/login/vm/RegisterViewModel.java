package com.example.jdemo.login.vm;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdemo.AppPrefsUtils;
import com.example.jdemo.BaseConstant;
import com.example.jdemo.Pool;
import com.example.jdemo.db.entity.User;
import com.example.jdemo.db.repository.UserRepository;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<String> account;
    private MutableLiveData<String> pwd;
    private MutableLiveData<String> mail;
    private MutableLiveData<Boolean> enable;

    public MutableLiveData<String> getAccount() {
        if (account == null) {
            account = new MutableLiveData<>();
//            account.setValue("");
        }
        return account;
    }

    public MutableLiveData<String> getPwd() {
        if (pwd == null) {
            pwd = new MutableLiveData<>();
//            pwd.setValue("");
        }
        return pwd;
    }

    public MutableLiveData<String> getMail() {
        if (mail == null) {
            mail = new MutableLiveData<>();
//            mail.setValue("");
        }
        return mail;
    }

    public MutableLiveData<Boolean> getEnable() {
        if (enable == null) {
            enable = new MutableLiveData<>();
            enable.setValue(false);
        }
        return enable;
    }

    /**
     * account EditText Change
     */
    public void onAccountChange(CharSequence s) {
        account.setValue(s.toString());
    }

    /**
     * pwd EditText Change
     */
    public void onPwdChange(CharSequence s) {
        pwd.setValue(s.toString());
    }

    /**
     * mail EditText Change;mail -> name
     */
    public void onMailChange(CharSequence s) {
        mail.setValue(s.toString());
    }

    private UserRepository userRepository;

    public void setRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register() {
        Pool.execute(new Runnable() {
            @Override
            public void run() {
                long id = userRepository.register(mail.getValue(),account.getValue(),pwd.getValue());
                AppPrefsUtils.put(BaseConstant.SP_USER_ID,id);
            }
        });
    }
}
