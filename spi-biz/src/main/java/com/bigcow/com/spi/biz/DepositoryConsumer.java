package com.bigcow.com.spi.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataConsumer {
    public static void main(String[] args) {
        System.out.println(divideDepository(20, 10));
    }

    /**
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

    private void nums(int total, int count) {

    }
}
