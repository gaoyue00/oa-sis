package com.cjs.example.lock.test.xdclass.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2020/4/2 13:28
 */
public class test {



    public static void main(String[] args) {
      /*  ThreadDemo1 threadDemo1 = new ThreadDemo1();
        threadDemo1.setName("Demo1");
        threadDemo1.start();


        ThreadDemo2 threadDemo2 = new ThreadDemo2();
        Thread thread = new Thread(threadDemo2);
        thread.setName("Demo2");
        thread.start();

*/      test tt = new test();

        for (int i = 0; i <100 ; i++) {
            Thread thread1 =  new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                System.out.println(tt.tt());
            });
            thread1.start();

        }

    /*    FutureTask futureTask = new FutureTask(()->{
            System.out.println("task tread-->"+Thread.currentThread().getName());
            return "返回值";
        });
        Thread thread_task = new Thread(futureTask);
        thread_task.start();*/
    }
    int count = 0;
    public int tt(){
        count = count+1;
        return count;
    }
}
