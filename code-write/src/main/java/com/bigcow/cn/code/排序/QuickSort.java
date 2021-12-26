package com.bigcow.cn.code.排序;

import org.junit.Test;

/**
 * Create by suzhiwu on 2021/12/26
 */
public class QuickSort {

    @Test
    public void testQuickSort() {
        int[] arr = { 1, 7, 5, 3, 4 };
        quickSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print("--->" + arr[i]);
        }
    }

    public void quickSort(int[] array) {
        if (array.length <= 1) {
            return;
        }
        quickSort(array, 0, array.length - 1);
    }

    public void quickSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int base = array[left];
        int end = right;
        while (left != right) {
            // 需要先从右往左排
            while (left < right && array[right] >= base) {
                right--;
            }
            swap(array, left, right);
            // 再从左往右排
            while (left < right && array[left] <= base) {
                left++;
            }
            swap(array, left, right);
        }
        quickSort(array, 0, left - 1);
        quickSort(array, left + 1, end);
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
