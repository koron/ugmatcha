package net.kaoriya.qb.words_finder;

public class Word {

    public final String text;

    public final int index;

    public final int rank;

    Word(String text, int index) {
        this.text = text;
        this.index = index;
        this.rank = RankUtils.rank(this.text);
    }
}
