package com.jesusrojo.workmanagerdemo.udemy.obsolet.kotlin

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

class MainActivityK : AppCompatActivity() {

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
            appendTV("click: startWorkManager(oneTimeWorkRequest)")
            startWorkManager(oneTimeWorkRequest)
        }
    }

    private fun getOneTimeWorkRequest(): OneTimeWorkRequest {
        val data = Data.Builder()
            .putInt(KEY_COUNT_VALUE, 1750)
            .build()
        return OneTimeWorkRequest.Builder(MyWorkerK::class.java)
            .setInputData(data)
            .build()
    }

    private fun observeWorkManager(oneTimeWorkRequest: OneTimeWorkRequest) {
        appendTV("observeWorkManager")
        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this, { workInfo ->
                if (workInfo != null) {
                    appendTV("onChanged ${workInfo.state.name}")

                    if (workInfo.state.isFinished) {
                        val outputData = workInfo.outputData
                        val message = outputData.getString(MyWorkerK.KEY_WORKER)

                        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                        appendTV(message)
                    }
                }
            })
    }

    private fun appendTV(message: String?) {
        textView?.append("\n" + message)
    }

    private fun startWorkManager(oneTimeWorkRequest: OneTimeWorkRequest?) {
        WorkManager.getInstance().enqueue(oneTimeWorkRequest!!)
    }

    companion object {
        const val KEY_COUNT_VALUE = "key_count"
    }
}