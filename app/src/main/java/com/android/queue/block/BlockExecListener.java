package com.android.queue.block;

/**
 * @author lizhifeng
 * @date 2020/9/9 10:24
 */
public interface BlockExecListener {
    void onEmpty();
    void onExec(BlockTaskBean blockTaskBean);
}
