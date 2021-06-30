package com.android.queue.lock;

/**
 * @author lizhifeng
 * @date 2021/6/30 15:16
 * <p>
 * join()方法：在A线程中调用了B线程的join()方法时，
 * 表示只有当B线程执行完毕时，A线程才能继续执行。
 * 基于这个原理，我们使得三个线程按顺序执行，然后循环多次即可。
 * 无论线程1、线程2、线程3哪个先执行，最后执行的顺序都是线程1——>线程2——>线程3。代码如下：
 */
public class JoinPrint implements ICrossPrint {

    @Override
    public void print() {
        for (int i = 0; i < strings.length; i++) {
            JoinThread stringsThread = new JoinThread(strings[i]);
            stringsThread.setName("strings");

            JoinThread intsThread = new JoinThread(ints[i]);
            intsThread.setName("ints");
            stringsThread.setNextThread(intsThread);
            intsThread.setNextThread(stringsThread);

            stringsThread.start();
            intsThread.start();
        }
    }

    static class JoinThread extends Thread {
        private Thread nextThread;
        private String stringArray;

        public JoinThread(String stringArray) {
            this.stringArray = stringArray;
        }

        public void setNextThread(Thread nextThread) {
            this.nextThread = nextThread;
        }

        @Override
        public void run() {
            super.run();
            System.out.println(stringArray + "   " + nextThread);
            if (nextThread != null) {
                try {
                    nextThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
