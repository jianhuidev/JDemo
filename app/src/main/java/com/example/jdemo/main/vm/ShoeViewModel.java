package com.example.jdemo.main.vm;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.jdemo.db.entity.Shoe;
import com.example.jdemo.db.repository.ShoeRepository;
import com.example.jdemo.main.datasource.ShoePageDataSourceFactory;

public class ShoeViewModel extends ViewModel {

    public static final String ALL = "All";
    public static final String NIKE = "Nike";
    public static final String ADIDAS = "Adidas";
    public static final String OTHER = "other";

//    private LiveData<PagedList<Shoe>> shoesLivePaged;

    private MutableLiveData<String> brand;

    public LiveData<PagedList<Shoe>> getShoesLivePaged() {
        if (brand == null) {
            brand = new MutableLiveData<>();
            brand.setValue(ALL);
        }
        return Transformations.switchMap(brand, new Function<String, LiveData<PagedList<Shoe>>>() {
            @Override
            public LiveData<PagedList<Shoe>> apply(String input) {
                if (ALL.equals(input)) {
                    return getLivePagedListBuilder(new ShoePageDataSourceFactory(shoeRepository),
                            10,
                            false,10)
                            .build();
                } else {
                    String[] bs ;
                    if (NIKE.equals(input)) {
                        bs = new String[]{"Nike", "Air Jordan"};
                    } else if (ADIDAS.equals(input)) {
                        bs = new String[]{"Adidas"};
                    } else {
                        bs = new String[]{"Converse", "UA", "ANTA"};
                    }
                    return getLivePagedListBuilder(shoeRepository.getShoesByBrand(bs),
                            2,
                            false,2)
                            .build();
                }
            }
        });
    }

    private LivePagedListBuilder<Integer, Shoe> getLivePagedListBuilder(DataSource.Factory<Integer, Shoe> dataSourceFactory,
                                                           int pageSize,
                                                           boolean enablePlaceholders,
                                                           int initialLoadSizeHint) {
        return new LivePagedListBuilder<Integer, Shoe>(
                dataSourceFactory,
                new PagedList.Config.Builder()
                        .setPageSize(pageSize)
                        .setEnablePlaceholders(enablePlaceholders)
                        .setInitialLoadSizeHint(initialLoadSizeHint)
                        .build());
    }

    private ShoeRepository shoeRepository;

    public ShoeViewModel(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    //    public void setRepository(ShoeRepository shoeRepository) {
//        this.shoeRepository = shoeRepository;
//    }

    public MutableLiveData<String> getBrand() {
        if (brand == null) {
            brand = new MutableLiveData<>();
            brand.setValue(ALL);
        }
        return brand;
    }

//    public void setBrand(String b) {
//        this.brand.setValue(b);
//    }
}
