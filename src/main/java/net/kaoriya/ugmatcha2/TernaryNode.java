package net.kaoriya.ugmatcha2;

import java.util.ArrayList;
import java.util.List;

public class TernaryNode<T> {
    public final char label;
    private TernaryNode<T> firstChild;
    private TernaryNode<T> low;
    private TernaryNode<T> high;
    private T value;

    public interface Processor<S> {
        boolean process(TernaryNode<S> node);
    }

    public static class Counter<S> implements Processor<S> {
        public int count = 0;
        public boolean process(TernaryNode<S> node) {
            ++this.count;
            return true;
        }
    }

    public TernaryNode(char label) {
        this.label = label;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public TernaryNode<T> get(char k) {
        TernaryNode<T> curr = this.firstChild;
        while (curr != null) {
            if (k == curr.label) {
                return curr;
            } else if (k < curr.label) {
                curr = curr.low;
            } else {
                curr = curr.high;
            }
        }
        return null;
    }

    public TernaryNode<T> dig(char k) {
        TernaryNode<T> curr = this.firstChild;
        if (curr == null) {
            this.firstChild = new TernaryNode<T>(k);
            return this.firstChild;
        }

        while (true) {
            if (k == curr.label) {
                return curr;
            } else if (k < curr.label) {
                if (curr.low == null) {
                    curr.low = new TernaryNode<T>(k);
                    return curr.low;
                }
                curr = curr.low;
            } else {
                if (curr.high == null) {
                    curr.high = new TernaryNode<T>(k);
                    return curr.high;
                }
                curr = curr.high;
            }
        }
    }

    public int size() {
        if (this.firstChild == null) {
            return 0;
        }
        Counter<T> counter = new Counter<T>();
        each(counter);
        return counter.count;
    }

    public void removeAll() {
        this.firstChild = null;
    }

    public void each(Processor<T> processor) {
        eachNode(processor, this.firstChild);
    }

    private boolean eachNode(Processor<T> processor, TernaryNode<T> node) {
        if (node != null) {
            if (!eachNode(processor, node.low) || !processor.process(node)
                    || !eachNode(processor, node.high)) {
                return false;
            }
        }
        return true;
    }

    private List<TernaryNode<T>> children() {
        final ArrayList<TernaryNode<T>> array
            = new ArrayList<TernaryNode<T>>();
        each(new Processor<T>() {
            public boolean process(TernaryNode<T> node) {
                array.add(node);
                return true;
            }
        });
        return array;
    }

    public void balance() {
        if (this.firstChild == null) {
            return;
        }
        List<TernaryNode<T>> children = children();
        for (TernaryNode<T> child : children) {
            child.low = null;
            child.high = null;
        }
        this.firstChild = balance(children, 0, children.size());
        return;
    }

    private TernaryNode<T> balance(List<TernaryNode<T>> nodes, int start, int end) {
        int count = end - start;
        if (count <= 0) {
            return null;
        } else if (count == 1) {
            return nodes.get(start);
        } else if (count == 2) {
            TernaryNode<T> s = nodes.get(start);
            s.high = nodes.get(start + 1);
            return s;
        } else {
            int mid = (start + end) / 2;
            TernaryNode<T> n = nodes.get(mid);
            n.low = balance(nodes, start, mid);
            n.high = balance(nodes, mid + 1, end);
            return n;
        }
    }
}
