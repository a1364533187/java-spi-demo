package com.bigcow.com.spi.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DepositoryConsumer {

    /**
     * https://www.cnblogs.com/dongma/p/10077901.html
     * @param args
     */
    public static void main(String[] args) {
        // 方法一
        System.out.println(divideDepository(20, 10));
        // 方法二
        System.out.println(divideDepositoryLineCut(20, 10));
    }

    /**
     * 方法一：二倍均值法
     * 根据总的库存和随机数的个数，生成具体的出库的数组
     * @param totalAmount 总的库存
     * @param count 随机数的个数
     * @return
     */
    public static List<Integer> divideDepository(Integer totalAmount, Integer count) {
        List<Integer> amountList = new ArrayList<Integer>();
        Integer restAmount = totalAmount;
        Integer restPeopleNum = count;
        Random random = new Random();
        for (int i = 0; i < count - 1; i++) {
            // 随机范围：[1，剩余人均金额的两倍)，左闭右开
            int amount = random.nextInt(restAmount / restPeopleNum * 2 - 1) + 1;
            restAmount -= amount;
            restPeopleNum--;
            amountList.add(amount);
        }
        amountList.add(restAmount);
        return amountList;
    }

    /**
     * 方法二： 线段分割法
     * @param money
     * @param people
     */
    public static List<Integer> divideDepositoryLineCut(int money, int people) {
        List<Integer> team = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        int m = money - 1;
        while (team.size() < people - 1) {
            int nextInt = random.nextInt(m) + 1;//不让nextInt 为0
            if (!team.contains(nextInt)) {
                team.add(nextInt);
            }
        }
        Collections.sort(team);

        for (int i = 0; i < team.size(); i++) {
            if (i == 0) {
                result.add(team.get(i));
            } else {
                result.add(team.get(i) - team.get(i - 1));
                if (i == team.size() - 1) {
                    result.add(money - team.get(i));
                }
            }
        }
        return result;
    }
}
