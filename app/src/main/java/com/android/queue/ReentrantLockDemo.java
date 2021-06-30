package com.android.queue;

import com.android.queue.LockTimeThread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lizhifeng
 * @date 2021/6/30 10:03
 */
public class ReentrantLockDemo {

    //lock & unlock
    public void lock(){
        ReentrantLock reentrantLock = new ReentrantLock();
        System.out.println("reentrantLock->lock");
        reentrantLock.lock();
        try {
            System.out.println("睡眠2秒...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
            System.out.println("reentrantLock->unlock");
        }
    }

    //try lock time
    public void tryLock(){
        ReentrantLock reentrantLock = new ReentrantLock();
        LockTimeThread lockTimeThread=new LockTimeThread(reentrantLock);
        lockTimeThread.setName("Thread_1");
        lockTimeThread.start();

        LockTimeThread lockTimeThread_2=new LockTimeThread(reentrantLock);
        lockTimeThread_2.setName("Thread_2");
        lockTimeThread_2.start();
    }

}
