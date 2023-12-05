package ch.platoon.aoc23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 {

  private static final Pattern NUMBER = Pattern.compile("\\d+");

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Path.of("input/3.txt"));

    List<Integer> parts = new ArrayList<>();
    Map<String,List<Integer>> gears = new TreeMap<>();
    for (int i = 0; i < lines.size(); i++) {

      String line = lines.get(i);

      Matcher matcher = NUMBER.matcher(line);

      while (matcher.find()) {
        boolean match = false;
        String matched =  addNumbers(line, matcher.start(), matcher.end());
        if (matched != null) {
          gears.computeIfAbsent(i+ "," + matched, s -> new ArrayList<>()).add(Integer.parseInt(matcher.group()));
          match = true;
        }
        if (i > 0) {
          matched =  addNumbers(lines.get(i-1), matcher.start(), matcher.end());
          if (matched != null) {
            gears.computeIfAbsent((i-1) + "," + matched, s -> new ArrayList<>()).add(Integer.parseInt(matcher.group()));
            match = true;
          }
        }
        if (i < lines.size() - 1) {
          matched =  addNumbers(lines.get(i+1), matcher.start(), matcher.end());
          if (matched != null) {
            gears.computeIfAbsent((i+1) + "," + matched, s -> new ArrayList<>()).add(Integer.parseInt(matcher.group()));
            match = true;
          }
        }
        if (match) {
          parts.add(Integer.parseInt(matcher.group()));
        }
      }
    }
    int summ = 0;
    for (Integer integer : parts) {
      summ += integer;
    }
    System.out.println(summ);

    long summ2 = 0;

    for (Map.Entry<String, List<Integer>> gear : gears.entrySet()) {
      if (gear.getKey().contains("*") && gear.getValue().size() ==2) {
        summ2 += (long) gear.getValue().get(0) * gear.getValue().get(1);
      }
    }

    System.out.println(summ2);

//    gears.entrySet().stream().filter(e -> e.getKey().contains("*")).forEach(e -> {
//      System.out.println(e.getKey() + ": " + e.getValue().stream().map(i -> i +"").collect(Collectors.joining(", ")));
//    });
  }

  private static String addNumbers(String line, int start, int end) {
    for (int i = Math.max(start - 1, 0); i <= Math.min(end, line.length() - 1); i++) {
      if (line.charAt(i) != '.' && (line.charAt(i) < '0' || line.charAt(i) > '9')) {
        return i + "," + line.charAt(i);
      }
    }
    return null;
  }
}
