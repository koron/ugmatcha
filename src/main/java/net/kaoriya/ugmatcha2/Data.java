package net.kaoriya.ugmatcha2;

class Data<T> {

    String pattern;
    T value;
    TernaryNode<Data<T>> failure;

    Data(String pattern, T value, TernaryNode<Data<T>> failure) {
        this.pattern = pattern;
        this.value = value;
        this.failure = failure;
    }

    Data(String pattern, T value) {
        this(pattern, value, null);
    }

    Data(TernaryNode<Data<T>> failure) {
        this(null, null, failure);
    }

    Data() {
        this(null, null, null);
    }

    // to comparison in equals method.
    @SuppressWarnings("unchecked")
    private Data<T> toData(Object o) {
        return (Data<T>)o;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        Data<T> t = toData(o);
        if (t == null) {
            return false;
        }
        // compare member fields.
        if (this.pattern != t.pattern && this.pattern != null &&
                !this.pattern.equals(t.pattern)) {
            return false;
                }
        if (this.value != t.value && this.value != null &&
                !this.value.equals(t.value)) {
            return false;
                }
        if (this.failure != t.failure) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Data<>{")
            .append("pattern=").append(this.pattern)
            .append(" value=").append(this.value)
            .append(" failure=").append(this.failure)
            .append("}");
        return s.toString();
    }
}
