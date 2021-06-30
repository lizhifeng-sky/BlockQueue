package com.android.queue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lizhifeng
 * @date 2021/6/30 10:22
 */
public class LockTimeThread extends Thread{
    private ReentrantLock reentrantLock;

    public LockTimeThread(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    @Override
    public void run() {
        super.run();
        try {
            System.out.println("try lock:" + Thread.currentThread().getName());
            boolean tryLock = reentrantLock.tryLock(3, TimeUnit.SECONDS);
            if (tryLock) {
                System.out.println("try lock success :" + Thread.currentThread().getName());
                System.out.println("睡眠一下：" + Thread.currentThread().getName());
                Thread.sleep(5000);
                System.out.println("醒了：" + Thread.currentThread().getName());
            } else {
                System.out.println("try lock 超时 :" + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("timeout:" + Thread.currentThread().getName());
        } finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                System.out.println("unlock:" + Thread.currentThread().getName());
                reentrantLock.unlock();
            }else {
                System.out.println("unlock:等待超时 未unlock " + Thread.currentThread().getName());
            }
        }
    }
}
