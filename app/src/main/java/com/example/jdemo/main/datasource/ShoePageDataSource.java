package com.example.jdemo.main.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.jdemo.BaseConstant;
import com.example.jdemo.db.entity.Shoe;
import com.example.jdemo.db.repository.ShoeRepository;

import java.util.List;

public class ShoePageDataSource extends PageKeyedDataSource<Integer, Shoe> {

    private static final String TAG = "ShoePageDataSource";
    private ShoeRepository mShoeRepository;

    public ShoePageDataSource(ShoeRepository shoeRepository) {
        mShoeRepository = shoeRepository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Shoe> callback) {
        long startIndex = 0;
        long endIndex = startIndex + params.requestedLoadSize;
        List<Shoe> shoes = mShoeRepository.getPageShoes(startIndex, endIndex);
        callback.onResult(shoes, null, 2);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Shoe> callback) {

        Log.e(TAG, "endPage:" + params.key + ",size:" + params.requestedLoadSize);

        int endPage = params.key;
        long endIndex = (endPage - 1) * BaseConstant.SINGLE_PAGE_SIZE + 1L;
        long startIndex = endIndex - params.requestedLoadSize;
        startIndex = (startIndex < 0) ? 0L : startIndex;
        List<Shoe> shoes = mShoeRepository.getPageShoes(startIndex, endIndex);

        callback.onResult(shoes, params.key + 1);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Shoe> callback) {

        Log.e(TAG, "startPage:" + params.key + ",size:" + params.requestedLoadSize);

        int startPage = params.key;
        long startIndex = (startPage - 1) * BaseConstant.SINGLE_PAGE_SIZE + 1L;
        long endIndex = startIndex + params.requestedLoadSize - 1;
        List<Shoe> shoes = mShoeRepository.getPageShoes(startIndex, endIndex);

        callback.onResult(shoes, params.key + 1);
    }
}
