package mergeSort;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSort {
    public static int[] temp;   //开辟临时空间用于归并排序

    //递归对数组进行排序
    public static void sort(int[] arr,int low,int high,int[] temp){
        if (low>=high)  //基线条件
            return;
        int mid = (low+high)/2;
        sort(arr,low,mid,temp);   //对左半边进行排序
        sort(arr,mid+1,high,temp);   //对右半边进行排序
        merge(arr,low,mid,high,temp);     //将两个有序的序列进行归并 [low,mid]为一组，[mid+1,high]为一组
    }

    //归并
    public static void merge(int[] arr,int low,int mid,int high,int[] temp){
        int i = low;    //左半序列的起始指针
        int j = mid+1;  //右半序列的起始指针
        int t = 0;      //临时数组的起始指针
        while (i<=mid && j<=high){  //mid是左半序列的终止位置，high是右半序列的终止位置
            //开始比较两个序列的元素，将较小的元素放入合并空间
            if (arr[i]<=arr[j]){
                temp[t++]=arr[i++];
            }else {
                temp[t++]=arr[j++];
            }
        }//while
        //将剩余的序列元素放入合并空间
        while (i<=mid){
            temp[t++]=arr[i++];
        }
        while (j<=high){
            temp[t++]=arr[j++];
        }
        //将temp中的元素拷贝到原数组
        t=0;
        while (low <= high){
            arr[low++]=temp[t++];
        }
    }

    public static void main(String[] args) {
        int[] arr = {9,8,7,6,3,1,2,4,5};
        temp = new int[arr.length];
        sort(arr,0,arr.length-1,temp);
        System.out.println(Arrays.toString(arr));
    }
}
