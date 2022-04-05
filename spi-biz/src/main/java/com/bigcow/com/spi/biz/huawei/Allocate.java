package com.bigcow.com.spi.biz.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Allocate {

    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();
            List<String> operators = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                operators.add(sc.next());
            }
            List<Integer> lists = allocateMemory(operators);
            printRes(lists);

        //        int[][] buffer = new int[100][2];
        //        buffer[9][0] = 1;
        //        buffer[9][1] = 2;
        //        buffer[10][0] = 1;
        //        buffer[10][1] = 2;
        //        buffer[19][0] = 1;
        //        buffer[19][1] = 2;
        //        requestMemory(3, 10, buffer);
    }

    private static void printRes(List<Integer> lists) {
        for (int i = 0; i < lists.size(); i++) {
            Integer cur = lists.get(i);
            if (cur == null) {
                System.out.println("error");
            } else {
                System.out.println(cur);
            }
        }
    }

    private static List<Integer> allocateMemory(List<String> operators) {
        // buffer每行数据有2位
        // 第一位表示是否占用， 用1， 0表示， 1表示占用了， 0表示未占用
        // 第二位表示是第几次操作的占用，release时，某一次的buffer可以统一清理
        int[][] buffer = new int[100][2]; // 初始化缓存都没有占用
        // res为null 表示有错误输出
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < operators.size(); i++) {
            String command = operators.get(i);
            // parse command REQUEST=10
            if (command.startsWith("REQUEST")) { // 请求内存
                int memoryCount = Integer.parseInt(command.split("=")[1]);
                res.add(requestMemory(i, memoryCount, buffer));
            } else { // 释放内存
                int releaseIndex = Integer.parseInt(command.split("=")[1]);
                releaseMemory(res, releaseIndex, buffer);
            }
        }
        return res;
    }

    // 释放内存成功，则不输出
    // 释放内存失败， res add null
    private static void releaseMemory(List<Integer> res, int releaseIndex, int[][] buffer) {
        if (releaseIndex < 0 || releaseIndex >= 100 || buffer[releaseIndex][0] == 0) {
            res.add(null);
            return;
        }
        int curOp = buffer[releaseIndex][1];
        if (releaseIndex > 0 && buffer[releaseIndex - 1][1] == curOp) {
            res.add(null);
            return;
        }

        // 开始释放内存
        int cur = releaseIndex;
        while (cur < 100 && buffer[cur][1] == curOp) {
            buffer[cur][0] = 0;
            buffer[cur][1] = 0;
            cur++;
        }
    }

    private static Integer requestMemory(int index, int memoryCount, int[][] buffer) {
        if (memoryCount == 0) {
            return null;
        }
        int start = -1;
        // 从缓存中找到未使用的memory, 长度为memoryCount
        for (int i = 0; i < 100; i++) {
            if (start == -1 && buffer[i][0] == 0) {
                start = i;
                int end = i;
                while (end < 100 && end - start + 1 <= memoryCount) {
                    if (buffer[end][0] == 0) {
                        end++;
                    } else {
                        break;
                    }
                }
                if (end - start == memoryCount) {
                    for (int j = start; j < end; j++) {
                        buffer[j][0] = 1;
                        buffer[j][1] = index;
                    }
                    return start;
                } else {
                    start = -1;
                    i = end;
                }
            }
        }
        if (start == -1) {
            return null;
        }
        return start;
    }

    //    private static List<Integer> allocateMemory(List<String> operators) {
    //        List<Integer> res = new ArrayList<>();
    //        List<int[]> freeMemorys = new ArrayList<>();
    //        List<int[]> usedMemorys = new ArrayList<>();
    //        int[] initMemory = new int[] { 0, 99 };
    //        freeMemorys.add(initMemory);
    //        for (int i = 0; i < operators.size(); i++) {
    //            String command = operators.get(i);
    //            // parse command REQUEST=10
    //            if (command.startsWith("REQUEST")) {
    //                // allocate memory
    //                int memoryCount = Integer.parseInt(command.split("=")[1]);
    //                boolean canRequest = false;
    //                for (int j = 0; j < freeMemorys.size(); j++) {
    //                    int[] cur = freeMemorys.get(j);
    //                    if (memoryCount <= cur[1] - cur[0]) {
    //                        canRequest = true;
    //                        // usedMemory 增加
    //                        usedMemorys.add(new int[] { cur[0], memoryCount - 1 });
    //                        // freeMemory adiust
    //                        freeMemorys.remove(j);
    //                        if (cur[1] > memoryCount + 1) {
    //                            freeMemorys.add(new int[] { memoryCount, cur[1] });
    //                        }
    //                        res.add(cur[0]);
    //                    }
    //                }
    //                if (!canRequest) {
    //                    res.add(null);
    //                }
    //            } else {
    //                int releaseVal = Integer.parseInt(command.split("=")[1]);
    //                boolean canRelease = false;
    //                for (int j = 0; j < usedMemorys.size(); j++) {
    //                    int[] usedMemory = usedMemorys.get(j);
    //                    if (releaseVal == usedMemory[0]) {
    //                        canRelease = true;
    //                        usedMemorys.remove(j);
    //                        // 恢复freememorys
    //                        for (int k = 0; k < freeMemorys.size(); k++) {
    //                            // 需要把前面一段空闲的， 和后面一段空闲的，合并成一段
    //                            // 找前一段空闲的
    //                            freeMemorys.get(k);
    //                        }
    //                    }
    //                }
    //
    //                if (!canRelease) {
    //                    res.add(null);
    //                }
    //            }
    //        }
    //        return res;
    //    }
}
