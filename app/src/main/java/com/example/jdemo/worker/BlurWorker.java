package com.example.jdemo.worker;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.jdemo.BaseConstant;

import java.io.FileNotFoundException;

public class BlurWorker extends Worker {
    private static final String TAG = "BlurWorker";

    public BlurWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        String resourceUri = getInputData().getString(BaseConstant.KEY_IMAGE_URI);

        makeStatusNotification("Blurring image", context);

        try {
            // 图片处理逻辑
            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG, "Invalid input uri");
//                throw IllegalArgumentException("Invalid input uri")
            }

            ContentResolver resolver = context.getContentResolver();
            Bitmap picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)));
            // 创建Bitmap文件
            Bitmap output = blurBitmap(picture, context);
            // 存储路径
            Uri outputUri = writeBitmapToFile(context, output);
            // 输出路径
            Data outPutData = workDataOf(BaseConstant.KEY_IMAGE_URI);
            makeStatusNotification("Output is "+ outputUri, context);

            return Result.success(outPutData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            Log.e(TAG, "Error applying blur", throwable);
            return Result.failure();
        }
    }

    private Data workDataOf(String keyImageUri) {
        return null;
    }

    private Uri writeBitmapToFile(Context context, Bitmap output) {
        return null;
    }

    private Bitmap blurBitmap(Bitmap picture, Context context) {
        return null;
    }

    private void makeStatusNotification(String s, Context context) {

    }
}
