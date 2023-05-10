package binaryTree;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BinaryTree {
    //树根
    TreeNode root;


    //树节点
    class TreeNode{
        int val;
        TreeNode left,right;
        public TreeNode(){}
        public TreeNode(int val) {this.val = val;}
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //构造器
    public BinaryTree(){}

    public BinaryTree(int[] arr){
        this.root = createTree(arr,0);
    }

    //按照数组顺序构造二叉树，i为子结点的下标索引
    private TreeNode createTree(int[] arr,int i){
        TreeNode node = null;
        if (i < arr.length){
            node = new TreeNode(arr[i]);
            node.left = createTree(arr,2*i+1);
            node.right = createTree(arr,2*i+2);
        }
        return node;
    }


    //前序递归遍历二叉树
    public List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        preOrderTraversal(root,list);
        return list;
    }
    private void preOrderTraversal(TreeNode node,List<Integer> list){
        if (node == null)
            return;
        list.add(node.val);
        preOrderTraversal(node.left,list);
        preOrderTraversal(node.right,list);
    }

    //后序递归遍历二叉树
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        postorderTraversal(root,list);
        return list;
    }
    private void postorderTraversal(TreeNode root,List<Integer> list){
        if (root == null)
            return;
        postorderTraversal(root.left,list);//遍历左子树
        postorderTraversal(root.right,list);//遍历右子树
        list.add(root.val);//访问根结点
    }

    //中序递归遍历二叉树
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inorderTraversal(root,list);
        return list;
    }
    private void inorderTraversal(TreeNode root,List<Integer> list){
        if (root == null)
            return;
        inorderTraversal(root.left,list);//遍历左子树
        list.add(root.val);//访问根结点
        inorderTraversal(root.right,list);//遍历右子树
    }

    //非递归前序遍历，按照层次逻辑的栈迭代法
    public List<Integer> preOrderTraversalByStack(TreeNode node) {
        List<Integer> list = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.push(node);//树根入栈
        while (!stack.isEmpty()){
            node = stack.pop();//出栈顶元素
            list.add(node.val);//出栈标为已访问
            //将右子结点入栈
            if (node.right!=null){
                stack.push(node.right);
            }
            //将左子结点入栈
            if (node.left!=null){
                stack.push(node.left);
            }
        }
        return list;
    }

    //非递归前序遍历，按照深度优先逻辑的栈迭代法
    public List<Integer> preOrderTraversalByStack2(TreeNode node){
        List<Integer> list = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cNode = node;
        while (cNode!=null || !stack.isEmpty()){
            if (cNode!=null){
                stack.push(cNode);//入栈
                list.add(cNode.val);//访问结点
                cNode = cNode.left;
            }else {
                TreeNode n = stack.pop();
                cNode = n.right;
            }

        }
        return list;
    }

    //非递归中序遍历，深入树的左子树最底部的结点，才开始处理
    public List<Integer> inorderTraversalByStack(TreeNode node){
        List<Integer> list = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cNode = node;//当前结点指向根结点
        //当前结点不为空，或者栈不为空时执行下列代码
        while (cNode!=null || !stack.isEmpty()){
            //若当前结点不为空，将结点入栈
            if (cNode!=null){
                stack.push(cNode);
                cNode = cNode.left; //让左子结点为当前结点
            }else {
                //若左子结点为空，说明左子树处理完成，访问中间节点，继续处理右子树
                cNode = stack.pop();
                list.add(cNode.val);
                cNode = cNode.right;
            }
        }
        return list;
    }

    //非递归后序遍历，层次遍历法
    public List<Integer> postorderTraversalByStack(TreeNode node){
        List<Integer> list = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.push(node);//根结点入栈
        //栈不为空时执行以下代码
        while (!stack.isEmpty()){
            //弹出栈顶元素，访问
            node = stack.pop();
            list.add(node.val);
            //若左子结点不为空，将左子结点入栈
            if (node.left!=null){
                stack.push(node.left);
            }
            //若右子结点不为空，将右子结点入栈
            if (node.right!=null){
                stack.push(node.right);
            }
        }
        //对list反转一次
        Collections.reverse(list);
        return list;
    }

    //二叉树的层次遍历,使用队列实现
    public List<Integer> levelOrder(TreeNode node) {
        List<Integer> list = new ArrayList<>();
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        //树根入队
        queue.offer(node);
        //当队列不为空时，执行下列语句
        while (!queue.isEmpty()){
            node = queue.pop();//弹出队首元素
            list.add(node.val);//访问元素
            //若左子结点不为空，入队
            if (node.left!=null)
                queue.offer(node.left);
            //若右子结点不为空，入队
            if (node.right!=null)
                queue.offer(node.right);
        }
        return list;
    }



    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        BinaryTree tree = new BinaryTree(arr);
        System.out.println(tree.levelOrder(tree.root));
    }

}
