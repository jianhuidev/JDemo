package com.example.jdemo.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.example.jdemo.BaseConstant;
import com.example.jdemo.UiUtils;
import com.example.jdemo.databinding.FragmentShoeBinding;
import com.example.jdemo.db.entity.Shoe;
import com.example.jdemo.db.repository.ShoeRepository;
import com.example.jdemo.main.vm.ShoeViewModel;
import com.example.jdemo.main.vm.ShoeViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShoeFragment extends Fragment {

    private FragmentShoeBinding binding;
    private ShoeViewModel mViewModel;
    private ShoeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShoeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        mViewModel = new ViewModelProvider(this).get(ShoeViewModel.class);
//        mViewModel.setRepository(ShoeRepository.getInstance(getContext()));
        providerShoeViewModel();
        binding.setLifecycleOwner(this);

        mAdapter = new ShoeAdapter(getContext());
        binding.recycler.setAdapter(mAdapter);

        LiveData<PagedList<Shoe>> pagedListLiveData = mViewModel.getShoesLivePaged();

        pagedListLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<Shoe>>() {
            @Override
            public void onChanged(PagedList<Shoe> shoes) {
                if (shoes != null) {
                    mAdapter.submitList(shoes);
                }
            }
        });
        setListener();
        setGpVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.fabShoe.post(new Runnable() {
            @Override
            public void run() {
                width = binding.fabShoe.getMeasuredWidth();
            }
        });

        radius = UiUtils.dp2px(getContext(), 80f);
    }

    public void setListener() {
        binding.fabShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mViewModel.getBrand().setValue(ShoeViewModel.ALL);
                shoeAnimation();
            }
        });

        binding.fabNike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getBrand().setValue(ShoeViewModel.NIKE);
                shoeAnimation();
            }
        });

        binding.fabAdidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getBrand().setValue(ShoeViewModel.ADIDAS);
                shoeAnimation();
            }
        });

        binding.fabOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getBrand().setValue(ShoeViewModel.OTHER);
                shoeAnimation();
            }
        });
    }

    // 动画集合，用来控制动画的有序播放
    private AnimatorSet animatorSet;
    // 圆的半径
    private int radius = 0;
    // FloatingActionButton宽度和高度，宽高一样
    private int width = 0;

    private void shoeAnimation() {
        // 播放动画的时候不可以点击
        if (animatorSet != null && animatorSet.isRunning()) {
            return;
        }

        if (binding.gpNike.getVisibility() != View.VISIBLE) {
            // 收起状态
            animatorSet = new AnimatorSet();
            ValueAnimator nAnimator = getValueAnimator(binding.fabNike, false, binding.gpNike, 0);
            ValueAnimator aAnimator = getValueAnimator(binding.fabAdidas, false, binding.gpAdidas, 45);
            ValueAnimator oAnimator = getValueAnimator(binding.fabOther, false, binding.gpOther, 90);

            animatorSet.playSequentially(nAnimator, aAnimator, oAnimator);
            animatorSet.start();
        } else {
            animatorSet = new AnimatorSet();
            ValueAnimator nAnimator = getValueAnimator(binding.fabNike, true, binding.gpNike, 0);
            ValueAnimator aAnimator = getValueAnimator(binding.fabAdidas, true, binding.gpAdidas, 45);
            ValueAnimator oAnimator = getValueAnimator(binding.fabOther, true, binding.gpOther, 90);

            animatorSet.playSequentially(oAnimator, aAnimator, nAnimator);
            animatorSet.start();
        }
    }

    private ValueAnimator getValueAnimator(final FloatingActionButton fab, final boolean reverse, final Group group, final int angle) {
        ValueAnimator animator;
        if (reverse) {
            // 收起
            animator = ValueAnimator.ofFloat(1f, 0f);
        } else {
            animator = ValueAnimator.ofFloat(0f, 1f);
        }

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) fab.getLayoutParams();
                params.circleRadius = (int) ((float)radius * v);
                params.circleAngle = 270f + angle * v;
                params.width = (int) ((float)width * v);
                params.height = (int) ((float)width * v);
                fab.setLayoutParams(params);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                group.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (reverse) {
                    setGpVisible(false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });

        animator.setDuration(180);
        animator.setInterpolator(new DecelerateInterpolator());
        return animator;
    }

    private void setGpVisible(Boolean isShow) {
        binding.gpNike.setVisibility(isShow ? View.VISIBLE : View.GONE);
        binding.gpAdidas.setVisibility(isShow ? View.VISIBLE : View.GONE);
        binding.gpOther.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private void providerShoeViewModel() {
        ShoeViewModelFactory shoeViewModelFactory = new ShoeViewModelFactory(ShoeRepository.getInstance(getContext()));
        mViewModel = shoeViewModelFactory.create(ShoeViewModel.class);
    }
}
