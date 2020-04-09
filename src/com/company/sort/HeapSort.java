package com.company.sort;

import com.sun.org.apache.xerces.internal.xs.ItemPSVI;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {4, 1, 5, 6, 3, 2,9,-4,9};
              heapSort(arr);
        //分布完成
        System.out.println(Arrays.toString(arr));
    }
    public static void heapSort(int[] arr) {
        int temp=0;
        System.out.println("堆排序");
        //第一次最下最右
        /*adjustHeap(arr, 2, arr.length);
        adjustHeap(arr, 1, arr.length);
        adjustHeap(arr, 0, arr.length);*/
        for (int i = arr.length/2-1; i >=0 ; i--) {
            adjustHeap(arr, i, arr.length);
        }

        for (int j = arr.length - 1; j > 0; j--) {
              //交换
            temp =arr[j];
            arr[j]=arr[0];
            arr[0]=temp;
            adjustHeap(arr,0,j);
        }
    }
    //将一个数组(二叉树)调整一个大顶锥
    public static void adjustHeap(int arr[], int i, int length) {
        int temp = arr[i];
        for (int j = i * 2 + 1; j < length; j = j * 2 + 1) {
            if (j + 1 < length && arr[j] < arr[j + 1]) {//说明左子节点的值右子节点的值
                j++;//k指向右子的节点
            }
            if (arr[j] > temp) {//如果子节点大于父节点
                arr[i] = arr[j];//角儿大值赋给当前节点
                i = j;// i指向j ,继续循环比较
            } else {
                break;
            }
        }
        arr[i] = temp;
    }
}
