package com.jesusrojo.workmanagerdemo.exampleold.obsolet.kotlin

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.Worker
import com.jesusrojo.workmanagerdemo.exampleold.obsolet.java.MainActivityJ

class MyWorkerK(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val data = inputData
        val countLimit = data.getInt(MainActivityJ.KEY_COUNT_VALUE, 0)
        for (i in 0 until countLimit) {
            Log.i("## MYTAG", "doWork, Count is $i")
        }
        val dataToSend = Data.Builder()
            .putString(KEY_WORKER, " Task Done Successfully")
            .build()
        return Result.success(dataToSend)
    }

    companion object {
        const val KEY_WORKER = "key_worker"
    }
}