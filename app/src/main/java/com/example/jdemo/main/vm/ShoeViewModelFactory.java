package com.example.jdemo.main.vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.jdemo.db.repository.ShoeRepository;

public class ShoeViewModelFactory extends  ViewModelProvider.NewInstanceFactory {

    private ShoeRepository shoeRepository;

    public ShoeViewModelFactory(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        return super.create(modelClass);
        return (T) new ShoeViewModel(shoeRepository);
    }
}
