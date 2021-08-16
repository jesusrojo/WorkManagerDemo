package com.jesusrojo.workmanagerdemo.java;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jesusrojo.workmanagerdemo.R;

public class MainActivityJ extends AppCompatActivity {
    private TextView textView;
    private OneTimeWorkRequest oneTimeWorkRequest;
    public static final String KEY_COUNT_VALUE = "key_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        oneTimeWorkRequest = getOneTimeWorkRequest();

        initUi();

        observeWorkManager(oneTimeWorkRequest);
    }

    private void initUi() {
        textView = findViewById(R.id.tvStatus);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                textView.append("\nclick: startWorkManager(oneTimeWorkRequest)");
                startWorkManager(oneTimeWorkRequest);
            }
        });
    }

    private void startWorkManager(OneTimeWorkRequest oneTimeWorkRequest) {
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }

    @NonNull
    private OneTimeWorkRequest getOneTimeWorkRequest() {
        Data data = new Data.Builder()
                .putInt(KEY_COUNT_VALUE, 1750)
                .build();

        final OneTimeWorkRequest oneTimeWorkRequest =
                new OneTimeWorkRequest.Builder(MyWorkerJ.class)
                        .setInputData(data)
                        .build();
        return oneTimeWorkRequest;
    }

    private void observeWorkManager(OneTimeWorkRequest oneTimeWorkRequest) {
        textView.append("\nobserveWorkManager");

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if (workInfo != null) {
                            textView.append("\nonChanged" + workInfo.getState().name());

                            if (workInfo.getState().isFinished()) {

                                Data outputData = workInfo.getOutputData();
                                String message = outputData.getString(MyWorkerJ.KEY_WORKER);

                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                textView.append("\n" + message);
                            }
                        }
                    }
                });
    }

}
