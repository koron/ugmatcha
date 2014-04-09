package net.kaoriya.qb.words_finder.benchmark;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<String> readLines(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader(file));
            String line;
            while ((line = r.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            if (r != null) {
                r.close();
            }
        }
        return lines;
    }

    public static void writeLines(File file, List<String> lines)
        throws IOException
    {
        PrintWriter w = null;
        try {
            w = new PrintWriter(file, "UTF-8");
            for (String line : lines) {
                w.println(line);
            }
        } finally {
            if (w != null) {
                w.close();
            }
        }
    }
}
