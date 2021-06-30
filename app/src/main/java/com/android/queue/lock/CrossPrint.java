package com.android.queue.lock;


/**
 * @author lizhifeng
 * @date 2021/6/30 11:21
 */
public class CrossPrint implements ICrossPrint{
    private final ICrossPrint iCrossPrint;

    public CrossPrint(ICrossPrint iCrossPrint) {
        this.iCrossPrint = iCrossPrint;
    }

    public void print() {
        iCrossPrint.print();
    }
}
