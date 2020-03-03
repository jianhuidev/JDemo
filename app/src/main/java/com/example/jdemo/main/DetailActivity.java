package com.example.jdemo.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jdemo.AppPrefsUtils;
import com.example.jdemo.BaseConstant;
import com.example.jdemo.Pool;
import com.example.jdemo.R;
import com.example.jdemo.XAsync;
import com.example.jdemo.databinding.ActivityDetailBinding;
import com.example.jdemo.db.entity.FavouriteShoe;
import com.example.jdemo.db.entity.Shoe;
import com.example.jdemo.db.repository.FavouriteShoeRepository;
import com.example.jdemo.db.repository.ShoeRepository;
import com.example.jdemo.main.vm.DetailViewModel;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    DetailViewModel mViewModel;
//    LiveData<Shoe> shoeLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        long userId = (long) AppPrefsUtils.get(BaseConstant.SP_USER_ID, 0L);
        long shoeId = getIntent().getLongExtra(BaseConstant.DETAIL_SHOE_ID, 0L);

        mViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        mViewModel.setRepository(FavouriteShoeRepository.getInstance(this));
        mViewModel.setRepository(ShoeRepository.getInstance(this));
        binding.setLifecycleOwner(this);

//        shoeLiveData = mViewModel.getShoeLivData(shoeId);

        bindShoe(shoeId);
        favourite(userId, shoeId);
        setListener(userId, shoeId);
    }

    private void bindShoe(final long shoeId) {
        Pool.execute(new XAsync<Shoe>() {
            @Override
            protected Shoe task() {
                return mViewModel.getShoe(shoeId);
            }

            @Override
            protected void callback(Shoe result) {
                binding.setShoe(result);
            }
        });
    }

    private void favourite(final long userId, final long shoeId) {
        Pool.execute(new XAsync<FavouriteShoe>() {
            @Override
            protected FavouriteShoe task() {
                return mViewModel.getFavouriteShoe(userId, shoeId);
            }

            @Override
            protected void callback(FavouriteShoe result) {
                if (result == null) {
                    binding.setVisible(View.VISIBLE);
                } else {
                    binding.setVisible(View.GONE);
                }
            }
        });
    }

    private void setListener(final long userId, final long shoeId) {
        binding.fbFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbFavouriteAnimate(userId, shoeId);
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("DetailActivity",shoeLiveData.getValue().getName());
                onBackPressed();
            }
        });
    }

    private void fbFavouriteAnimate(final long userId, final long shoeId) {
        binding.fbFavourite.animate()
                .rotation(360.0f)
                .scaleX(0.0f)
                .scaleY(0.0f)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) { }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        Pool.execute(new XAsync<Integer>() {
                            @Override
                            protected Integer task() {
                                mViewModel.favourite(userId, shoeId);
                                return 0;
                            }

                            @Override
                            protected void callback(Integer result) {
                                binding.setVisible(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) { }

                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                }).setDuration(200)
                .start();
    }
}
