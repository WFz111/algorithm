package quicksort;

import java.util.Arrays;
import java.util.Random;

/**
 * 随机选元法实现快速排序
 * 思想：随机生成范围在[start,end]的下标索引，取出值作为枢轴元素，与数组第一个元素进行交换，开始快速排序
 */
public class QuickSortByMe2 {

    public static int[] QuickSort(int[] arr,int start,int end){
       if (start<end){
           int i = start;
           int j = end;
           Random random = new Random();
           int s = random.nextInt(end - start + 1) + start; //生成范围在[start,end]的下标索引
           int base = arr[s];   //枢轴元素
           //枢轴元素与数组第一个元素进行交换
           arr[s] = arr[start];
           arr[start] = base;
           //进行快速排序
           while (i<j){
               //从右向左找第一个比枢轴元素小的元素并交换
               while (j>i && arr[j]>base){
                   j--;
               }
               if (arr[j]<base){
                  arr[i] = arr[j];
                  i++;
               }
               //从左向右找第一个比枢轴元素大的元素并进行交换
               while (i<j && arr[i]<base){
                   i++;
               }
               if (arr[i]>base){
                   arr[j] = arr[i];
                   j--;
               }
           }
           arr[i] = base;
           QuickSort(arr,start,i-1);
           QuickSort(arr,i+1,end);
       }
       return arr;
    }
    public static void main(String[] args) {
        int[] arr = {4,2,8,0,5,7,1,3,9};
        System.out.println(Arrays.toString(QuickSort(arr,0,arr.length-1)));
    }
}
