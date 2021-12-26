package com.bigcow.cn.code.排序;

import org.junit.Test;

public class mergeSort {

    @Test
    public void mergeSort() {
        int[] arr = { 3, 7, 5, 9, 10, 11, 0, -1 };
        mergeSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print("--->" + arr[i]);
        }
    }

    public void mergeSort(int[] array) {
        // 先找到数组的中间位置
        // 将数组分成2个子数组
        // 归并排序2个子数组
        // 待子数组有序后， merge 合并
        mergeSort(array, 0, array.length - 1);
    }

    public void mergeSort(int[] array, int i, int j) {
        if (i >= j) {
            return;
        }
        int mid = (j + i) / 2;
        mergeSort(array, i, mid);
        mergeSort(array, mid + 1, j);

        // merge
        merge(array, i, j, mid);
    }

    // merge [i, mid], [mid+1, j]的数据
    public void merge(int[] array, int i, int j, int mid) {
        int[] temps = new int[array.length];
        int start = i;
        int end = j;
        int right = mid + 1;
        int tempIndex = i;
        while (i <= mid && right <= j) {
            if (array[i] <= array[right]) {
                temps[tempIndex] = array[i];
                i++;
            } else {
                temps[tempIndex] = array[right];
                right++;
            }
            tempIndex++;
        }

        while (i <= mid) {
            temps[tempIndex] = array[i];
            i++;
            tempIndex ++;
        }
        while (right <= j) {
            temps[tempIndex] = array[right];
            right++;
            tempIndex ++;
        }

        //再将temps 还原回去
        for (int k = start; k <= end; k++) {
            array[k] = temps[k];
        }
    }

}
