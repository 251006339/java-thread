package com.company.sort;

import java.util.Arrays;

public class HeapSort1 {

    public static void main(String[] args) {
        int[] arr = {4, 6, 3, 7, 3,6,2,7,2,8,2,8,33,11};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void heapSort(int arr[]) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustSort(arr, i, arr.length);
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            adjustSort(arr, 0, i);
        }
    }

    private static void adjustSort(int[] arr, int i, int length) {
        int temp = arr[i];
        //左下角
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            if (k+1 < length && arr[k] < arr[k + 1]) {
                k++;
            }
            if (arr[k] > temp) {
                arr[i] = arr[k];
                i = k;
            }
        }
        arr[i] = temp;
    }


}
