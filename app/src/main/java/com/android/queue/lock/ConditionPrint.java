package com.android.queue.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lizhifeng
 * @date 2021/6/30 14:24
 */
public class ConditionPrint implements ICrossPrint{
    ReentrantLock reentrantLock = new ReentrantLock();
    Condition stringsCondition = reentrantLock.newCondition();
    Condition intsCondition = reentrantLock.newCondition();
    @Override
    public void print() {
        ConditionPrint.ConditionThread stringsThread = new ConditionPrint.ConditionThread(reentrantLock, 0, strings);
        stringsThread.setName("stringsThread");
        stringsThread.start();

        ConditionPrint.ConditionThread intsThread = new ConditionPrint.ConditionThread(reentrantLock, 1, ints);
        intsThread.setName("intsThread");
        intsThread.start();
    }

    class ConditionThread extends Thread {
        private final ReentrantLock reentrantLock;
        private final String[] stringArray;
        private final int type;

        public ConditionThread(ReentrantLock reentrantLock, int type, String[] strings) {
            this.reentrantLock = reentrantLock;
            this.stringArray = strings;
            this.type = type;
        }

        @Override
        public void run() {
            super.run();
            try {
                reentrantLock.lock();
                for (String string : stringArray) {
                    System.out.println(Thread.currentThread().getName() + "  " + string);
                    if (type == 0) {
                        stringsCondition.signal();
                        intsCondition.await();
                    } else {
                        intsCondition.signal();
                        stringsCondition.await();
                    }
                }
                if (type == 0) {
                    stringsCondition.signal();
                } else {
                    intsCondition.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                reentrantLock.unlock();
            }

        }
    }
}
