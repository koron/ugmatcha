package net.kaoriya.qb.words_finder.benchmark;

public interface Engine {

    void add(String word);

    void prepare();

    String findOne(String text);

    String getName();
}
