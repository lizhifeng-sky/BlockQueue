package com.android.queue.lock;

/**
 * @author lizhifeng
 * @date 2021/6/30 14:22
 */
public class ObjectPrint implements ICrossPrint {
    private Object lockObject =new Object();
    @Override
    public void print() {
        ObjectPrint.ObjectThread stringsThread=new ObjectPrint.ObjectThread(strings);
        stringsThread.setName("strings");
        stringsThread.start();
        ObjectPrint.ObjectThread intsThread=new ObjectPrint.ObjectThread(ints);
        intsThread.setName("ints");
        intsThread.start();
    }

    class ObjectThread extends Thread{
        private final String[] stringArray;

        public ObjectThread(String[] stringArray) {
            this.stringArray = stringArray;
        }

        @Override
        public void run() {
            super.run();
            synchronized (lockObject){
                for (String s : stringArray) {
                    System.out.println(Thread.currentThread().getName() + " " + s);
                    lockObject.notify();
                    try {
                        lockObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lockObject.notify();
            }
        }
    }
}
