package breadthFirshSearch;

import java.util.*;

/**
 * 广度优先搜索
 */
public class BreadthFirstSearch {
    private static Map<String, List<String>> graph = new HashMap<>();

    public static void main(String[] args) {
        //构建图
        graph.put("you", Arrays.asList("alice", "bob", "claire"));
        graph.put("bob", Arrays.asList("anuj", "peggy"));
        graph.put("alice", Arrays.asList("peggy"));
        graph.put("claire", Arrays.asList("thom", "jonny"));
        graph.put("anuj", Collections.emptyList());
        graph.put("peggy", Collections.emptyList());
        graph.put("thom", Collections.emptyList());
        graph.put("jonny", Collections.emptyList());
        search("you");
    }

    //有个问题就是没有加入you节点进行访问
    private static boolean search(String name) {
        Queue<String> searchQueue = new ArrayDeque<>(graph.get(name));
        // 标记已经访问过的节点
        List<String> searched = new ArrayList<>();

        while (!searchQueue.isEmpty()) {
            String person = searchQueue.poll();
            // 只访问没有被访问过的节点
            if (!searched.contains(person)) {
                if (person_is_seller(person)) {
                    System.out.println(person + " is a mango seller!");
                    return true;
                } else {
                    searchQueue.addAll(graph.get(person));
                    // 标记该节点已被访问
                    searched.add(person);
                }
            }
        }
        return false;
    }
    // 判断是否是经销商
    private static boolean person_is_seller(String name) {
        return name.endsWith("m");
    }
}
