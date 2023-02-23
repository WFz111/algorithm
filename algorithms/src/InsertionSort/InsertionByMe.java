package InsertionSort;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertionByMe {

    public static int[] InsertionSort(int[] arr){
        for (int i = 1; i < arr.length; i++) {  //以i作为遍历元素的指针,第一个元素当做有序序列开始
            int j = i;                          //i指向当前需要进行排序的元素
            while(j>0 && arr[j]<arr[j-1]) {     //以j作为从后向前移动寻找元素的指针，一边比较一边进行交换
                int temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                j--;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {5,2,4,6,1,3};
        arr = InsertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
