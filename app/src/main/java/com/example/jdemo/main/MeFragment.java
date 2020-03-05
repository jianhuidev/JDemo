package com.example.jdemo.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jdemo.Pool;
import com.example.jdemo.XAsync;
import com.example.jdemo.databinding.FragmentMeBinding;
import com.example.jdemo.db.entity.User;
import com.example.jdemo.db.repository.UserRepository;
import com.example.jdemo.main.vm.MeViewModel;

public class MeFragment extends Fragment {

    private FragmentMeBinding binding;
    private MeViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(MeViewModel.class);
        mViewModel.setRepository(UserRepository.getInstance(getContext()));

        // LiveData 与 Room 结合使用
        mViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.setUser(user);

            }
        });

//        Pool.execute(new XAsync<User>() {
//            @Override
//            protected User task() {
//                return mViewModel.getUser();
//            }
//
//            @Override
//            protected void callback(User result) {
//                User user = result;
//                String url = user.getHeadImage();
//                binding.setUser(result);
//            }
//        });

        binding.ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"选",Toast.LENGTH_SHORT).show();
                mViewModel.applyBlur(getContext(), 3);
            }
        });


    }

    private void showDialog() {
//        new sweetAlertDialog()
    }

//    private val sweetAlertDialog: SweetAlertDialog by lazy {
//        SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
//                .setTitleText("头像")
//                .setContentText("更新中...")
//            /*
//            .setCancelButton("取消") {
//                model.cancelWork()
//                sweetAlertDialog.dismiss()
//            }*/
//    }
}
