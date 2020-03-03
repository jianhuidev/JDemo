package com.example.jdemo.login.vm;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdemo.Pool;
import com.example.jdemo.db.AppDataBase;
import com.example.jdemo.db.dao.ShoeDao;
import com.example.jdemo.db.entity.Shoe;
import com.example.jdemo.db.entity.User;
import com.example.jdemo.db.repository.ShoeRepository;
import com.example.jdemo.db.repository.UserRepository;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> account;
    private MutableLiveData<String> pwd;
    private MutableLiveData<Boolean> enable;
//    private MutableLiveData<User> user;

    public MutableLiveData<String> getAccount() {
        if (account == null) {
            account = new MutableLiveData<>();
            account.setValue("");
        }
        return account;
    }

    public MutableLiveData<String> getPwd() {
        if (pwd == null) {
            pwd = new MutableLiveData<>();
            pwd.setValue("");
        }
        return pwd;
    }

    public MutableLiveData<Boolean> getEnable() {
        if (enable == null) {
            enable = new MutableLiveData<>();
            enable.setValue(false);
        }
        return enable;
    }

//    public MutableLiveData<User> getUser() {
//        if (user == null) {
//            user = new MutableLiveData<>();
//        }
//        return user;
//    }

    public void onAccountChange(CharSequence s) {
        account.setValue(s.toString());
    }

    public void onPwdChange(CharSequence s) {
        pwd.setValue(s.toString());
    }

    private UserRepository userRepository;

    public void setRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login() {
        return userRepository.login(account.getValue(),pwd.getValue());
    }

    /**
     * 第一次启动的时候调用
     */
    public boolean onFirstLaunch(Context context) {
        if (context != null) {
            List<Shoe> shoeList = new ArrayList<>();
            try {
                Gson gson = new Gson();
                JSONArray jArr = new JSONArray(getAssetsJson(context));
                for (int i = 0;i < jArr.length();i++){
                    shoeList.add(gson.fromJson(jArr.getJSONObject(i).toString(), Shoe.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
//            ShoeDao shoeDao = ShoeRepository.getInstance(AppDataBase.getDatabase(context).getShoeDao());
            ShoeDao shoeDao = AppDataBase.getDatabase(context).getShoeDao();
            shoeDao.insertShoes(shoeList);

            return true;
        }
        return false;
    }

    private String getAssetsJson(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("shoes.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

//    fun onFirstLaunch():String {
//        val context = BaseApplication.context
//        context.assets.open("shoes.json").use {
//            JsonReader(it.reader()).use {
//                val shoeType = object : TypeToken<List<Shoe>>() {}.type
//                val shoeList: List<Shoe> = Gson().fromJson(it, shoeType)
//
//                val shoeDao = RepositoryProvider.providerShoeRepository(context)
//                shoeDao.insertShoes(shoeList)
//                for (i in 0..2) {
//                    for (shoe in shoeList) {
//                        shoe.id += shoeList.size
//                    }
//                    shoeDao.insertShoes(shoeList)
//                }
//            }
//
//        }
//        return "初始化数据成功！"
//    }

}
