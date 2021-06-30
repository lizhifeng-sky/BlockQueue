package com.android.queue;

import com.android.queue.lock.AtomicPrint;
import com.android.queue.lock.CountDownLatchPrint;
import com.android.queue.lock.CrossPrint;
import com.android.queue.lock.JoinPrint;
import com.android.queue.lock.SemaphorePrint;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void lock(){
        ReentrantLockDemo reentrantLockDemo=new ReentrantLockDemo();
        reentrantLockDemo.lock();
    }

    @Test
    public void tryLock(){
        ReentrantLockDemo reentrantLockDemo=new ReentrantLockDemo();
        reentrantLockDemo.tryLock();
    }

    @Test
    public void print(){
//        new CrossPrint(new ObjectPrint()).print();
//        new CrossPrint(new ConditionPrint()).print();
//        new CrossPrint(new VolatilePrint()).print();
//        new CrossPrint(new LockSupportPrint()).print();
        new CrossPrint(new JoinPrint()).print();
//        new CrossPrint(new SemaphorePrint()).print();
//        new CrossPrint(new AtomicPrint()).print();
//        new CrossPrint(new CountDownLatchPrint()).print();
    }
}