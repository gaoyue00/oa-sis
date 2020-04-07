package com.cjs.example.lock.test.xdclass.thread;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2020/4/2 18:02
 */
public class Yiyuan {

    public static void main(String[] args) {

        ThreadDemo2 threadDemo2 = new ThreadDemo2();
        ThreadDemo2 threadDemo1 = new ThreadDemo2();
        ThreadDemo2 threadDemo3 = new ThreadDemo2();
        ThreadDemo2 threadDemo4 = new ThreadDemo2();
        ThreadDemo2 threadDemo5 = new ThreadDemo2();
        ThreadDemo2 threadDemo6 = new ThreadDemo2();
        for (int i = 0; i < 5000; i++) {

            Thread thread = new Thread(threadDemo2);
            Thread thread1 = new Thread(threadDemo1);
            thread.setName("Demo"+i);
            thread1.setName("Demo"+i);
            Thread thread3 = new Thread(threadDemo3);
            Thread thread4 = new Thread(threadDemo4);
            thread3.setName("Demo"+i);
            thread4.setName("Demo"+i);
            Thread thread5 = new Thread(threadDemo5);
            Thread thread6 = new Thread(threadDemo6);
            thread5.setName("Demo"+i);
            thread6.setName("Demo"+i);
            thread5.start();
            thread6.start();
            thread.start();
            thread1.start();
            thread3.start();
            thread4.start();
        }

    }
}
