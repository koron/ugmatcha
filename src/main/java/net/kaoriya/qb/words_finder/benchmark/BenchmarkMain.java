package net.kaoriya.qb.words_finder.benchmark;

public class BenchmarkMain {

    public static void benchmark(Engine engine) {
        // TODO:
    }

    public static void main(String[] args) {
        benchmark(new FinderEngine());
        benchmark(new RegexpEngine());
    }
}
