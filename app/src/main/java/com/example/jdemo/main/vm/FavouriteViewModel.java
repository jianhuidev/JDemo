package com.example.jdemo.main.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdemo.AppPrefsUtils;
import com.example.jdemo.BaseConstant;
import com.example.jdemo.db.entity.Shoe;
import com.example.jdemo.db.repository.ShoeRepository;

import java.util.List;

public class FavouriteViewModel extends ViewModel {

    private ShoeRepository shoeRepository;

    public void setRepository(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    public LiveData<List<Shoe>> getShoesLiveData() {
        long userId = (long) AppPrefsUtils.get(BaseConstant.SP_USER_ID, 0L);
        return shoeRepository.getShoesByUserId(userId);
    }
}
