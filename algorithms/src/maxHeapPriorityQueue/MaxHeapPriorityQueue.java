package maxHeapPriorityQueue;

import java.util.Arrays;

/**
 * 二叉堆实现的优先队列
 */
public class MaxHeapPriorityQueue {
    private int[] pq; //数组存储元素实现二叉堆,从index=1开始
    private int n; //优先队列中的元素个数

    /**
     * 结点下沉
     * @param k 需要下沉结点的位置
     */
    private void sink(int k){
        //只要结点还有子节点(从左子节点开始)，就进行循环判断
        while (2*k <= n){   //若pq从index=0开始存储，左子结点为2k+1
            //j是k的左子结点位置
            int j = 2*k;
            //取子结点中较大的一个 pq[j]左子结点 pq[j+1]右子结点
            if (j<n && pq[j]<pq[j+1]){
                j++;
            }
            //如果结点比较大子节点都大，那么说明结点比两个子结点都大，结束循环
            if (pq[k]>pq[j]){
                break;
            }
            //与较大的子结点进行交换
            int temp = pq[j];
            pq[j] = pq[k];
            pq[k] = temp;
            k=j;
        }
    }

    /**
     * 结点上浮
     * @param k 需要上浮结点的位置
     */
    private void swim(int k){
        //只要结点比父节点大就上浮
        while (k>1 && pq[k]>pq[k/2]){
            //交换结点k与其父节点的位置
            int temp = pq[k];
            pq[k] = pq[k/2];
            pq[k/2] = temp;
            //上浮之后的位置，就是其父节点的位置
            k=k/2;
        }
    }

    /**
     * 插入新的元素
     * @param value 要插入的新元素
     */
    public void insert(int value){
        //将新元素插入到数组末尾，然后将其上浮到合适位置
        n++;
        pq[n] = value;
        swim(n);
    }

    /**
     * 删除最大元素
     */
    public int deleteMax(){
        //将最后一个元素移到堆顶，下沉到合适位置
        int max = pq[1];
        pq[1] = pq[n];
        pq[n] = Integer.MIN_VALUE;   //防止越界
        n--;
        sink(1);
        return max;
    }

    public static void main(String[] args) {
        MaxHeapPriorityQueue mhp = new MaxHeapPriorityQueue();
        int[] arr = {0,8,7,5,6,4,1,2,0,3,0};
        mhp.n = 9;
        mhp.pq = arr;
        mhp.insert(9);
        System.out.println(Arrays.toString(mhp.pq));//9 8 5 6 7 1 2 0 3 4
    }
}
