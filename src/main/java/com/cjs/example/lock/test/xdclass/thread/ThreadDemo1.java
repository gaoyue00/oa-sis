package com.cjs.example.lock.test.xdclass.thread;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2020/4/2 13:28
 */
public class ThreadDemo1 extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"------");
    }
}
