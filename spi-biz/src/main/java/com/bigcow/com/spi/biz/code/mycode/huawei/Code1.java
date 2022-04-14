package com.bigcow.com.spi.biz.code.mycode.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Code1 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(0, 3);
        System.out.println(list);
        //        int[][] inputs = { { 7, 0 }, { 4, 4 }, { 7, 1 }, { 5, 0 }, { 6, 1 }, { 5, 2 } };
        //        List<int[]> res = reOrder(inputs);

    }

    /**
     * [7,0] [4,4] [7,1] [5,0] [6,1] [5,2]
     *
     *
     *
     * [5,0] [7,0] [5,2] [6,1] [4,4] [7,1]
     *
     * 
     * 
     *      * [7,0] [7,1] [6, 1] [5,0] [5,2] [4,4]
     *      *
     *      * [7,0]
     *      * [7,0] [7,1]
     *      * [7,0] [6, 1] [7,1]
     *      * [5,0] [7,0] [6, 1] [7,1]
     *      * [5,0] [7,0] [5,2] [6, 1] [7,1]
     *      * [5,0] [7,0] [5,2] [6, 1] [4,4] [7,1]
     */

    public static List<int[]> reOrder(int[][] inputs) {
        if (inputs == null || inputs.length <= 0) {
            return new ArrayList<>();
        }
        // 对inputs 排序
        Arrays.sort(inputs, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            }
            return o2[0] - o1[0];
        });

        List<int[]> res = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            int[] input = inputs[i];
            res.add(input[1], input);
        }
        return res;
    }
}
