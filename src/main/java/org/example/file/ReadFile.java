package org.example.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadFile {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("C:/Users/HSH/Desktop/temp");
        boolean directory = Files.isDirectory(path);
        System.out.println(directory);

        // read all files in the directory
        Files.list(path).forEach(System.out::println);


        Path target = Path.of(path.toString() + "/ApiTime.txt");
        List<String> strings = Files.readAllLines(target);

        List<List<String>> list = strings.stream().map(s -> s.split("\\s"))
                .map(strings1 -> {
                    return Arrays.stream(strings1)
                            .filter(s -> !s.isEmpty())
                            .toList();
                })
                .toList();

        System.out.println(list);
        AtomicInteger result = new AtomicInteger();
        list.forEach(l -> {
            result.addAndGet(Integer.parseInt(l.get(1)));
        });

        System.out.println(result);
        int cnt = result.get() / 500;
        System.out.println("needs: " + cnt*100);

    }
}
