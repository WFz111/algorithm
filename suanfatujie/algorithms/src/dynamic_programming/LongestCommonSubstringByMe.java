package dynamic_programming;

import java.util.Arrays;

/**
 * 动态规划
 * 最长公共子串
 */
public class LongestCommonSubstringByMe {
    public static void main(String[] args) {
        String stringA="fish";
        String stringB="hish";
        //网格
        int[][] cell = new int[stringA.length()][stringB.length()];
        for (int i = 0; i < stringA.length(); i++) {
            for (int j = 0; j < stringB.length(); j++) {
                if (stringA.charAt(i) == stringB.charAt(j)){      //字符相同
                    if (i>0 && j>0){    //有左上对角线邻居
                        cell[i][j] = cell[i-1][j-1] + 1;
                    }else {             //没有左上对角线邻居
                        cell[i][j] = 1;
                    }
                }else {                                           //字符不相同
                    cell[i][j] = 0;
                }
            }//for
        }//for
        //输出网格
        int max = 0;
        for (int[] row:cell) {
            System.out.println(Arrays.toString(row));
            for (int temp:row) {
                if (temp > max){
                    max = temp;
                }
            }
        }//for
        System.out.println("最长公共子串长度="+max);
    }
}
