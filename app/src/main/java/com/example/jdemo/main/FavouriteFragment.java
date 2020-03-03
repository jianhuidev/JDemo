package com.example.jdemo.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jdemo.R;
import com.example.jdemo.databinding.FragmentFavouriteBinding;
import com.example.jdemo.db.entity.Shoe;
import com.example.jdemo.db.repository.ShoeRepository;
import com.example.jdemo.main.vm.FavouriteViewModel;

import java.util.List;

public class FavouriteFragment extends Fragment {

    private FragmentFavouriteBinding binding;
    private FavouriteAdapter mAdapter;
    private FavouriteViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        mViewModel.setRepository(ShoeRepository.getInstance(getContext()));
        binding.setLifecycleOwner(this);

        mAdapter = new FavouriteAdapter(getContext());
        binding.recycler.setAdapter(mAdapter);
        mViewModel.getShoesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Shoe>>() {
            @Override
            public void onChanged(List<Shoe> shoes) {
                if (shoes != null) {
                    mAdapter.submitList(shoes);
                }
            }
        });

    }
}
