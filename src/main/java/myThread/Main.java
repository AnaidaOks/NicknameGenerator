package myThread;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    public static int lengthThree = 0;
    public static int lengthFour = 0;
    public static int lengthFive = 0;

    public static void main(String[] args) {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        AtomicInteger length3 = new AtomicInteger(0);
        AtomicInteger length4 = new AtomicInteger(0);
        AtomicInteger length5 = new AtomicInteger(0);

        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].length() == 3 && texts[i].charAt(0) == texts[i].charAt(1)
                        && texts[i].charAt(1) == texts[i].charAt(2)) {
                    lengthThree = length3.incrementAndGet();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].length() == 4 && texts[i].charAt(0) == texts[i].charAt(3) && texts[i].charAt(1) == texts[i].charAt(2)) {
                    lengthFour = length4.incrementAndGet();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].length() == 5) {
                    String[] chars = texts[i].split("");
                    Arrays.sort(chars);
                    if (texts[i].equals(String.join("", chars))) {
                        lengthFive = length5.incrementAndGet();
                    }
                }
            }
        }).start();

        System.out.printf("Красивых слов с длиной 3: %d шт\n", lengthThree);
        System.out.printf("Красивых слов с длиной 4: %d шт\n", lengthFour);
        System.out.printf("Красивых слов с длиной 5: %d шт\n", lengthFive);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}