package dynamic_programming;

import java.util.Arrays;

/**
 * 动态规划
 * 最长公共子序列
 */
public class LongestCommonSubsequenceByMe {
    public static void main(String[] args) {
        //字符串
        String stringA = "fish";
        String stringB = "hish";
        //创建网格
        int[][] cell = new int[stringA.length()][stringB.length()];
        //遍历网格求解
        for (int i = 0; i < stringA.length(); i++) {
            for (int j = 0; j < stringB.length(); j++) {
                if (stringA.charAt(i) == stringB.charAt(j)){    //当字符相同时
                    if (i>0 && j>0){    //单元格有左上对角线邻居
                       cell[i][j] = cell[i-1][j-1] + 1;
                    }else {             //没有左上对角线邻居
                       cell[i][j] = 1;
                    }
                }else {     //字符不相同时
                    if (i==0 && j==0){          //左上邻居都没有
                        cell[i][j] = 0;
                    }else if (i==0 && j>0){     //只有左没有上邻居
                        cell[i][j] = cell[i][j-1];
                    }else if (i>0 && j==0){     //只有上没有左邻居
                        cell[i][j] = cell[i-1][j];
                    }else {
                        cell[i][j] = Math.max(cell[i][j-1],cell[i-1][j]);
                    }
                }
            }//for
        }//for
        //输出网格
        int max = 0;
        for (int[] row: cell) {
            System.out.println(Arrays.toString(row));
            for (int temp : row) {
                if (temp > max) {
                    max = temp;
                }
            }
        }
        System.out.println("最长公共子序列长度="+max);
    }
}
