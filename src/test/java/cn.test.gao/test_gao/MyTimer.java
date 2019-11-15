package cn.test.gao.test_gao;

import java.time.LocalDate;
import java.util.TimerTask;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2019/11/12 16:56
 */
public class MyTimer extends TimerTask {


    @Override
    public void run() {
        LocalDate now = LocalDate.now();
        System.out.println("当前时间：" + now);
    }
}
