package KMP;

/**
 * 字符串匹配算法
 */
public class KMP {
    /**
     * KMP字符串匹配
     * @param haystack 文本
     * @param needle 模式串
     * @return 第一次匹配字符串的起始索引
     */
    public static int strStr(String haystack, String needle) {
        int[] next = new int[needle.length()];
        //构造前缀表
        getNext(next,needle);
        int j = 0;//指针j指向模式串字符
        //遍历文本字符串
        for (int i = 0; i < haystack.length(); i++) {
            //如果当前文本字符和模式串字符不相等，根据前缀表回退指针j
            while (j>0 && haystack.charAt(i)!=needle.charAt(j))
                j = next[j-1];
            //如果相同，比较文本和模式串的下一个字符
            if (haystack.charAt(i) == needle.charAt(j))
                j++;
            //模式串遍历完成，说明匹配到子串，返回第一次出现的下标
            if (j == needle.length())
                return i-j+1;
        }
        return -1;
    }


    /**
     * 求前缀表
     * @param next 前缀表
     * @param pat 模式串
     */
    public static void getNext(int[] next,String pat){
        int j = 0;  //表明当前子串的最长相等前后缀长度，以及指向当前子串的前缀末尾字符
        next[0] = 0;//首字符没有前后缀
        //i指向当前子串的最后一个字符，也指向当前子串后缀的末尾
        for (int i = 1; i < pat.length(); i++) {
            //当前子串的前缀和后缀末尾字符不相同，需要回溯到上一个前后缀相同的地方
            while (j>0 && pat.charAt(i) != pat.charAt(j)){
                j = next[j-1];
            }
            //当前子串的前缀和后缀末尾字符相同，最长相等前后缀长度+1
            if (pat.charAt(i) == pat.charAt(j)){
                j++;
            }
            next[i] = j;    //更新当前子串的最长相等前后缀长度
        }
    }

    public static void main(String[] args) {
        String s = "mississip";
        System.out.println(strStr(s,"issip"));
    }
}
