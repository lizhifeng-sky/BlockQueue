package com.android.queue.block;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author lizhifeng
 * @date 2020/9/9 09:31
 */
public class BlockTask implements Runnable {
    private BlockExecListener blockEmptyListener;
    private LinkedBlockingDeque<BlockTaskBean> blockingDeque;
    private boolean isStart = true;

    public BlockTask(LinkedBlockingDeque<BlockTaskBean> blockingDeque) {
        this.blockingDeque = blockingDeque;
    }

    @Override
    public void run() {
        while (isStart) {
            try {
                if (blockingDeque.isEmpty()){
                    onEmpty();
                }
                BlockTaskBean take = blockingDeque.take();
                todo(take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setBlockEmptyListener(BlockExecListener blockEmptyListener) {
        this.blockEmptyListener = blockEmptyListener;
    }

    public void onEmpty(){
        if (blockEmptyListener!=null){
            blockEmptyListener.onEmpty();
        }
    }

    public void todo(BlockTaskBean blockTaskBean) throws InterruptedException {
        if (blockEmptyListener!=null){
            blockEmptyListener.onExec(blockTaskBean);
        }
        Thread.sleep(blockTaskBean.getTime());
    }

    public void stop(){
        isStart=false;
    }

    public void addTask(BlockTaskBean blockTaskBean) {
        blockingDeque.add(blockTaskBean);
    }
}
