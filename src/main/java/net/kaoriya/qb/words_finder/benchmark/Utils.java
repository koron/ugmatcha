package net.kaoriya.qb.words_finder.benchmark;

import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Utils {

    public static List<String> readLines(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader r = new BufferedReader(new FileReader(file));
        String line;
        while ((line = r.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

}
