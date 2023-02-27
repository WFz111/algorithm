package BinarySearchTree;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

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
        private Node parent;        //父结点指针
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
     * 获取树的最小结点
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
     * 获取树的最大结点
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
    public Iterable<Node> keys(char lo, char hi){
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
     * 获取结点h的父结点
     * @param h 结点
     * @return h的父结点
     */
    private Node parentOf(Node h){
        if (h == null)
            return null;
        return h.parent;
    }

    /**
     * 查找结点
     * @param h 根结点
     * @param key 目标结点键
     * @return 目标结点
     */
    private Node search(Node h,char key){
        if (h == null)
            return null;
        if (key < h.key){
            return search(h.left,key);
        }else if (key == h.key){
            return h;
        }else {
            return search(h.right,key);
        }
    }

    /**
     * 结点左旋转
     * @param h 旋转结点
     */
    private void rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        if (x.left != null){
            x.left.parent = h;
        }
        x.parent = h.parent;
        if (h.parent == null){
            this.root = x;
        }else {
            if (h == h.parent.left)
                h.parent.left = x;
            else
                h.parent.right = x;
        }
        x.left = h;
        h.parent = x;
        //设置颜色
        x.color = h.color;
        h.color = RED;
        //更新以x、h为子树的结点数
        x.n = h.n;
        h.n = size(h.left) + size(h.right) + 1;
    }

    /**
     * 结点右旋转
     * @param h 旋转结点
     */
    private void rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        if (x.right != null){
            x.right.parent = h;
        }
        x.parent = h.parent;
        if (h.parent == null){
            this.root = x;
        }else {
            if (h == h.parent.left)
                h.parent.left = x;
            else
                h.parent.right = x;
        }
        x.right = h;
        h.parent = x;
        //设置颜色
        x.color = h.color;
        h.color = RED;
        //更新以x、h为子树的结点数
        x.n = h.n;
        h.n = size(h.left) + size(h.right) + 1;
    }

    /**
     * 用非递归的方法实现普通红黑树的结点插入
     * @param key 插入结点键
     * @param val 插入结点值
     */
    public void put(char key,int val){
        //如果根结点为空，创建一个根结点，插入结束
        if (this.root == null){
            this.root = new Node(key,val,1,RED);
        }else { //否则找插入的位置
            Node parent = null; //标记要插入位置的父结点
            Node h = this.root; //h为当前结点
            while (h!=null){
                parent = h;
                if (key < h.key){                 //如果新插入的键小于当前结点，往左子树找
                    h = h.left;
                }else if (key == h.key){        //结点已经存在树中，更新其值，结束插入
                    h.val = val;
                    return;
                }else {
                    h = h.right;                //往右子树找
                }
            }//while
            //找到插入位置
            Node node = new Node(key,val,1,RED);
            node.parent = parent;   //新建结点的父结点设置为parent
            if (key < parent.key){
                parent.left = node;
            }else {
                parent.right = node;
            }
            //从新结点开始调整修复红黑树
            putFixUp(node);
        }
        //根结点设置为黑色
        this.root.color = BLACK;
    }

    /**
     * 插入结点之后的修复
     * 1.树为空时插入就是根结点不需要修复，因为不会调用这个函数
     * 2.当插入结点的父结点是黑色时不需要修复
     * 3.只有插入结点的父结点是红色时需要修复
     *
     * @param h 需要修复的当前结点
     */
    private void putFixUp(Node h){
        Node parent,grandparent;    //当前结点的父结点和祖父结点
        //只有插入结点的父结点是红色时，需要调整
        while ((parent=parentOf(h))!=null && isRed(parent)){
            grandparent = parentOf(parent);

            if (parent == grandparent.left){        //以L开头的情况：（LR，LL，上溢LR，上溢LL）——父结点是祖父结点的左孩子

                Node uncle = grandparent.right;     //获得叔父节点，用于判断是否上溢

                /**
                 * case 1 (上溢LR，上溢LL):叔父结点存在并且为红色，是上溢的情况
                 */
                if ((uncle!=null) && isRed(uncle)){
                    uncle.color = BLACK;        //叔父结点涂黑
                    parent.color = BLACK;       //父结点涂黑
                    grandparent.color = RED;    //祖父结点涂红
                    h = grandparent;            //以祖父结点作为当前结点递归修复
                    continue;
                }

                /**
                 * case 2 (LR):叔父结点为黑色(不存在也是黑色)，且插入结点是父结点的右孩子
                 * 以父结点左旋，再以祖父结点右旋(以祖父结点右旋在case3完成)
                 */
                if (h == parent.right){
                    rotateLeft(parent);
                    //更新旋转之后的父结点和当前结点，准备跳出if之后的祖父结点右旋
                    Node temp;
                    temp = parent;
                    parent = h;
                    h = temp;
                }

                /**
                 * case 3 (LL):叔父结点为黑色，且插入结点是父结点的左孩子
                 * 以祖父结点进行右旋
                 */
                rotateRight(grandparent);

            }else {                    //以R开头的情况：（RL，RR，上溢RL，上溢RR）——父结点是祖父结点的右孩子

                Node uncle = grandparent.left;      //获取叔父节点，判断是否上溢

                /**
                 * case 1:叔父结点存在并且为红色，是上溢的情况 (上溢RL，上溢RR)
                 */
                if ((uncle!=null) && isRed(uncle)){
                    uncle.color = BLACK;        //叔父结点涂黑
                    parent.color = BLACK;       //父结点涂黑
                    grandparent.color = RED;    //祖父结点涂红
                    h = grandparent;            //以祖父结点作为当前结点递归修复
                    continue;
                }

                /**
                 * case 2:叔父结点为黑色，且插入结点是父结点的左孩子(RL)
                 * 以父结点右旋，再以祖父结点左旋(以祖父结点左旋在case3完成)
                 */
                if (h == parent.left){
                    rotateRight(parent);
                    //更新旋转之后的父结点和当前结点，准备跳出if之后的祖父结点左旋
                    Node temp;
                    temp = parent;
                    parent = h;
                    h = temp;
                }

                /**
                 * case 3:叔父节点是黑色，且插入结点是父结点的右孩子
                 * 以祖父结点左旋
                 */
                rotateLeft(grandparent);
            }
        }
    }


    /**
     * 删除结点(非递归实现)
     * @param key 目标结点的键
     */
    public void delete(char key){
        Node h = search(this.root,key);     //寻找目标结点
        if (h == null)
            return;
        delete(h);                          //删除目标结点
    }
    private void delete(Node h){

        Node replace ;      //目标结点的代替结点

        //1.删除的目标结点有左右孩子，用后继结点代替目标结点
        if (h.left!=null && h.right!=null){
            replace = min(h.right);    //右子树的最小结点是后继结点
            h.key = replace.key;
            h.val = replace.val;
            h = replace;                    //替换之后，要删除的结点变为代替结点
        }

        //2.删除的目标结点没有孩子，就是叶子结点
        if (h.left == null && h.right == null){
            //删除的是叶子结点，判断结点颜色，黑色需要修复，红色不用修复

            //要删除的结点没有父结点，就是根结点
            if (h.parent == null){
                this.root = null;
                return;
            }

            //删除的叶子结点是黑色的，需要调整
            if (!isRed(h)){
                deleteFixUp(h);
            }

            //删除叶子结点
            if (h == h.parent.left){
                h.parent.left = null;
            }else {
                h.parent.right = null;
            }
            h.parent = null;
        }else {

        //3.否则要删除的目标结点只有一棵子树，根据红黑树的性质，这棵子树只有一个结点，用该孩子结点代替目标结点
            if (h.left!=null){          //找到替换子结点
                replace = h.left;
            }else {
                replace = h.right;
            }

            //用子结点替换目标结点
            if (parentOf(h) == null){   //如果目标删除结点的父结点是空，那么目标结点是根结点，更新
                this.root = replace;
            }else if (h.parent.left == h){  //如果目标删除结点是父结点的左孩子，令父结点的左链接指向代替结点
                h.parent.left = replace;
            }else {                         //否则，令父结点的左链接指向代替结点
                h.parent.right = replace;
            }

            //删除结点
            h.parent = null;
            h.left = null;
            h.right = null;
            //将替换结点改为黑色，因为根据红黑树的性质，单子树时删除的结点一定是黑色的，替换结点是红色的，将替换结点修改为删除结点的颜色
            replace.color = BLACK;
            //删除红色叶子结点不需要做修复调整，删除结束
        }

    }

    /**
     * 删除结点之后修复红黑树
     * @param h 当前结点
     */
    private void deleteFixUp(Node h){
        Node brother;   //当前结点的兄弟结点

        //递归调整结束的条件：当前结点为根结点 或者 当前结点为红色结点
        while (h!=this.root && !isRed(h)){
            /*
             * 如果要删除的目标结点是父结点的  左子结点
             */
            if (h == h.parent.left){
                brother = h.parent.right;   //获取兄弟结点

                /**
                 * 1.删除的兄弟结点为 红色
                 * 对目标结点的父结点左旋，再对目标结点进行判断
                 */
                if (isRed(brother)){
                    rotateLeft(h.parent);
                    brother = h.parent.right;
                }

                /**
                 * 2.删除的兄弟结点为 黑色
                 */
                //（1）兄弟结点没有红色子结点
                if (!isRed(brother.left) && !isRed(brother.right)) {

                    if (isRed(h.parent)) {   //如果父结点是红色,父变黑，兄弟变红，结束
                        h.parent.color = BLACK;
                        brother.color = RED;
                        h = this.root;       //结束递归调整
                    } else {                 //父结点是黑色，父变黑，兄弟变红，递归调整父结点
                        h.parent.color = BLACK;
                        brother.color = RED;
                        h = h.parent;       //继续递归
                    }

                }else {
                    //(2）兄弟结点有红色左子结点
                    if (isRed(brother.left)){
                        rotateRight(brother);       //右旋兄弟结点
                        brother = h.parent.right;   //更新旋转后的兄弟结点(旋转结束就会变成兄弟结点有红色右子结点的情况)
                    }

                    //（3）兄弟结点有红色右子结点
                    rotateLeft(h.parent);           //左旋父结点,brother就变成了旋转后的父结点
                    brother.left.color = BLACK;     //子结点变为黑色
                    brother.right.color = BLACK;

                    h = this.root;//结束递归调整
                }//else

            }//if
            /*
             * 要删除的目标结点是父结点的   右子结点
             */
            else {
                brother = h.parent.left;    //获取目标结点的兄弟结点

                /**
                 * 1.目标结点的兄弟结点是红色
                 * 对目标结点的父结点右旋，再对目标结点进行判断
                 */
                if (isRed(brother)){
                    rotateRight(h.parent);
                    brother = h.parent.left;
                }

                /**
                 * 2.兄弟结点为 黑色
                 */
                //（1）兄弟结点没有红色子结点
                if (!isRed(brother.left) && !isRed(brother.right)){
                    if (isRed(h.parent)){   //父结点是红色，父变黑，兄弟变红，结束
                        h.parent.color  = BLACK;
                        brother.color = RED;
                        h = this.root;      //结束
                    }else {                 //父结点是黑色，父变黑吗，兄弟变红，父结点变为当前结点
                        h.parent.color = BLACK;
                        brother.color = RED;
                        h = h.parent;
                    }
                }else {
                    //（2）兄弟结点有红色右子结点
                    if (isRed(brother.right)){
                        rotateLeft(brother);        //左旋兄弟结点
                        brother = h.parent.left;    //更新旋转后的兄弟结点(旋转结束就会变成兄弟结点有红色左子结点的情况)
                    }

                    //（3）兄弟结点有红色左子结点
                    if (isRed(brother.left)){
                        rotateRight(h.parent);      //右旋父结点
                        brother.left.color = BLACK; //子结点变为黑色
                        brother.right.color = BLACK;
                        h = this.root;              //结束递归调整
                    }

                }//else
            }//else

        }//while
        this.root.color = BLACK;    //每次调整都将树根设置为黑色

    }


    public static void main(String[] args) {
        System.out.println("构建红黑树：");
        Scanner scanner = new Scanner(System.in);
        RedBlackTree tree = new RedBlackTree();
        char key = scanner.next().charAt(0);
        int i=0;
        while (key!='-'){
            tree.put(key,i);
            key = scanner.next().charAt(0);
            i++;
        }
        Iterable<Node> queue = tree.keys();
        for (Node h:queue) {
            System.out.println(h.key+" color is "+h.color);
        }

        //删除结点
        System.out.println("删除结点：");
        key = scanner.next().charAt(0);
        tree.delete(key);
        queue = tree.keys();
        for (Node h:queue) {
            System.out.println(h.key+" color is "+h.color);
        }
    }
}
