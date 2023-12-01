package ch.platoon.aoc23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day1 {
  public static void main(String[] args) throws IOException {
    List<String> numbers = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    List<String> allLines = Files.readAllLines(Path.of("input/1.txt"));
    long summ = 0;
    for (String line : allLines) {
      Integer first = null;
      Integer last = null;
      for (int i = 0; i < line.length(); i++) {
        for (int j = 0; j < numbers.size(); j++) {
          String num = numbers.get(j);
          if (line.length() - i >= num.length()) {
            if (line.startsWith(num, i)) {
              if (first == null) {
                first = j + 1;
              }
              last = j + 1;
            }
          }
        }


        if (line.charAt(i) >= '0' && line.charAt(i) <= '9') {
          if (first == null) {
            first = Integer.parseInt(String.valueOf(line.charAt(i)));
          }
          last = Integer.parseInt(String.valueOf(line.charAt(i)));
        }
      }
      System.out.println(first + " " + last);
      summ += first * 10 + last;
    }
    System.out.println(summ);
  }
}