package com.example.jdemo.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jdemo.R;
import com.example.jdemo.databinding.FragmentRegisterBinding;
import com.example.jdemo.db.repository.UserRepository;
import com.example.jdemo.login.vm.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegisterViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
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

        mViewModel.getMail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mViewModel.getEnable().setValue(enable());
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.register();

                Bundle bundle = new Bundle();
                bundle.putString("account",mViewModel.getAccount().getValue());
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.loginFragment,bundle);

                Log.e("RegisterFragment","register");
            }
        });

        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });
    }

    private boolean enable() {
        return !TextUtils.isEmpty(mViewModel.getAccount().getValue())
                && !TextUtils.isEmpty(mViewModel.getPwd().getValue())
                && !TextUtils.isEmpty(mViewModel.getMail().getValue());
    }
}
