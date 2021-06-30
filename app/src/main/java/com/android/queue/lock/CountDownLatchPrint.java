package com.android.queue.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author lizhifeng
 * @date 2021/6/30 15:47
 */
public class CountDownLatchPrint implements ICrossPrint {

    @Override
    public void print() {
        for (int i = 0; i < strings.length; i++) {
            CountDownLatch countDownLatch = new CountDownLatch(2);
            CountDownLatchThread stringsThread = new CountDownLatchThread(strings[i], countDownLatch);
            CountDownLatchThread intsThread = new CountDownLatchThread(ints[i], countDownLatch);
            stringsThread.start();
            intsThread.start();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class CountDownLatchThread extends Thread {
        private String strings;
        private CountDownLatch current;

        public CountDownLatchThread(String strings, CountDownLatch current) {
            this.strings = strings;
            this.current = current;
        }

        @Override
        public void run() {
            super.run();
            System.out.println(strings);
            current.countDown();
        }
    }
}
