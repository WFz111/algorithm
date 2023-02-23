package dijkstraAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DijkstraAlgorithmByMe {
    //图结构
    public static Map<String,Map<String,Double>> graph = new HashMap<>();
    //标记已访问过的结点
    public static List<String> haveProcessed = new ArrayList<>();
    //起点到其他所有节点的开销列表
    public static Map<String,Double> cost = new HashMap<>();
    //所有节点的父节点列表
    public static Map<String,String> parents = new HashMap<>();

    //找待处理的开销最小的结点
    public static String findLowestCostNode(Map<String,Double> cost){
        Double lowestCost = Double.POSITIVE_INFINITY; //记录最小开销
        String lowestCostNode = null; //记录最小开销结点
        //遍历开销散列表，只有当结点是开销最小，且没有被处理过才返回该节点
        for (Map.Entry<String,Double> node:cost.entrySet()) {
            Double cost_temp = node.getValue();
            if (cost_temp < lowestCost && !haveProcessed.contains(node.getKey())){
                lowestCost = cost_temp;
                lowestCostNode = node.getKey();
            }
        }
        return lowestCostNode;
    }

    public static void main(String[] args) {
        //存储图结构
        graph.put("start", new HashMap<>());
        graph.get("start").put("A",6.0);
        graph.get("start").put("B",2.0);

        graph.put("A",new HashMap<>());
        graph.get("A").put("end",1.0);

        graph.put("B",new HashMap<>());
        graph.get("B").put("A",3.0);
        graph.get("B").put("end",5.0);

        //终点没有邻居
        graph.put("end",new HashMap<>());

        //起点到其他节点的开销散列表
        cost.put("A",6.0);
        cost.put("B",2.0);
        cost.put("end",Double.POSITIVE_INFINITY);

        //父节点散列表
        parents.put("A","start");
        parents.put("B","start");
        parents.put("end",null);

        //获取待处理的开销最小的节点
        String node = findLowestCostNode(cost);
        while (node!=null){
            //获取起点到node的开销
            Double cost_old = cost.get(node);
            //更新起点到node的邻居的开销
            //获取node的邻居节点
            Map<String,Double> neighborhood = graph.get(node);
            //只有加入这个node节点之后，起点到n的开销比原来的小，才更新开销和父节点散列表
            for (String n: neighborhood.keySet()) {
                //计算起点到新节点的开销 = 起点到该节点的开销 + 该节点到邻居的开销
                double newCost = cost_old + neighborhood.get(n);
                if (newCost < cost.get(n)){
                    //更新开销散列表
                    cost.put(n,newCost);
                    //更新父节点散列表
                    parents.put(n,node);
                }
            }
            //将node标记为已处理
            haveProcessed.add(node);
            //寻找下一个待处理的最小开销节点
            node = findLowestCostNode(cost);
        }
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
