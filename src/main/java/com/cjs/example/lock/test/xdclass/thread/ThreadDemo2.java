package com.cjs.example.lock.test.xdclass.thread;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2020/4/2 13:32
 */
public class ThreadDemo2 implements Runnable {

    private static int count = 0;

    @Override
    public void run() {
        while (count < 50){
            System.out.println(Thread.currentThread().getName()+"----------"+count++);
        }

    }
}
