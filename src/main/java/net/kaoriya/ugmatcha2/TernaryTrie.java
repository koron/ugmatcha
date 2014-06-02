package net.kaoriya.ugmatcha2;

import java.util.LinkedList;

/**
 * Ternary Trie implementation.
 */
public class TernaryTrie<T> {

    private final TernaryNode<T> root = new TernaryNode<>('\u0000');

    public TernaryNode<T> root() {
        return this.root;
    }

    public TernaryNode<T> get(String key) {
        TernaryNode<T> node = this.root;
        for (char ch : key.toCharArray()) {
            node = node.get(ch);
            if (node == null) {
                break;
            }
        }
        return node;
    }

    public TernaryNode<T> put(String key, T value) {
        TernaryNode<T> node = this.root;
        for (char ch : key.toCharArray()) {
            node = node.dig(ch);
        }
        node.setValue(value);
        return node;
    }

    public int size() {
        TernaryNode.Counter<T> counter = new TernaryNode.Counter<>();
        eachDepth(counter);
        return counter.count;
    }

    public void balance() {
        eachDepth(new TernaryNode.Processor<T>() {
            public boolean process(TernaryNode<T> node) {
                node.balance();
                return true;
            }
        });
    }

    public void eachDepth(final TernaryNode.Processor<T> proc) {
        this.root.each(new TernaryNode.Processor<T>() {
            public boolean process(TernaryNode<T> node) {
                node.each(this);
                return proc.process(node);
            }
        });
    }

    public void eachWidth(TernaryNode.Processor<T> proc) {
        final LinkedList<TernaryNode<T>> queue = new LinkedList<>();
        queue.add(this.root);
        TernaryNode.Processor<T> myproc = new TernaryNode.Processor<T>() {
            public boolean process(TernaryNode<T> node) {
                queue.add(node);
                return true;
            }
        };
        while (queue.size() != 0) {
            TernaryNode<T> node = queue.poll();
            if (!proc.process(node)) {
                break;
            }
            node.each(myproc);
        }
    }
}
