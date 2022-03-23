package com.bigcow.com.spi.biz.microsoft;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Code6 {

    public static void main(String[] args) {
        int[] arr = {5,7,-3,-1,2,7,10};
        int[] res = findMaxValue(arr, 3);
        for (int i = 0; i < res.length; i++) {
            System.out.print("---" + res[i]);
        }
    }

    /**
     *  5,7,-3,-1,2,7,10
     *  7,7,2,7,10
     *  
     *  双端队列： 单调递减， 从队列头部可以取到最大值
     *
     *  最大值从队列的头部获取7，7，2，7
     *  
     * @param arr
     * @param k
     * @return
     */
    // 数组，滑动窗口
    public static int[] findMaxValue(int[] arr, int k) {
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < k - 1; i++) {
            while (!queue.isEmpty() && arr[queue.getLast()] < arr[i]) {
                queue.removeLast();
            }
            queue.add(i);
        }
        List<Integer> res = new ArrayList<>();
        for (int i = k - 1; i < arr.length; i++) {
            while (!queue.isEmpty() && arr[queue.getLast()] < arr[i]) {
                queue.removeLast();
            }
            queue.add(i);
            if (i - queue.getFirst() >= k) {
                queue.removeFirst();
            }
            res.add(arr[queue.getFirst()]);
        }

        int[] ans = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ans[i] = res.get(i);
        }
        return ans;
    }
}
