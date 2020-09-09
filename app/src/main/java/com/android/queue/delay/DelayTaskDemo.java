package com.android.queue.delay;

/**
 * @author lizhifeng
 * @date 2020/9/9 09:27
 */
class DelayTaskDemo {
    public void demo(){
        DelayTask delayTask=new DelayTask();
        Thread thread=new Thread(delayTask);
        thread.start();

        delayTask.addTask(new DelayTaskBean("name","id",100));
        delayTask.addTask(new DelayTaskBean("name","id",100));
        delayTask.addTask(new DelayTaskBean("name","id",100));
        delayTask.addTask(new DelayTaskBean("name","id",100));
    }
}
