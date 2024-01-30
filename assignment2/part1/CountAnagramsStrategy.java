package part1;

import part1.aux_files.AJob;
import part1.aux_files.Pair;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class CountAnagramsStrategy implements JobSchedulerStrategy<String, String> {
    private final String out_file = "count_anagrams.txt";

    @Override
    public Stream<AJob<String, String>> emit() {
        Scanner in = new Scanner(System.in);

        System.out.println("Absolute path (directory):");
        String dir_name = in.nextLine();
        Path dir = Paths.get(dir_name);
        if (!Files.exists(dir) || !Files.isDirectory(dir) || !dir.isAbsolute()) {
            System.err.println("The directory '"+dir_name+ "' is not valid. Check if it exist, that it is a directory and provide an absolute path");
            return Stream.empty();
        }
        try {
            return Files
                    .list(dir)
                    .filter(dirPath -> dirPath.toString().endsWith(".txt"))
                    .map(filePath -> new CiaoJob(filePath.toString()));
        } catch (Exception e) {
            System.err.println("IO error opening the directory '"+dir_name+ "'");
            System.err.println(e.getMessage());
            return Stream.empty();
        }
    }

    @Override
    public void output(Stream<Pair<String, List<String>>> stream) {
        try (PrintWriter pw = new PrintWriter(this.out_file, StandardCharsets.UTF_8)) {
            stream.forEach((kListPair ->
                pw.println(kListPair.getKey() + ", " + kListPair.getValue().size())
            ));
            System.out.println("Output wrote to " + out_file);
        } catch (IOException e) {
            System.err.println("IO error occurred");
            System.err.println(e.getMessage());
        }
    }
}
