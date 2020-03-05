package com.example.jdemo.main.vm;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import com.example.jdemo.AppPrefsUtils;
import com.example.jdemo.BaseConstant;
import com.example.jdemo.Pool;
import com.example.jdemo.XAsync;
import com.example.jdemo.db.entity.User;
import com.example.jdemo.db.repository.UserRepository;
import com.example.jdemo.worker.BlurWorker;
import com.example.jdemo.worker.CleanUpWorker;
import com.example.jdemo.worker.SaveImageToFileWorker;

public class MeViewModel extends ViewModel {

    private User user;

    private UserRepository userRepository;

    public void setRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        long id = (long) AppPrefsUtils.get(BaseConstant.SP_USER_ID, 0L);
        user = userRepository.findUserById(id);
        return user;
    }

    public LiveData<User> getUserLiveData() {
        long id = (long) AppPrefsUtils.get(BaseConstant.SP_USER_ID, 0L);
        return userRepository.getUserByIdLiveData(id);
    }

    private WorkManager workManager;

    public void applyBlur(Context context, int blurLevel) {
        if (workManager == null) {
            workManager = WorkManager.getInstance(context);
        }

        // 任务A
        WorkContinuation continuation = workManager.beginUniqueWork(BaseConstant.IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE, OneTimeWorkRequest.from(CleanUpWorker.class));

        // 任务串行起来
        for (int i = 0; i < blurLevel; i++) {
            OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(BlurWorker.class);
            if (i == 0) {
                builder.setInputData(createInputDataForUri());
            }
            continuation = continuation.then(builder.build());
        }

        // 构建约束条件
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true) // 非电池低电量
                .setRequiredNetworkType(NetworkType.CONNECTED) // 网络连接的情况
                .setRequiresStorageNotLow(true) // 存储空间足
                .build();

        // 储存照片
        OneTimeWorkRequest save = new OneTimeWorkRequest.Builder(SaveImageToFileWorker.class)
                .setConstraints(constraints)
                .addTag(BaseConstant.TAG_OUTPUT)
                .build();

        continuation.then(save).enqueue();
    }

    private Uri imageUri;

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    private Data createInputDataForUri() {
        Data.Builder builder = new Data.Builder();
        builder.putString(BaseConstant.KEY_IMAGE_URI, imageUri.toString());
        return builder.build();
    }

}
