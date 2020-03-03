package com.example.jdemo.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jdemo.AppPrefsUtils;
import com.example.jdemo.BaseConstant;
import com.example.jdemo.main.MainActivity;
import com.example.jdemo.Pool;
import com.example.jdemo.R;
import com.example.jdemo.XAsync;
import com.example.jdemo.databinding.FragmentLoginBinding;
import com.example.jdemo.db.entity.User;
import com.example.jdemo.db.repository.UserRepository;
import com.example.jdemo.login.vm.LoginViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mViewModel.setRepository(UserRepository.getInstance(getContext()));

        binding.setData(mViewModel);
        binding.setLifecycleOwner(this);

        mViewModel.getAccount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mViewModel.getEnable().setValue(enable());
            }
        });

        mViewModel.getPwd().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mViewModel.getEnable().setValue(enable());
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                Log.e("LoginFragment","login");
            }
        });

        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });

        loadShoesData();
    }

    private boolean enable() {
        return !TextUtils.isEmpty(mViewModel.getAccount().getValue())
                && !TextUtils.isEmpty(mViewModel.getPwd().getValue());
    }

    private void login() {
        Pool.execute(new XAsync<User>() {
            @Override
            protected User task() {
                Log.e("LoginFragment","xxxxxxxxxxxx");
                return  mViewModel.login();
            }

            @Override
            protected void callback(User result) {
                if (result != null) {
                    Toast.makeText(getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(),MainActivity.class));
                    Log.e("LoginFragment",result.getAccount() + " - " + result.getPwd());
                } else {
                    Log.e("LoginFragment","null");
                }
            }
        });
    }

    private void loadShoesData() {
        boolean isFirstLaunch = (boolean) AppPrefsUtils.get(BaseConstant.IS_FIRST_LAUNCH, true);
        if (isFirstLaunch) {
            Pool.execute(new XAsync<Boolean>() {
                @Override
                protected Boolean task() {
                    return mViewModel.onFirstLaunch(getContext());
                }

                @Override
                protected void callback(Boolean result) {
                    if (result) {
                        AppPrefsUtils.put(BaseConstant.IS_FIRST_LAUNCH, false);
                    }
                }
            });
        }
    }
}
