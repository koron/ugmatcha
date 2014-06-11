package net.kaoriya.ugmatcha2;

import java.util.ArrayList;
import java.util.List;

public class AhoCorasick<T> {

    public static class Match<S> {
        public final int index;
        public final String pattern;
        public final S value;

        public Match(int index, String pattern, S value) {
            this.index = index;
            this.pattern = pattern;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            Match<S> t = (Match<S>)o;
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
                .append(" pattern=").append(this.pattern)
                .append(" value=").append(this.value)
                .append("}");
            return s.toString();
        }
    }

    static class Data<S> {
        String pattern;
        S value;
        TernaryNode<Data<S>> failure;

        Data(String pattern, S value, TernaryNode<Data<S>> failure) {
            this.pattern = pattern;
            this.value = value;
            this.failure = failure;
        }

        Data(String pattern, S value) {
            this(pattern, value, null);
        }

        Data(TernaryNode<Data<S>> failure) {
            this(null, null, failure);
        }

        Data() {
            this(null, null, null);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            Data<S> t = (Data<S>)o;
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

    final TernaryTrie<Data<T>> trie = new TernaryTrie<>();

    public AhoCorasick() {
        // nothing to do.
    }

    public void add(String pattern, T value) {
        this.trie.put(pattern, new Data<T>(pattern, value));
    }

    public void compile() {
        this.trie.balance();
        final TernaryNode<Data<T>> root = this.trie.root();
        root.setValue(new Data<T>(root));
        this.trie.eachWidth(new TernaryNode.Processor<Data<T>>() {
            public boolean process(final TernaryNode<Data<T>> parent) {
                parent.each(new TernaryNode.Processor<Data<T>>() {
                    public boolean process(TernaryNode<Data<T>> node) {
                        fillFailure(node, parent, root);
                        return true;
                    }
                });
                return true;
            }
        });
    }

    private void fillFailure(
            TernaryNode<Data<T>> node,
            TernaryNode<Data<T>> parent,
            TernaryNode<Data<T>> root)
    {
        Data<T> data = node.getValue();
        if (data == null) {
            data = new Data<T>();
            node.setValue(data);
        }
        if (parent == root) {
            data.failure = root;
            return;
        }
        data.failure = getNextNode(getFailureNode(parent, root), root,
                node.label);
    }

    private TernaryNode<Data<T>> getNextNode(
            TernaryNode<Data<T>> node,
            TernaryNode<Data<T>> root,
            char ch)
    {
        while (true) {
            TernaryNode<Data<T>> next = node.get(ch);
            if (next != null) {
                return next;
            } else if (node == root) {
                return root;
            }
            node = getFailureNode(node, root);
        }
    }

    private TernaryNode<Data<T>> getFailureNode(
            TernaryNode<Data<T>> node,
            TernaryNode<Data<T>> root)
    {
        TernaryNode<Data<T>> next = node.getValue().failure;
        return next != null ? next : root;
    }

    public List<Match<T>> matchAll(String text) {
        final ArrayList<Match<T>> list = new ArrayList<>();
        match(text, new MatchHandler<T>() {
            public boolean matched(int index, String pattern, T value) {
                list.add(new Match<T>(index, pattern, value));
                return true;
            }
        }, 0);
        return list;
    }

    public boolean match(String text, MatchHandler<T> handler, int max) {
        if (max <= 0) {
            max = Integer.MAX_VALUE;
        }
        int count = 0;
        TernaryNode<Data<T>> root = this.trie.root();
        TernaryNode<Data<T>> curr = root;
        TernaryNode<Data<T>> target = null;
        for (int i = 0, L = text.length(); i < L; ++i) {
            char ch = text.charAt(i);
            curr = getNextNode(curr, root, ch);
            for (target = curr; target != root; ) {
                Data<T> data = target.getValue();
                if (data.pattern != null) {
                    ++count;
                    if (handler.matched(
                                i - data.pattern.length() + 1,
                                data.pattern,
                                data.value) == false
                            || count >= max) {
                        break;
                    }
                }
                target = data.failure;
            }
        }
        return count > 0;
    }
}
