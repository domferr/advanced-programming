package part1;

import part1.aux_files.AJob;
import part1.aux_files.Pair;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class CiaoJob extends AJob<String, String> {
    private final String filePath;

    public CiaoJob(String filePath) {
        this.filePath = filePath;
    }

    private String ciao(String word) {
        char[] charArray = word.toLowerCase().toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    @Override
    public Stream<Pair<String, String>> execute() {
        try {
            Scanner scanner = new Scanner(new File(this.filePath));
            // Regular expression to set the values we do NOT want.
            // It simply negates the category of characters we want (only alphabetic characters)
            // + means 1 or more of the negated category
            scanner.useDelimiter("\\P{Alpha}+");
            List<Pair<String, String>> workingList = new LinkedList<>(); // ensure O(1) add operation
            while (scanner.hasNext()) {
                // retrieve the next word (alphabetic characters only)
                String word = scanner.next();
                // ignore all words of less than four characters
                if (word.length() < 4) continue;

                String ciaoOfWord = ciao(word);
                workingList.add(new Pair<>(ciaoOfWord, word));
            }
            return workingList.stream();
        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
            System.err.println("File " + this.filePath + "ignored");
            return Stream.empty();
        }
    }
}
