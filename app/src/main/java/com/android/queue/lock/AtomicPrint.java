package com.android.queue.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lizhifeng
 * @date 2021/6/30 15:39
 */
public class AtomicPrint implements ICrossPrint{
    private AtomicInteger atomicInteger=new AtomicInteger(0);
    @Override
    public void print() {
        AtomicThread stringsThread=new AtomicThread(strings,0);
        AtomicThread intsThread=new AtomicThread(ints,1);
        stringsThread.start();
        intsThread.start();
    }

    class AtomicThread extends Thread{
        private String[] strings;
        private int threadNumber;

        public AtomicThread(String[] strings, int threadNumber) {
            this.strings = strings;
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < strings.length; i++) {
                while (atomicInteger.get()!=threadNumber){
                    //wait
                }
                System.out.println(strings[i]);
                if (atomicInteger.get()==1){
                    atomicInteger.getAndSet(0);
                }else {
                    atomicInteger.getAndSet(1);
                }
            }
        }
    }
}
