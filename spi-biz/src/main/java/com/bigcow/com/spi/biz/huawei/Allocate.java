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
        List<Integer> res = new ArrayList<>();
        List<int[]> freeMemorys = new ArrayList<>();
        List<int[]> usedMemorys = new ArrayList<>();
        int[] initMemory = new int[] { 0, 99 };
        freeMemorys.add(initMemory);
        for (int i = 0; i < operators.size(); i++) {
            String command = operators.get(i);
            // parse command REQUEST=10
            if (command.startsWith("REQUEST")) {
                // allocate memory
                int memoryCount = Integer.parseInt(command.split("=")[1]);
                boolean canRequest = false;
                for (int j = 0; j < freeMemorys.size(); j++) {
                    int[] cur = freeMemorys.get(j);
                    if (memoryCount <= cur[1] - cur[0]) {
                        canRequest = true;
                        // usedMemory 增加
                        usedMemorys.add(new int[] { cur[0], memoryCount - 1 });
                        // freeMemory adiust
                        freeMemorys.remove(j);
                        if (cur[1] > memoryCount + 1) {
                            freeMemorys.add(new int[] { memoryCount, cur[1] });
                        }
                        res.add(cur[0]);
                    }
                }
                if (!canRequest) {
                    res.add(null);
                }
            } else {
                int releaseVal = Integer.parseInt(command.split("=")[1]);
                boolean canRelease = false;
                for (int j = 0; j < usedMemorys.size(); j++) {
                    int[] usedMemory = usedMemorys.get(j);
                    if (releaseVal == usedMemory[0]) {
                        canRelease = true;
                        usedMemorys.remove(j);
                        // 恢复freememorys
                        for (int k = 0; k < freeMemorys.size(); k++) {
                            // 需要把前面一段空闲的， 和后面一段空闲的，合并成一段
                            // 找前一段空闲的
                            freeMemorys.get(k);
                        }
                    }
                }

                if (!canRelease) {
                    res.add(null);
                }
            }
        }
        return res;
    }
}
