package breadthFirshSearch;

import java.util.*;

public class BFSByMe {
    //图结构
    public static Map<String, List<String>> graph = new HashMap<>();

    public static String search(String name){
        //访问队列,将第一个节点加入队列
        Queue<String> searchQueue = new ArrayDeque<>(Collections.singletonList(name));
        //把节点的所有邻居加入访问队列
        searchQueue.addAll(graph.get(name));
        //标记访问过的节点
        List<String> searchAlready = new ArrayList<>();
        while (!searchQueue.isEmpty()){
            String person = searchQueue.poll(); //出队列
            if (!searchAlready.contains(person)){ //person没有访问过
                if (isSeller(person)){
                    return person;
                }
                searchAlready.add(person); //将person标记为已访问
                searchQueue.addAll(graph.get(person)); //将person的所有邻居加入队列
            }
        }
       return "no one";
    }

    public static boolean isSeller(String name){
        return name.endsWith("m");
    }

    public static void main(String[] args) {
        graph.put("you", Arrays.asList("Bob","Claire","Alice"));
        graph.put("Bob", Arrays.asList("Anuj","peggy"));
        graph.put("Claire", Arrays.asList("Thom","Jonny"));
        graph.put("Alice", Arrays.asList("peggy"));
        graph.put("Anuj", Collections.emptyList());
        graph.put("peggy", Collections.emptyList());
        graph.put("Jonny", Collections.emptyList());
        graph.put("Thom", Collections.emptyList());
        String person = search("you");
        System.out.println(person + " is a mango seller!");
    }
}
