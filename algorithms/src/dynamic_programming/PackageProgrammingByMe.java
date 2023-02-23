package dynamic_programming;

import java.util.Arrays;

/**
 * 动态规划
 * 背包问题
 */
public class PackageProgrammingByMe {
    public static void main(String[] args) {
        //物品的重量数组,依次为：吉他，音响，笔记本电脑
        int[] weight = {1,4,3};
        //物品价值
        int[] value = {1500,3000,2000};
        //背包容量
        int capacity = 4;
        //网格,首行首列位置不使用，使得j对应子背包的容量,因为背包重量从1开始
        int[][] bag = new int[weight.length+1][capacity+1];
        //遍历网格
        for (int i = 1; i < weight.length+1; i++) {   //i表示物品
            for (int j = 1; j < capacity+1; j++) {    //j表示背包容量
                if (j >= weight[i-1]){                //子背包装得下当前物品
                    bag[i][j] = Math.max(value[i-1] + bag[i-1][j-weight[i-1]],bag[i-1][j]) ;   //单元格 =max（当前物品价值 + 剩余空间的价值，上个单元格的值）
                }else {                               //子背包装不下当前物品
                    bag[i][j] = bag[i-1][j];
                }
            }//for
        }//for
        //输出网格
        for (int[] row:bag) {
            System.out.println(Arrays.toString(row));
        }
    }
}
