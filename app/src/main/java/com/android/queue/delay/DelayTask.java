package com.android.queue.delay;

import java.util.concurrent.DelayQueue;

/**
 * @author lizhifeng
 * @date 2020/9/9 09:19
 */
public class DelayTask implements Runnable{
    private DelayQueue<DelayTaskBean> delayQueue=new DelayQueue<>();
    public boolean isStart=true;
    @Override
    public void run() {
        while (isStart){
            try {
                DelayTaskBean take = delayQueue.take();
                todo(take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void todo(DelayTaskBean delayTaskBean){

    }

    public void addTask(DelayTaskBean delayTaskBean){
        delayQueue.add(delayTaskBean);
    }
}
