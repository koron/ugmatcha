package net.kaoriya.qb.words_finder;

class Word {

    final String text;

    final int index;

    final int rank;

    Word(String text, int index) {
        this.text = text;
        this.index = index;
        this.rank = RankUtils.rank(this.text);
    }
}
