package shell;

import java.util.Arrays;

/**
 * 希尔排序
 */
public class Shell {
    public static void main(String[] args) {
        int[] arr = {58,39,89,11,23,62,99,92,78,23};
        arr = shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static int[] shellSort(int[] arr){
        for (int k = arr.length/2; k >0 ; k=k/2) {        //第一层循环控制增量序列,用公式 N/2 计算
            for (int i = k; i < arr.length; i++) {        //第二层循环进行分组的插入排序，k是增量，也是每个分组的第二个元素
                int j = i;
                while (j>=k && arr[j]<arr[j-k]){
                    int temp = arr[j];
                    arr[j]=arr[j-k];
                    arr[j-k]=temp;
                    j -= k;
                }//while
            }//for
        }//for
        return arr;
    }
}
