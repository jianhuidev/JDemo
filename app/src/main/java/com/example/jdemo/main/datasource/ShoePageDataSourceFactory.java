package com.example.jdemo.main.datasource;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.jdemo.db.entity.Shoe;
import com.example.jdemo.db.repository.ShoeRepository;

public class ShoePageDataSourceFactory extends DataSource.Factory<Integer, Shoe> {

    private ShoeRepository shoeRepository;

    public ShoePageDataSourceFactory(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    @NonNull
    @Override
    public DataSource<Integer, Shoe> create() {
        return new ShoePageDataSource(shoeRepository);
    }
}
