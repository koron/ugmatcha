package net.kaoriya.qb.words_finder;

public class Match {

    public final String text;

    public final int index;

    public Match(String text, int index) {
        this.text = text;
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        Match p = (Match)o;
        if (p == null) {
            return false;
        } else if (p == this) {
            return true;
        } else {
            return this.text.equals(p.text) && this.index == p.index;
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Match {")
            .append(" text=\"").append(this.text).append("\"")
            .append(" index=").append(this.index)
            .append(" }");
        return s.toString();
    }
}
