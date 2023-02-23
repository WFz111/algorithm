package binary_search;

public class BinarySearchByMe {
    public static void main(String[] args) {
        int[] array = {1,3,5,7,9};
        System.out.println(binarySearch(array,3));
    }

    //判断数组长度
    public static boolean isEmpty(int[] array){
        return array.length == 0;
    }

    public static int binarySearch(int[] array,int object){
        if (!isEmpty(array)){
            int low = 0;
            int high = array.length-1;
            while (low <= high){
                int mid = (low + high)/2;
                if (array[mid] == object){
                    return mid;
                }else if (array[mid] > object){
                    high = mid - 1;
                }else {
                    low = mid + 1;
                }
            }
        }
        return -1;
    }
}
