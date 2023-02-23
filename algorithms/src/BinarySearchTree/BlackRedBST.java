package BinarySearchTree;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * 左倾红黑树
 */
public class BlackRedBST {

    //标记链接的颜色
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;  //根结点

    //定义结点结构
    private class Node{
        private char key;           //结点的键
        private int val;            //结点的值
        private int n;              //以当前结点为根的 子树的结点数
        private Node left,right;    //左右结点指针
        private boolean color;      //指向该结点链接的颜色（结点的颜色）

        public Node(char key, int val, int n, boolean color) {
            this.key = key;
            this.val = val;
            this.n = n;
            this.color = color;
        }
    }

    public BlackRedBST(){}

    private int size(Node node){
        if (node!=null)
            return node.n;
        else
            return 0;
    }

    //1.判断结点颜色
    public static boolean isRed(Node h) {
        if (h == null)
            return false;
        return h.color == RED;
    }

    /**
     * 2.左旋转
     * @param h 旋转结点
     * @return x 旋转之后的子树的根结点
     */
    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        //设置结点的颜色
        x.color = h.color;
        h.color = RED;
        //更新结点数
        x.n = h.n;
        h.n = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * 3.右旋转
     * @param h 旋转结点
     * @return x 旋转之后的子树的根结点
     */
    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        //设置结点的颜色
        x.color = h.color;
        h.color = RED;
        //更新结点数
        x.n = h.n;
        h.n = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * 4.颜色变换，将父结点h左右两条红色链接变为黑色，父结点颜色由黑变红
     * @param h 父结点
     */
    private void flipColors(Node h){
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    /**
     * 5.插入新结点，如果已存在更新值，否则插入新的结点
     * @param key 键
     * @param val 值
     */
    public void put(char key,int val){
        root = put(root,key,val);
        root.color = BLACK; //每次插入都将根结点颜色设置为黑色
    }
    private Node put(Node h,char key,int val){
        //如果在原红黑树中找不到，插入新的红结点
        if (h == null)
            return new Node(key,val,1,RED);
        //在原红黑树中进行查找
        if (key < h.key){       //在左子树中递归查找插入
            h.left = put(h.left,key,val);
        }else if (key == h.key){
            h.val = val;        //找到结点更新值
        }else {
            h.right = put(h.right,key,val);     //在右子树中递归查找插入
        }
        //进行调整
        if (isRed(h.right) && !isRed(h.left))       //右链接是红色，进行左旋转
            h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))    //连续两条左链接是红色，进行右旋转
            h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))        //左右两条链接是红色，变换颜色
            flipColors(h);

        h.n = size(h.left) + size(h.right) + 1;
        return h;
    }

    /**
     * 获取树h的最小结点
     * @return
     */
    public char min(){
        return min(root).key;
    }
    private Node min(Node h){
        if (h.left == null)
            return h;
        else
            return min(h.left);
    }

    /**
     * 获取树h的最大结点
     * @return
     */
    public char max(){
        return max(root).key;
    }
    private Node max(Node h){
        if (h.right == null){
            return h;
        }else {
            return max(h.right);
        }
    }

    /**
     * 中序遍历树
     * @return
     */
    public Iterable<Node> keys(){
        return keys(min(),max());//顺序获取所有键
    }
    public Iterable<Node> keys(char lo,char hi){
        Queue<Node> queue = new ArrayDeque<>();
        keys(root,queue,lo,hi);
        return queue;
    }
    private void keys(Node node, Queue<Node> queue, char lo, char hi){
        if (node == null)
            return;
        //判断lo在左子树还是右子树
        if (lo < node.key){
            keys(node.left,queue,lo,hi);
        }
        if (lo <= node.key && hi >= node.key){
            queue.add(node);
        }
        if (hi >= node.key){
            keys(node.right,queue,lo,hi);
        }
    }

    /**
     * 删除结点时的颜色转换
     * @param h 根结点
     */
    private void flipColorsForDelete(Node h){
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    /**
     * 红色左移
     * @param h 进行红色左移的根结点
     * @return 返回红色左移之后的新的根结点
     */
    private Node moveRedLeft(Node h){
        //转换根结点h以及左右子结点的颜色
        flipColorsForDelete(h);
        //如果h的右子结点有红色子结点，右子结点进行右旋，h进行左旋，就可以完成红色左移
        if (isRed(h.right.left)){
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColorsForDelete(h);
        }
        return h;
    }

    /**
     * 红色右移
     * @param h 进行红色右移的根结点
     * @return 返回红色右移之后的新的根结点
     */
    private Node moveRedRight(Node h){
        //转换根结点h以及左右子结点的颜色
        flipColorsForDelete(h);
        //如果h的左子结点有红色子结点，h进行右旋，就可以完成红色右移
        if (isRed(h.left.left)){
            h = rotateRight(h);
            flipColorsForDelete(h);
        }
        return h;
    }

    /**
     * 调整树满足红黑树的性质
     * @param h 根结点
     * @return 调整之后的根结点
     */
    private Node balance(Node h){
        //左链接是黑色，右链接是红色，需要左旋
        if (isRed(h.right) && !isRed(h.left)){
             h = rotateLeft(h);
        }
        //如果有两条连续的红链接，需要右旋
        if (isRed(h.left) && isRed(h.left.left)){
           h = rotateRight(h);
        }
        //左右都是红色
        if (isRed(h.left) && isRed(h.right)){
            flipColorsForDelete(h);
        }
        h.n = size(h.left) + size(h.right) + 1;
        return h;
    }

    /**
     * 6.删除最小键
     */
    public void deleteMin(){
        //如果根结点的两个左右子结点都是黑色，将根结点设置为红色
        if (!isRed(this.root.left) && !isRed(this.root.right)){
            this.root.color = RED;
        }
        this.root = deleteMin(this.root);
        //将树根设置为黑色
        this.root.color = BLACK;
    }
    private Node deleteMin(Node h){
        //如果结点没有左子结点，那么h就是最小结点
        if (h.left == null)
            return null;
        //在左子树中删除结点可能需要红色左移
        //当根结点h的 左子结点 和 左子结点的左子结点 为黑色时，需要进行红色左移
        if (!isRed(h.left) && !isRed(h.left.left)){
            h = moveRedLeft(h);
        }
        //递归进入左子树进行删除
        h.left = deleteMin(h.left);
        //每次递归都需要进行修复
        return balance(h);
    }

    /**
     * 7.删除最大键
     */
    public void deleteMax(){
        //如果根结点的两个左右子结点都是黑色，将根结点设置为红色
        if (!isRed(this.root.left) && !isRed(this.root.right)){
            this.root.color = RED;
        }
        this.root = deleteMax(this.root);
        //将树根设置为黑色
        this.root.color = BLACK;
    }
    private Node deleteMax(Node h){

        //先判断红色右移，是因为最大结点可能有红色子结点（但不会有右子结点，因为是最大的），右移之后，最大结点就会变成叶子结点

        //在右子树中删除结点可能需要红色右移
        //当根结点h的 左子结点 为红色时，对左子结点进行右旋就可以完成红色右移
        if (isRed(h.left)){
            h = rotateRight(h);
        }
        //根据左倾红黑树性质，如果最大结点有红色左子结点，上面的语句会将最大结点变为红色叶子结点，且红链接在右边；
        //找到最大结点
        if (h.right == null)
            return null;

        //当根结点h的右子结点 以及 右子结点的左子结点 是黑色，需要红色右移
        if (!isRed(h.right) && !isRed(h.right.left)){
            h = moveRedRight(h);
        }
        //递归进入右子树进行删除
        h.right = deleteMax(h.right);
        return balance(h);
    }

    /**
     * 9.删除任意结点
     * @param key 目标结点
     */
    public void delete(char key){
        //如果根结点的左右子结点都是黑色，将树根变为红色
        if (!isRed(this.root.left) && !isRed(this.root.right)){
            this.root.color = RED;
        }
        this.root = delete(this.root,key);
        this.root.color = BLACK;
    }
    private Node delete(Node h,char key){
        //如果目标结点小于根结点，进入左子树递归删除
        if (key<h.key){
            //进入左子树之前需要判断是否需要红色左移
            if (!isRed(h.left) && !isRed(h.left.left)){
                h = moveRedLeft(h);
            }
            //进入左子树递归删除
            h.left = delete(h.left,key);
        }
        //如果目标结点大于等于根结点，可能进入右子树，也可能是当前结点
        else {
            //如果有红色左子结点，进行右旋，方便进入右子树删除
            if (isRed(h.left)){
                h = rotateRight(h);
            }

            //如果当前结点是目标结点，并且没有右子结点，那么目标结点是叶子结点，可以直接删除
            //因为：假设结点不是叶子结点，那么结点有孩子结点
            //如果只有左孩子，没有右孩子，根据红黑树性质左孩子一定是红色，会被上面的语句右旋，从而右孩子不为空
            if (key == h.key && h.right == null){
                return null;
            }

            //判断是否需要进行红色右移
            if (!isRed(h.right) && !isRed(h.right.left)){
                h = moveRedRight(h);
            }

            //如果当前结点是内部节点，需要找到后继节点进行替换，然后删除后继节点,就是删除右子树最小结点
            if (key == h.key){
                Node min = min(h.right);
                h.key = min.key;
                h.val = min.val;
                h.right = deleteMin(h.right);
            }
            //目标结点大于当前结点，进入右子树递归删除
            else {
                h.right = delete(h.right,key);
            }
        }
        return balance(h);
    }

    public static void main(String[] args) {
        System.out.println("构建红黑树：");
        Scanner scanner = new Scanner(System.in);
        BlackRedBST st = new BlackRedBST();
        char key = scanner.next().charAt(0);
        int i=0;
        while (key!='-'){
            st.put(key,i);
            key = scanner.next().charAt(0);
            i++;
        }
        Iterable<Node> queue = st.keys();
        for (Node h:queue) {
            System.out.println(h.key+" color is "+h.color);
        }

        System.out.println("删除结点：");
        key = scanner.next().charAt(0);
        st.delete(key);
        queue = st.keys();
        for (Node h:queue) {
            System.out.println(h.key+" color is "+h.color);
        }
    }
}
