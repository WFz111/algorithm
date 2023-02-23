package dijkstraAlgorithm;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    //图结构
    public static Map<String,Map<String,Double>> graph = new HashMap<>();
    //标记已访问过的结点
    public static List<String> haveProcessed = new ArrayList<>();
    //起点到其他所有节点的开销列表
    public static Map<String,Double> cost = new HashMap<>();
    //所有节点的父节点列表
    public static Map<String,String> parents = new HashMap<>();

    //获取带处理的开销最小的节点
    public static String findLowestCostNode(Map<String,Double> cost){
        Double lowestCost = Double.POSITIVE_INFINITY;
        String lowestCostNode = null;
        //遍历开销列表，只有没有处理过的，并且开销最小的节点才返回
        for (Map.Entry<String,Double> node:cost.entrySet()) {
            if (!haveProcessed.contains(node.getKey()) && node.getValue() < lowestCost){
                lowestCost = node.getValue();
                lowestCostNode = node.getKey();
            }
        }
        return lowestCostNode;
    }

    public static void main(String[] args) {
        //构造图
        graph.put("start",new HashMap<>());
        graph.get("start").put("A",5.0);
        graph.get("start").put("B",2.0);

        graph.put("A",new HashMap<>());
        graph.get("A").put("C",4.0);
        graph.get("A").put("D",2.0);

        graph.put("B",new HashMap<>());
        graph.get("B").put("A",8.0);
        graph.get("B").put("D",7.0);

        graph.put("C",new HashMap<>());
        graph.get("C").put("D",6.0);
        graph.get("C").put("end",3.0);

        graph.put("D",new HashMap<>());
        graph.get("D").put("end",1.0);

        graph.put("end",new HashMap<>());

        //构造开销列表
        cost.put("A",5.0);
        cost.put("B",2.0);
        cost.put("C",Double.POSITIVE_INFINITY);
        cost.put("D",Double.POSITIVE_INFINITY);
        cost.put("end",Double.POSITIVE_INFINITY);

        String node = findLowestCostNode(cost);
        while (node!=null){
            //获取起点到达node的开销
            Double oldCost = cost.get(node);
            //获取node的邻居节点
            Map<String,Double> neighbors = graph.get(node);
            //遍历邻居，只有当加入node，使得起点到达邻居节点的开销比原来更小，才更新父节点列表和开销列表
            for (String n: neighbors.keySet()) {
                //计算起点通过node到达其他节点的新开销 = 起点到达node的开销 + node到达邻居的开销
                double newCost = oldCost + neighbors.get(n);
                if (newCost < cost.get(n)){
                    //更新起点到达其他节点的开销列表
                    cost.put(n,newCost);
                    //更新父节点列表
                    parents.put(n,node);
                }
            }
            //节点标记为已处理
            haveProcessed.add(node);
            node = findLowestCostNode(cost);
        }//while
        //输出起点到终点的开销
        System.out.println(cost.get("end"));
        //输出路径,从parents最后向前回溯
        List<String> line = new ArrayList<>();
        line.add("end");
        //获取终点的父节点
        String l = parents.get("end");
        while (!l.equals("start")){
            line.add(l);
            l = parents.get(l);
        }
        line.add("start");
        for (int i = line.size()-1; i >= 0; i--) {
            System.out.println(line.get(i));
        }
    }
}
