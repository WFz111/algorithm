package maxHeapPriorityQueue;

import java.util.Arrays;

/**
 * 堆排序
 */
public class Heap {

    /**
     * 构造堆
     * @param pq 无序堆用数组存储,下标从0开始
     */
    public static void sort(int[] pq){
        int n= pq.length; //n是元素个数
        //阶段1：构造有序堆
        //从最后一个非叶子结点开始调整堆
        for (int i = n/2-1; i >= 0 ; i--) {
            sink(pq,i,n);
        }
        //阶段2：交换堆顶元素和末尾元素
        int k = n-1; //k指向末尾元素
        while (k>0){
            //交换堆顶元素和末尾元素
            exchange(pq,0,k);
            //调整堆为有序堆,将堆顶下沉
            sink(pq,0,k);
            //元素个数减一
            k--;
        }
    }

    /**
     * 结点下沉
     * @param pq 堆
     * @param k 目标结点的下标索引
     * @param n 元素个数
     */
    public static void sink(int[] pq,int k,int n){
        //从目标结点的左子结点开始比较，只要还有左子结点就循环检查下沉
        while (2*k+1<n){
            int j=2*k+1;  //j指向k的子节点
            //获取较大子节点的下标
            if (j<n-1 && pq[j]<pq[j+1]){
                j++;
            }
            //结点已经比较大的子节点都大，说明不需要再下沉
            if (pq[k]>pq[j]){
                break;
            }
            //交换目标结点与较大子节点的位置
            exchange(pq,k,j);
            k=j;
        }
    }

    /**
     * 交换元素
     * @param pq 交换的数组
     * @param i 目标元素1的下标
     * @param j 目标元素2的下标
     */
    public static void exchange(int[] pq,int i,int j){
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    public static void main(String[] args) {
        int[] pq={4,6,8,5,9};
        Heap.sort(pq);
        System.out.println(Arrays.toString(pq));
    }

}
