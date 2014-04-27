package net.kaoriya.ugmatcha;

public class Match {

    public final String text;

    public final int index;

    public Match(String text, int index) {
        this.text = text;
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        // Check meta equality.
        if (o == this) {
            return true;
        } else if (o == null) {
            return false;
        } else if (o.getClass() != getClass()) {
            return false;
        }
        // Check properties equality.
        Match p = (Match)o;
        if (this.text == null ? p.text != null : !this.text.equals(p.text)) {
            return false;
        } else if (this.index != p.index) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Match{")
            .append(" text=\"").append(this.text).append("\"")
            .append(" index=").append(this.index)
            .append("}");
        return s.toString();
    }
}
