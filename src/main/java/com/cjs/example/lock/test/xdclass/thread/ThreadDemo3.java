package com.cjs.example.lock.test.xdclass.thread;

import java.util.concurrent.Callable;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2020/4/2 14:08
 */
public class ThreadDemo3 implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return "返回值";
    }
}
