package ch.platoon.aoc23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day2 {


  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Path.of("input/2.txt"));
    int summ = 0;
    int powerSumm = 0;
    for (String line : lines) {
      String[] split = line.split(":");
      int game = Integer.parseInt(split[0].substring("Game ".length()));
      System.out.println(game);
      String[] sets = split[1].split(";");
      boolean ok = true;
      Integer minRed = null;
      Integer minGreen = null;
      Integer minBlue = null;
      for (String set : sets) {
        String[] cubes = set.split(",");
        for (String cube : cubes) {
          String[] numberColor = cube.trim().split(" ");
          int number = Integer.parseInt(numberColor[0].trim());
          switch (numberColor[1].trim()) {
            case "red":
              if (number > 12) {
                ok = false;
              }
              minRed = minRed == null ? number : Math.max(number, minRed);
              break;
            case "green":
              if (number > 13) {
                ok = false;
              }
              minGreen = minGreen == null ? number : Math.max(number, minGreen);
              break;
            case "blue":
              if (number > 14) {
                ok = false;
              }
              minBlue = minBlue == null ? number : Math.max(number, minBlue);
              break;
          }
        }
      }
      int power = (minRed == null ? 1 : minRed) * (minGreen == null ? 1 : minGreen) * (minBlue == null ? 1 : minBlue);
      powerSumm += power;
      System.out.println(game + ": " + powerSumm);
    }
    System.out.println(summ);
    System.out.println(powerSumm);
  }
}
