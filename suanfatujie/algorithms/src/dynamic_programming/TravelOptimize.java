package dynamic_programming;


import java.util.Arrays;

/**
 * 动态规划
 * 旅行行程最优化
 */
public class TravelOptimize {
    public static void main(String[] args) {
        int[] value = {7,6,9,9,8};          //评分
        double[] day = {0.5,0.5,1,2,0.5};   //各地点花费的时间
        double[] capacity = {0.5,1,1.5,2};  //子背包容量
        int[][] cell = new int[day.length+1][capacity.length+1];
        //遍历网格
        for (int i = 1; i < day.length+1; i++) {
            for (int j = 1; j < capacity.length+1; j++) {
                if (capacity[j-1] >= day[i-1]){     //时间容量可以装得下当前地点
                    double left = capacity[j-1]-day[i-1];     //剩余时间
                    int index = 0;        //剩余时间所对应的的下标
                    for (int k = 0; k < capacity.length; k++) {
                        if (left == capacity[k]){
                            index = k+1;
                            break;
                        }
                    }
                    cell[i][j] = Math.max(value[i-1] + cell[i-1][index],cell[i-1][j]);  // 单元格 = max(当前地点价值 + 剩余时间容量的价值，上一个单元格的值)
                }else {                             //时间容量装不下当前地点
                    cell[i][j] = cell[i-1][j];
                }
            }//for
        }//for
        //输出网格
        for (int[] row:cell) {
            System.out.println(Arrays.toString(row));
        }
    }
}
