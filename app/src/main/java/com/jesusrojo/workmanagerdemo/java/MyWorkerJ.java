package com.jesusrojo.workmanagerdemo.java;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorkerJ extends Worker {

    public static final String KEY_WORKER="key_worker";

    public MyWorkerJ(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data data=getInputData();
        int countLimit=data.getInt(MainActivityJ.KEY_COUNT_VALUE,0);

        for(int i=0;i<countLimit;i++){

            Log.i("MYTAG"," Count is "+i);
        }

        Data dataToSend=new Data.Builder()
                .putString(KEY_WORKER," Task Done Successfully")
                .build();



        return Result.success(dataToSend);
    }
}
