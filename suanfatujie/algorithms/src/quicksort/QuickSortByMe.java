package quicksort;

import java.util.Arrays;

/**
 * 数组实现
 */
public class QuickSortByMe {
    /**
     * @param arr 要排序的数组
     * @param start 数组开始的下标
     * @param end 数组结束的下标
     * @return 排序好的数组
     */
    public static int[] quickSort(int[] arr,int start,int end){
        //进行排序的基本条件
        if (start<end){
            int i = start;
            int j = end;
            int base = arr[i]; //选择一个枢轴元素
            while (i<j){
                while (i<j && arr[j]>=base){        //从右往左找第一个比枢轴元素小的元素进行交换,跳出循环的时候要么i>=j,要么arr[j]<base
                    j--;
                }
                if (arr[j]<base){
                    arr[i] = arr[j];    //交换元素，i往右移一位
                    i++;
                }
                while (i<j && arr[i]<base){        //从左往右找第一个比数轴元素大的元素进行交换，跳出循环的时候要么i>=j,要么arr[i]>base
                    i++;
                }
                if (arr[i]>base){
                    arr[j] = arr[i];
                    j--;                //交换元素，j向左移一位
                }
            }
            arr[i] = base;      //枢轴元素放入最终位置

            quickSort(arr,start,i-1);   //递归快速排序左边数组
            quickSort(arr,i+1,end);    //递归快速排序右边数组
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {4,2,8,0,5,7,1,3,9};
        System.out.println(Arrays.toString(quickSort(arr,0,arr.length-1)));
    }
}
