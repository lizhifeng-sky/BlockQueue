package com.android.queue.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lizhifeng
 * @date 2021/6/30 14:52
 */
public class LockSupportPrint implements ICrossPrint{
    private LockSupportThread stringsThread;
    private LockSupportThread intsThread;
    @Override
    public void print() {
        stringsThread=new LockSupportThread(strings);
        intsThread=new LockSupportThread(ints);
        stringsThread.start();
        intsThread.start();
    }

    class LockSupportThread extends Thread{
        private final String[] stringArray;

        public LockSupportThread(String[] stringArray) {
            this.stringArray = stringArray;
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < stringArray.length; i++) {
                System.out.println(stringArray[i]);
                if (this.equals(stringsThread)){
                    LockSupport.unpark(intsThread);
                }else {
                    LockSupport.unpark(stringsThread);
                }
                LockSupport.park();
            }
        }
    }
}
