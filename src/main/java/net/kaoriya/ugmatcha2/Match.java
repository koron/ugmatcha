package net.kaoriya.ugmatcha2;

public class Match<T> {

    public final int index;
    public final String pattern;
    public final T value;

    public Match(int index, String pattern, T value) {
        this.index = index;
        this.pattern = pattern;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        Match<T> t = (Match<T>)o;
        if (t == null) {
            return false;
        }
        // compare member fields.
        if (this.index != t.index) {
            return false;
        }
        if (this.pattern != t.pattern && this.pattern != null &&
                this.pattern.equals(t.pattern)) {
            return false;
        }
        if (this.value != t.value && this.value != null &&
                !this.value.equals(t.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Match<>{")
            .append("index=").append(this.index)
            .append(" pattern=").append(this. pattern)
            .append(" value=").append(this.value)
            .append("}");
        return s.toString();
    }
}
