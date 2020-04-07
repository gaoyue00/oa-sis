package com.cjs.example.lock.test.xdclass.maps;

import java.util.*;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2020/4/3 9:30
 */
public class TestMap {

    public static void main(String[] args) {

        Map map = Collections.synchronizedMap(new HashMap());
    }
}
