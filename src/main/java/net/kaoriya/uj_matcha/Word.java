package net.kaoriya.uj_matcha;

class Word {

    final String text;

    final int index;

    final int rank;

    Word(String text, int index) {
        this.text = text;
        this.index = index;
        this.rank = RankUtils.rank(this.text);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Word{")
            .append("text=").append(this.text)
            .append(" index=").append(this.index)
            .append(" rank=").append(this.rank)
            .append("}");
        return s.toString();
    }
}
