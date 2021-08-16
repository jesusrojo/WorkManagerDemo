package com.jesusrojo.workmanagerdemo.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.work.OneTimeWorkRequest
import android.os.Bundle
import android.view.View
import com.jesusrojo.workmanagerdemo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.work.WorkManager
import android.widget.Toast
import androidx.work.Data

class MainActivity : AppCompatActivity() {

    private var textView: TextView? = null
    private var oneTimeWorkRequest: OneTimeWorkRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oneTimeWorkRequest = getOneTimeWorkRequest()
        initUi()
        observeWorkManager(oneTimeWorkRequest!!)
    }

    private fun initUi() {
        textView = findViewById(R.id.tvStatus)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            textView?.append("\nclick: startWorkManager(oneTimeWorkRequest)")
            startWorkManager(oneTimeWorkRequest)
        }
    }

    private fun getOneTimeWorkRequest(): OneTimeWorkRequest {
        val data = Data.Builder()
            .putInt(KEY_COUNT_VALUE, 1750)
            .build()
        return OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)
            .build()
    }

    private fun observeWorkManager(oneTimeWorkRequest: OneTimeWorkRequest) {
        textView?.append("\nobserveWorkManager")
        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this, { workInfo ->
                if (workInfo != null) {
                    textView?.append("onChanged ${workInfo.state.name}")

                    if (workInfo.state.isFinished) {
                        val outputData = workInfo.outputData
                        val message = outputData.getString(MyWorker.KEY_WORKER)

                        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                        textView?.append("\n"+message)
                    }
                }
            })
    }

    private fun startWorkManager(oneTimeWorkRequest: OneTimeWorkRequest?) {
        WorkManager.getInstance().enqueue(oneTimeWorkRequest!!)
    }

    companion object {
        const val KEY_COUNT_VALUE = "key_count"
    }
}