package net.kaoriya.ugmatcha2;

import java.util.ArrayList;
import java.util.List;

public class AhoCorasick<T> {


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
