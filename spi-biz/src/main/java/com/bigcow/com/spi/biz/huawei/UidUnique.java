package com.bigcow.com.spi.biz.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class UidUnique {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<String> list1 = new ArrayList();
        for (int i = 0; i < n; i++) {
            list1.add(sc.next());
        }

        int m = sc.nextInt();
        List<String> list2 = new ArrayList();
        for (int i = 0; i < m; i++) {
            list2.add(sc.next());
        }

        Set<String> uniqueSets = getUniqueIds(list1, list2);
        String[] arr = new String[uniqueSets.size()];
        int index = 0;
        for (String oneStr : uniqueSets) {
            arr[index] = oneStr;
            index++;
        }
        Arrays.sort(arr);
        System.out.println(printArr(arr));
    }

    private static String printArr(String[] arr) {
        if (arr.length == 0) {
            return "[]";
        }
        String res = "[" + arr[0];
        for (int i = 1; i < arr.length; i++) {
            res += " " + arr[i];
        }
        return res + "]";
    }

    private static Set<String> getUniqueIds(List<String> list1, List<String> list2) {
        Set<String> set = new HashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        return set;
    }
}
