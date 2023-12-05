package ch.platoon.aoc23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day4 {

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Path.of("input/4.txt"));
    int sum = 0;
    int[] copies = new int[lines.size()];
    for (int i = 0; i < lines.size(); i++) {
      copies[i] = 1;
    }

    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      String[] split1 = line.split(":");
      String[] split2 = split1[1].split("\\|");
      Set<Integer> winning = Arrays.stream(split2[0].split(" ")).filter(s -> !s.isBlank()).map(s -> Integer.parseInt(s.trim())).collect(Collectors.toSet());
      Set<Integer> myNumbers = Arrays.stream(split2[1].split(" ")).filter(s -> !s.isBlank()).map(s -> Integer.parseInt(s.trim())).collect(Collectors.toSet());

      long count = myNumbers.stream().filter(winning::contains).count();
      for (int j = 0; j < count; j++) {
        copies[i+j+1]+= copies[i];
      }
    }
    for (int i = 0; i < copies.length; i++) {
      System.out.println(i + ":" + copies[i]);
      sum += copies[i];
    }
    System.out.println(sum);
  }
}
