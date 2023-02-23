package quicksort;

import java.util.Arrays;

/**
 * 递归求解数组和
 */
public class RecursiveSumByMe {

    public static int sum(int[] arr){
        if (arr.length == 1){
            return arr[0];
        }
        return arr[0] + sum(Arrays.copyOfRange(arr,1,arr.length));
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4};
        int sum = sum(arr);
        System.out.println(sum);
    }
}
