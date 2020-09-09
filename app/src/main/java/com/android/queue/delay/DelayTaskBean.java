package com.android.queue.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

/**
 * @author lizhifeng
 * @date 2020/9/9 09:19
 */
public class DelayTaskBean implements Delayed {
    private String name;
    private String id;
    private long endTime;
    private TimeUnit timeUnit=TimeUnit.SECONDS;

    public DelayTaskBean(String name, String id, long endTime) {
        this.name = name;
        this.id = id;
        this.endTime = endTime;
    }

    @Override
    public long getDelay(@NonNull TimeUnit unit) {
        return endTime-System.currentTimeMillis();
    }

    @Override
    public int compareTo(@NonNull Delayed o) {
        DelayTaskBean w = (DelayTaskBean) o;
        return this.getDelay(this.timeUnit) - w.getDelay(this.timeUnit) > 0 ? 1:0;
    }
}
