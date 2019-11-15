package cn.test.gao.test_gao;

import org.junit.Test;

import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.*;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2019/11/12 17:19
 */
public class TestTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 5; i++) {
            Future<Integer> result = pool.schedule(new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {
                    int num = new Random().nextInt(100);//生成随机数
                    System.out.println(Thread.currentThread().getName() + " : " + num);
                    return num;
                }
            }, 3, TimeUnit.SECONDS);


            System.out.println(result.get());
        }
        pool.shutdown();
    }



}

