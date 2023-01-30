package selection_sort;

import java.util.Arrays;

/**
 * 在原数组上进行排序
 */
public class SelectionSortByMe {

    public static int[] selectionSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            int index = findSmallest(i,arr); //找到最小元素的下标
            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
        return arr;
    }

    //start为开始寻找的下标
    public static int findSmallest(int start,int[] arr){
        int smallest_index = start;
        int smallest = arr[start];
        for (int i = start; i < arr.length; i++) {
            if (arr[i]<smallest){
                smallest_index = i;
                smallest = arr[i];
            }
        }
        return smallest_index;
    }

    public static void main(String[] args) {
        int[] arr = {5,3,6,2,10};
        int[] arr2 = selectionSort(arr);
        System.out.println(Arrays.toString(arr2));
    }
}
