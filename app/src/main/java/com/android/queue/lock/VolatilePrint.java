package com.android.queue.lock;

/**
 * @author lizhifeng
 * @date 2021/6/30 14:31
 */
public class VolatilePrint implements ICrossPrint{
    enum CurrentThread{
        T1,
        T2
    }
    private volatile CurrentThread currentThread=CurrentThread.T1;
    @Override
    public void print() {
        VolatileThread stringsThread=new VolatileThread(strings,CurrentThread.T1);
        stringsThread.start();
        VolatileThread intsThread=new VolatileThread(ints,CurrentThread.T2);
        intsThread.start();
    }

    class VolatileThread extends Thread{
        private String[] strings;
        private CurrentThread threadState;

        public VolatileThread(String[] strings, CurrentThread threadState) {
            this.strings = strings;
            this.threadState = threadState;
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < strings.length; i++) {
                while (threadState!=currentThread){
                   //wait
                }
                System.out.println(strings[i]);
                if (currentThread==CurrentThread.T1){
                    currentThread=CurrentThread.T2;
                }else {
                    currentThread=CurrentThread.T1;
                }
            }
        }
    }
}
