package ch.platoon.aoc23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day5 {

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Path.of("input/5.txt"));

    List<List<Long>> currentMaps = new ArrayList<>();
    Map<Long, Long> seedsMap = new HashMap<>();
    for (Iterator<String> iterator = lines.iterator(); iterator.hasNext(); ) {
      String line = iterator.next();
      if (line.startsWith("seeds:")) {
        List<Long> seedRanges = Arrays.stream(line.substring(line.indexOf(':') + 1).split(" ")).filter(s -> !s.isBlank()).map(s -> Long.parseLong(s.trim())).toList();
        for (int i = 0; i < seedRanges.size() - 1; i += 2) {
          for (long j = seedRanges.get(i); j < seedRanges.get(i) + seedRanges.get(i + 1); j++) {
            seedsMap.put(j, j);
          }
        }

      } else if (!line.isBlank()) {
        if (!Character.isDigit(line.charAt(0))) {
          System.out.println(line);
          if (!currentMaps.isEmpty()) {
            map(currentMaps, seedsMap);
          }

          seedsMap.forEach((integer, integer2) -> System.out.println(integer + "->" + integer2));
          currentMaps.clear();
        } else {
          currentMaps.add(Arrays.stream(line.split(" ")).filter(s -> !s.isBlank()).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList()));
        }
      }
    }


    map(currentMaps, seedsMap);
    seedsMap.forEach((integer, integer2) -> System.out.println(integer + "->" + integer2));

    long min = Long.MAX_VALUE;
    for (Long value : seedsMap.values()) {
      if(value < min) {
        min = value;
      }
    }
    System.out.println(min);

  }

  private static void map(List<List<Long>> currentMaps, Map<Long, Long> seedsMap) {
    for (Map.Entry<Long, Long> seedTargetEntry : seedsMap.entrySet()) {
      finMapping(currentMaps, seedTargetEntry);
    }
  }

  private static void finMapping(List<List<Long>> currentMaps, Map.Entry<Long, Long> seedTargetEntry) {
    for (List<Long> currentMap : currentMaps) {
      System.out.print(seedTargetEntry.getValue() + " > " + currentMap.get(1) + " && " + seedTargetEntry.getValue() + " <= " + currentMap.get(1) + " + " + currentMap.get(2)  );
      if(seedTargetEntry.getValue() >= currentMap.get(1) && seedTargetEntry.getValue() <= currentMap.get(1) +currentMap.get(2)) {
        long target = currentMap.get(0) + seedTargetEntry.getValue() - currentMap.get(1);
        seedTargetEntry.setValue(target);
        System.out.print("   ok");
        System.out.println();
        return;
      }
    }
  }

}
