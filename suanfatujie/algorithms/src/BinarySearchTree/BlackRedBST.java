package BinarySearchTree;

/**
 * 红黑树
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
        //如果在原红黑树中找不到，插入新的黑结点
        if (h == null)
            return new Node(key,val,1,BLACK);
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
            h = rotateRight(h.left);
        if (isRed(h.left) && isRed(h.right))        //左右两条链接是红色，变换颜色
            flipColors(h);

        h.n = size(h.left) + size(h.right) + 1;
        return h;
    }
}
