package com.cjs.example.lock.test.likou;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2020/3/31 9:56
 */
public class LiK_15 {

    public static void main(String[] args) {
        int[] arr = new int[]{5,1,1,2,0,0};
        System.out.println(Arrays.toString(sortArray(arr)));
    }

    public static int[] sortArray(int[] nums) {
        int[] result = new int[nums.length];

        for(int i=0;i<nums.length-1;i++){
            int temp = 0;
            for (int j = 1; j < nums.length-2 ; j++) {
                if (i < j){
                    temp = j;
                }
            }
            result[i]= temp;
        }
        return result;
    }


}
