package net.kaoriya.ugmatcha;

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
        Word p = (Word)o;
        if (this.text == null ? p.text != null : !this.text.equals(p.text)) {
            return false;
        } else if (this.index != p.index) {
            return false;
        }
        // No need to check ranks, because text is matched already.
        return true;
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
