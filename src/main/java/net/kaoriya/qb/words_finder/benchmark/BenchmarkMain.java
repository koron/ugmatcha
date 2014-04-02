package net.kaoriya.qb.words_finder.benchmark;

import java.util.List;
import java.io.File;
public class BenchmarkMain {

    public static final String WORDS_PATH = "data/words.txt";

    public static final String TARGETS_PATH = "data/targets.txt";

    public static final long FIND_COUNT = 100000000;

    public static void benchmark(Engine engine) throws Exception {
        List<String> words = Utils.readLines(new File(WORDS_PATH));
        List<String> targets = Utils.readLines(new File(TARGETS_PATH));
        for (String word : words) {
            engine.add(word);
        }
        System.gc();
        System.gc();

        boolean loop = true;
        long count = 0;
        long start = System.nanoTime();
        while (loop) {
            for (String target : targets) {
                engine.findOne(target);
                ++count;
                if (count >= FIND_COUNT) {
                    loop = false;
                    break;
                }
            }
        }
        long elapsed = System.nanoTime() - start;

        double seconds = elapsed / 1e9;
        System.out.format(
                "%1$1s: %2$.3f queries/sec (%3$d queries in %4$.3f secs)",
                engine.getName(), count / seconds, count, seconds);
        System.out.println("");
    }

    public static void main(String[] args) throws Exception {
        benchmark(new FinderEngine());
        benchmark(new RegexpEngine());
    }
}
