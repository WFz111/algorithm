package greedyAlgorithm;

import java.util.*;

public class SetCovering {
    public static void main(String... args) {
        // 需要被覆盖的州
        Set<String> statesNeeded = new HashSet<>(Arrays.asList("mt", "wa", "or", "id", "nv", "ut", "ca", "az"));
        //存储广播站
        Map<String,Set<String>> stations = new LinkedHashMap<>();

        stations.put("kone", new HashSet<>(Arrays.asList("id", "nv", "ut")));
        stations.put("ktwo", new HashSet<>(Arrays.asList("wa", "id", "mt")));
        stations.put("kthree", new HashSet<>(Arrays.asList("or", "nv", "ca")));
        stations.put("kfour", new HashSet<>(Arrays.asList("nv", "ut")));
        stations.put("kfive", new HashSet<>(Arrays.asList("ca", "az")));
        //最终选择的广播台
        Set<String> finalStations = new HashSet<>();
        while (!statesNeeded.isEmpty()) {
            //存储覆盖了最多的未覆盖州的广播台
            String bestStation = null;
            //存储当前广播台覆盖到的，未被覆盖的州
            Set<String> statesCovered = new HashSet<>();

            for (Map.Entry<String,Set<String>> station : stations.entrySet()) {
                Set<String> covered = new HashSet<>(statesNeeded);
                covered.retainAll(station.getValue());

                if (covered.size() > statesCovered.size()) {
                    bestStation = station.getKey();
                    statesCovered = covered;
                }
            }
            statesNeeded.removeIf(statesCovered::contains);

            if (bestStation != null) {
                finalStations.add(bestStation);
            }
        }
        System.out.println(finalStations); // [ktwo, kone, kthree, kfive]
    }
}
