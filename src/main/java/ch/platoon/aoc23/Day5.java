package ch.platoon.aoc23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day5 {

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Path.of("input/5.txt"));

    Map<String, List<Mapping>> mappingsMap = new TreeMap<>(Comparator.reverseOrder());
    List<Mapping> currentMapping = new ArrayList<>();
    String mappingName = null;

    for (String line : lines) {
      if (line.startsWith("seeds:")) {
        mappingName = "seeds";
        String[] split = line.substring("seeds: ".length()).split("\\s+");
        for (int i = 0; i < split.length - 1; i += 2) {
          currentMapping.add(new Mapping(split[i], split[i], split[i + 1]));
        }
      } else if (line.isBlank()) {
        currentMapping.sort(Comparator.comparing(Mapping::destinationFrom));
        mappingsMap.put(mappingsMap.size() + ": " + mappingName, currentMapping);
        currentMapping = new ArrayList<>();
      } else if (!Character.isAlphabetic(line.codePointAt(0))) {
        String[] split = line.split("\\s+");
        currentMapping.add(new Mapping(split[0], split[1], split[2]));
      } else {
        mappingName = line;
      }
    }
    currentMapping.sort(Comparator.comparing(Mapping::destinationFrom));
    mappingsMap.put(mappingsMap.size() + ": " + mappingName, currentMapping);

    for (Map.Entry<String, List<Mapping>> mappingEntry : mappingsMap.entrySet()) {
      System.out.println(mappingEntry.getKey());
      for (Mapping mapping : mappingEntry.getValue()) {
        System.out.println(mapping);
      }
    }

    List<Map.Entry<String, List<Mapping>>> mappingList = new ArrayList<>(mappingsMap.entrySet());

    Map.Entry<String, List<Mapping>> startMappings = mappingList.get(0);

    for (Mapping startMapping : startMappings.getValue()) {
      for (long l = startMapping.destinationFrom; l < startMapping.destinationTo; l++) {
        Long source = findSource(mappingList, 1, l);
        if (source != null) {
          System.out.println(l + " -> " + source);
        }
      }
    }

  }

  private static Long findSource(List<Map.Entry<String, List<Mapping>>> mappingsList, int mappingListIndex, long destination) {
    if (mappingListIndex == 7) {
      System.out.println("destination = " + destination);
      return destination;
    }
    List<Mapping> mappings = mappingsList.get(mappingListIndex).getValue();
    for (Mapping mapping : mappings) {
      if (mapping.isInRange(destination)) {
        return findSource(mappingsList, mappingListIndex + 1, mapping.sourceFrom + (destination - mapping.destinationFrom));
      }

    }
    if (mappingListIndex < 7) {
      return findSource(mappingsList, mappingListIndex + 1, destination);
    }
    return null;
  }

  record Mapping(long destinationFrom, long destinationTo, long sourceFrom, long sourceTo) {
    public Mapping(String dest, String source, String length) {
      this(Long.parseLong(dest), Long.parseLong(dest) + Long.parseLong(length), Long.parseLong(source), Long.parseLong(source) + Long.parseLong(length));
    }

    boolean isInRange(long destination) {
      return destination >= destinationFrom && destination < destinationTo;
    }
  }

  ;

}
