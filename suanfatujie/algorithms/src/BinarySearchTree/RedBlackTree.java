package BinarySearchTree;

/**
 * 普通红黑树
 */
public class RedBlackTree {

    //结点链接的颜色
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    //树根
    private Node root;

    //结点定义
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

    public RedBlackTree() {}

    /**
     * 获取树的节点数
     * @return
     */
    public int size(){
        return size(this.root);
    }
    private int size(Node h){
        if (h==null)
            return 0;
        else return h.n;
    }

    /**
     * 判断结点颜色是否为红色
     * @param h 目标结点
     * @return 红色返回true，黑色或者空返回false
     */
    public boolean isRed(Node h){
        if (h!=null)
            return h.color==RED;
        else
            return false;
    }

    /**
     * 结点左旋转
     * @param h 目标结点
     * @return x 旋转之后新的根结点
     */
    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        //设置颜色
        x.color = h.color;
        h.color = RED;
        //更新以x、h为子树的结点数
        x.n = h.n;
        h.n = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * 结点右旋转
     * @param h 目标结点
     * @return x 旋转之后新的根结点
     */
    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        //设置颜色
        x.color = h.color;
        h.color = RED;
        //更新以x、h为子树的结点数
        x.n = h.n;
        h.n = size(h.left) + size(h.right) + 1;
        return x;
    }
}
