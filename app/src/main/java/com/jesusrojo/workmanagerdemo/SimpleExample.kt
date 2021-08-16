package com.jesusrojo.workmanagerdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.work.*

//https://developer.android.com/topic/libraries/architecture/workmanager/basics#groovy

class UploadWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        //uploadImages() //HEAVY JOB
        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}

class SimpleExample : AppCompatActivity() {

    private lateinit var uploadWorkRequest: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>().build()
        startWorkManager(uploadWorkRequest)
    }

    private fun startWorkManager(uploadWorkRequest: WorkRequest) {
        WorkManager.getInstance(applicationContext).enqueue(uploadWorkRequest)
    }
}