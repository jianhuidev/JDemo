package com.example.jdemo.main.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdemo.db.entity.FavouriteShoe;
import com.example.jdemo.db.entity.Shoe;
import com.example.jdemo.db.repository.FavouriteShoeRepository;
import com.example.jdemo.db.repository.ShoeRepository;

public class DetailViewModel extends ViewModel {


    /**
     * 为啥写LiveData 查不出来数据
     * public LiveData<Shoe> getShoeLivData(long shoeId) {
     *      return shoeRepository.getShoeById(shoeId);
     * }
     */
    public Shoe getShoe(long shoeId) {
        return shoeRepository.getShoeById(shoeId);
    }

    public LiveData<Shoe> findShoeByIdLiveData(long shoeId) {
        return shoeRepository.findShoeByIdLiveData(shoeId);
    }

//    public LiveData<FavouriteShoe> getFavouriteShoe() {
//
//
//
//    }

    public LiveData<FavouriteShoe> getFavouriteShoe(long userId, long shoeId) {
        return favouriteShoeRepository.findFavouriteShoe(userId, shoeId);
    }

    // 收藏一双鞋
    public void favourite(long userId, long shoeId) {
        favouriteShoeRepository.createFavouriteShoe(userId,shoeId);
    }

    private FavouriteShoeRepository favouriteShoeRepository;
    public void setRepository(FavouriteShoeRepository favouriteShoeRepository) {
        this.favouriteShoeRepository = favouriteShoeRepository;
    }

    private ShoeRepository shoeRepository;
    public void setRepository(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

}
