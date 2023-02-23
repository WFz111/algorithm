package BinarySearchTree;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * 二叉查找树
 */
public class BST {

    private Node root;  //树根

    //定义结点
    private class Node{
        private char key; //结点的键
        private int val;    //结点的值
        private int n;      //以当前结点为根的 子树的结点数
        private Node left;  //左结点指针
        private Node right; //右结点指针

        public Node(char key,int val, int n) {
            this.key = key;
            this.val = val;
            this.n = n;
        }
    }

    public BST(){}

    //1.获取树中结点的数量 1
    public int size(){
        return size(root);
    }
    private int size(Node node){
        if (node!=null)
            return node.n;
        else
            return 0;
    }

    //2.获取键key对应的值 2
    public int get(char key){
        return get(root,key);
    }

    private int get(Node node,char key){
        if (node == null)
            return -1;
        else if (key < node.key)
            return get(node.left,key);
        else if (key > node.key)
            return get(node.right,key);
        else
            return node.val;
    }

    //3.更新结点 3
    //如果键存在于树中，更新对应的值；否则插入新的键值对
    public void put(char key,int val){
        this.root = put(this.root,key,val);
    }
    private Node put(Node node,char key,int val){
        if (node == null){
            return new Node(key,val,1); //没有就创建新的结点
        }else if (key < node.key){
            node.left = put(node.left,key,val);  //如果key小于结点，找左子树
        } else if (key > node.key) {
            node.right = put(node.right,key,val); //如果key大于结点，找右子树
        }else {
            node.val = val;
        }
        node.n = size(node.left) + size(node.right) + 1;
        return node;
    }

    //4.最小键 4
    public char min(){
        return min(root).key;
    }
    private Node min(Node node){
        if (node.left == null)
            return node;
        else
            return min(node.left);
    }

    //5.最大键 5
    public char max(){
        return max(root).key;
    }
    private Node max(Node node){
        if (node.right == null){
            return node;
        }else {
            return max(node.right);
        }
    }

    //6.floor操作
    public char floor(char key){
        Node node = floor(root,key);
        if (node == null)
            return '-';
        return node.key;
    }
    private Node floor(Node node,char key){
       if (node == null){
           return null;
       }
       if (key == node.key)
           return node;
       if (key < node.key)
           return floor(node.left,key);
       Node t = floor(node.right,key);
       //如果右子树不存在目标结点，根结点就是目标结点
       if (t != null)
           return t;
       else
           return node;
    }

    //7.选择操作
    public char select(int k){
        Node node = select(root,k);
        if (node == null)
            return '-';
        else
            return node.key;
    }
    private Node select(Node node,int k){
        if (node == null){
            return null;
        }
        int t = node.left.n;    //t是左子树结点总数
        if (k < t)
            return select(node.left,k);
        else if (k > node.left.n)
            return select(node.right,k-t-1);
        return node;
    }

    //8.排名
    public int rank(char key){
        return rank(root,key);
    }
    private int rank(Node node,char key){
        if (node == null)
            return 0;
        int t = node.left.n;
        if (key == node.key)
            return t;
        else if (key < node.key)
            return rank(node.left,key);
        else
            return rank(node.right,key) + t + 1;
    }

    //9.删除最小键
    public void deleteMin(){
        root = deleteMin(root);
    }
    private Node deleteMin(Node node){
        Node min = null;
        if (node.left == null) {
            min = node;
            return min.right;  //找到最小结点，返回其右子树
        }
        node.left = deleteMin(node.left);
        node.n = size(node.left) + size(node.right) + 1;
        return node;
    }

    //10.删除最大键
    public void deleteMax(){
        root = deleteMax(root);
    }
    private Node deleteMax(Node node){
        Node max = null;
        if (node.right == null){
            max = node;
            return max.left; //找到最大结点，返回其左子树
        }else {
            node.right = deleteMax(node.right);
            return node;
        }
    }

    //11.删除任意节点
    public void delete(char key){
        root = delete(root,key);
    }
    private Node delete(Node node,char key){
        if (node == null)
            return null;
        //要删除的结点在左子树中
        if (key < node.key)
            node.left = delete(node.left,key);
        //要删除的结点在右子树中
        else if (key > node.key)
            node.right = delete(node.right,key);
        //找到要删除的结点
        else {
            //无左有右，返回右子树
            if (node.left == null)
                return node.right;
            //有左无右，返回左子树
            if (node.right == null)
                return node.left;
            //有左有右
            Node t = node; //t是被删除结点
            node = min(t.right); // node是t的后继结点
            node.right = deleteMin(t.right);
            node.left = t.left;
        }
        //更新结点数
        node.n = size(node.left) + size(node.right) + 1;
        return node;
    }

    //12.范围查找结点
    public Iterable<Character> keys(){
        return keys(min(),max());//顺序获取所有键
    }
    public Iterable<Character> keys(char lo,char hi){
        Queue<Character> queue = new ArrayDeque<>();
        keys(root,queue,lo,hi);
        return queue;
    }
    private void keys(Node node, Queue<Character> queue,char lo,char hi){
        if (node == null)
            return;
        //判断lo在左子树还是右子树
        if (lo < node.key){
            keys(node.left,queue,lo,hi);
        }
        if (lo <= node.key && hi >= node.key){
            queue.add(node.key);
        }
        if (hi >= node.key){
            keys(node.right,queue,lo,hi);
        }
    }

    //13.判断树是否包含键
    public boolean contains(char key){
        return get(key) != -1;
    }

    //14.判断树是否为空
    public boolean isEmpty(){
        return size() == 0;
    }


    public static void main(String[] args) {
        BST bst = new BST();
        Scanner scanner = new Scanner(System.in);
        char key = scanner.next().charAt(0);
        int i=0;
        while (key!='-'){
            bst.put(key,i);
            key = scanner.next().charAt(0);
            i++;
        }
        Iterable<Character> queue = bst.keys('e','t');
        for (char k:queue) {
            System.out.println(k);
        }
    }

}
