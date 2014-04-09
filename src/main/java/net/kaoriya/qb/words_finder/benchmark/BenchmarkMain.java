package net.kaoriya.qb.words_finder.benchmark;

import java.util.List;
import java.io.File;

public class BenchmarkMain {

    public static final String WORDS_PATH = "data/words2.txt";

    public static final String TARGETS_PATH = "data/targets2.txt";

    public static final long FINDCOUNT_FINDER = 9000000;
    public static final long FINDCOUNT_REGEXP = 200000;

    public static void benchmark(
            Engine engine,
            long findCount)
        throws Exception
    {
        List<String> words = Utils.readLines(new File(WORDS_PATH));
        List<String> targets = Utils.readLines(new File(TARGETS_PATH));
        benchmark(engine, words, targets, findCount);
    }

    public static void benchmark(
            Engine engine,
            List<String> words,
            List<String> targets,
            long findCount)
        throws Exception
    {
        for (String word : words) {
            engine.add(word);
        }
        engine.prepare();
        System.gc();
        System.gc();

        boolean loop = true;
        long count = 0;
        long start = System.nanoTime();
        while (loop) {
            for (String target : targets) {
                engine.findOne(target);
                ++count;
                if (count >= findCount) {
                    loop = false;
                    break;
                }
            }
        }
        long elapsed = System.nanoTime() - start;

        double seconds = elapsed / 1e9;
        System.out.format(
                "%1$1s: %2$14.3f queries/sec (%3$d queries in %4$6.3f secs)",
                engine.getName(), count / seconds, count, seconds);
        System.out.println("");
    }

    public static void main(String[] args) throws Exception {
        benchmark(new FinderEngine(), FINDCOUNT_FINDER);
        benchmark(new RegexpEngine(), FINDCOUNT_REGEXP);
    }
}
