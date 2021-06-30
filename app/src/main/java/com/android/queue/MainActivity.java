package com.android.queue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.queue.block.BlockExecListener;
import com.android.queue.block.BlockTask;
import com.android.queue.block.BlockTaskBean;
import com.android.queue.block.BlockTaskManager;
import com.android.queue.block.UserBean;
import com.android.queue.lock.CrossPrint;

public class MainActivity extends AppCompatActivity {
    private TextView time_1;
    private TextView time_2;
    private TextView time_3;
    private TextView time_4;
    private BlockTask blockTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time_1 = findViewById(R.id.time_1);
        time_2 = findViewById(R.id.time_2);
        time_3 = findViewById(R.id.time_3);
        time_4 = findViewById(R.id.time_4);
        BlockTaskManager.getInstance().bindLife(this);
        BlockTaskManager.getInstance().start(new BlockExecListener() {
            @Override
            public void onEmpty() {
//                Log.e("Main  onEmpty",Thread.currentThread().getName());
            }

            @Override
            public void onExec(BlockTaskBean blockTaskBean) {
//                Log.e("Main  onExec",Thread.currentThread().getName());
            }
        });
        time_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlockTaskManager.getInstance().addTask(new BlockTaskBean(new UserBean("time_1","10"), 2000));
            }
        });
        time_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlockTaskManager.getInstance().addTask(new BlockTaskBean(new UserBean("time_2","10"), 2000));
            }
        });
        time_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlockTaskManager.getInstance().addTask(
                        new BlockTaskBean(new UserBean("time_3","10"), 2000));
            }
        });
        time_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlockTaskManager.getInstance().addTask(
                        new BlockTaskBean(new UserBean("time_4","10"), 2000));
            }
        });

    }
}