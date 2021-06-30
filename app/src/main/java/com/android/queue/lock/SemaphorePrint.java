package com.android.queue.lock;

import java.util.concurrent.Semaphore;

/**
 * @author lizhifeng
 * @date 2021/6/30 15:30
 *
 * Semaphore：用来控制同时访问某个特定资源的操作数量，
 * 或者同时执行某个制定操作的数量。
 * Semaphore内部维护了一个计数器，其值为可以访问的共享资源的个数。
 *
 * 一个线程要访问共享资源，先使用acquire()方法获得信号量，
 * 如果信号量的计数器值大于等于1，意味着有共享资源可以访问，
 * 则使其计数器值减去1，再访问共享资源。如果计数器值为0,线程进入休眠。
 *
 * 当某个线程使用完共享资源后，使用release()释放信号量，
 * 并将信号量内部的计数器加1，之前进入休眠的线程将被唤醒并再次试图获得信号量。
 */
public class SemaphorePrint implements ICrossPrint{
    private final Semaphore stringsSemaphore=new Semaphore(1);
    private final Semaphore intsSemaphore=new Semaphore(0);
    @Override
    public void print() {
        SemaphoreThread stringsSemaphoreThread= new SemaphoreThread(
                stringsSemaphore, intsSemaphore, strings
        );
        SemaphoreThread intsSemaphoreThread= new SemaphoreThread(
                intsSemaphore, stringsSemaphore, ints
        );

        stringsSemaphoreThread.start();
        intsSemaphoreThread.start();
    }

    static class SemaphoreThread extends Thread{
        private final Semaphore currentThread;
        private final Semaphore nextThread;
        private final String[] stringArray;

        public SemaphoreThread(Semaphore currentThread, Semaphore nextThread, String[] stringArray) {
            this.currentThread = currentThread;
            this.nextThread = nextThread;
            this.stringArray = stringArray;
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < stringArray.length; i++) {
                try {
                    currentThread.acquire();
                    System.out.println(stringArray[i]);
                    nextThread.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
