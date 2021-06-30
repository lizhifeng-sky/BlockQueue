package com.android.queue.block;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author lizhifeng
 * @date 2020/9/9 10:05
 */
public class BlockTaskManager implements LifecycleObserver {
    @SuppressLint("StaticFieldLeak")
    private static volatile BlockTaskManager instance;
    private BlockTask blockTask;
    private ExecutorService singleThreadPool;
    private Handler handler;
    private Context context;
    private BlockExecListener blockEmptyListener;
    private LinkedBlockingDeque<BlockTaskBean> blockingDeque = new LinkedBlockingDeque<>();

    private  BlockTaskManager() {
        blockTask = new BlockTask(blockingDeque);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("block-task-pool-%d").build();

         int cpu = Runtime.getRuntime().availableProcessors();
         int corePoolSize = cpu + 1;
         int maximumPoolSize = cpu * 2 + 1;
         long keepAliveTime = 1L;
         TimeUnit timeUnit = TimeUnit.SECONDS;
         int maxQueueNum = 128;

        singleThreadPool = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                new LinkedBlockingQueue<>(maxQueueNum),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static BlockTaskManager getInstance() {
        if (instance == null) {
            synchronized (BlockTaskManager.class) {
                if (instance == null) {
                    instance = new BlockTaskManager();
                }
            }
        }
        return instance;
    }

    public void bindLife(FragmentActivity fragmentActivity){
        context=fragmentActivity;
        initHandler(context);
    }
    public void bindLife(Fragment fragment){
        context=fragment.getContext();
        initHandler(context);
    }

    private void initHandler(Context context) {
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        handler=new Handler(context.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what==0){
                    if (blockEmptyListener!=null){
                        blockEmptyListener.onEmpty();
                    }
                }else if (msg.what==1){
                    if (blockEmptyListener!=null){
                        blockEmptyListener.onExec((BlockTaskBean) msg.obj);
                    }
                }
            }
        };
    }

    public void start(BlockExecListener blockEmptyListener) {
        //回调到主线程
        this.blockEmptyListener=blockEmptyListener;
//        blockTask.setBlockEmptyListener(new BlockExecListener() {
//            @Override
//            public void onEmpty() {
//                //子线程
//                Log.e("BlockManager  onEmpty",Thread.currentThread().toString());
//                if (handler!=null){
//                    Message message=Message.obtain();
//                    message.what=0;
//                    handler.sendMessage(message);
//                }
//            }
//
//            @Override
//            public void onExec(BlockTaskBean blockTaskBean) {
//                Log.e("BlockManager  onExec",Thread.currentThread().toString());
//                if (handler!=null){
//                    //子线程
//                    Message message=Message.obtain();
//                    message.what=1;
//                    message.obj=blockTaskBean;
//                    handler.sendMessage(message);
//                }
//            }
//        });
//        singleThreadPool.execute(blockTask);
    }

    public void shutdown(){
        singleThreadPool.shutdown();
    }

    public  void addTask(BlockTaskBean blockTaskBean) {
        blockingDeque.add(blockTaskBean);
        BlockTask command = new BlockTask(blockingDeque);
        command.setBlockEmptyListener(new BlockExecListener() {
            @Override
            public void onEmpty() {
                //子线程
                Log.e("BlockManager  onEmpty",Thread.currentThread().toString());
                if (handler!=null){
                    Message message=Message.obtain();
                    message.what=0;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onExec(BlockTaskBean blockTaskBean) {
                Log.e("BlockManager  onExec",Thread.currentThread().toString());
                if (handler!=null){
                    //子线程
                    Message message=Message.obtain();
                    message.what=1;
                    message.obj=blockTaskBean;
                    handler.sendMessage(message);
                }
            }
        });
        singleThreadPool.execute(command);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void unregister(){
        shutdown();
        blockTask.stop();
        context=null;
        handler.removeCallbacksAndMessages(null);
        handler=null;
        blockEmptyListener=null;
        instance=null;
    }

}
