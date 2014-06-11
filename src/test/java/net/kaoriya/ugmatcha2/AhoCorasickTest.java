package net.kaoriya.ugmatcha2;

import org.junit.Test;
import static org.junit.Assert.*;

public class AhoCorasickTest {

    private AhoCorasick<Integer> newInst() {
        AhoCorasick<Integer> m = new AhoCorasick<>();
        m.add("ab", 2);
        m.add("bc", 4);
        m.add("bab", 6);
        m.add("d", 7);
        m.add("abcde", 10);
        m.compile();
        return m;
    }

    private void checkNode(
            TernaryNode<Data<Integer>> node,
            int size,
            Data<Integer> expected)
    {
        assertEquals(size, node.size());
        Data<Integer> actually = node.getValue();
        assertEquals(expected, actually);
    }

    @Test
    public void treeStructure() {
        AhoCorasick<Integer> m = newInst();
        TernaryNode<Data<Integer>> r = m.trie.root();
        Data<Integer> invalid = new Data<Integer>(r);
        checkNode(r, 3, invalid);
        TernaryNode<Data<Integer>> n1 = r.get('a');
        checkNode(n1, 1, invalid);
        TernaryNode<Data<Integer>> n3 = r.get('b');
        checkNode(n3, 2, invalid);
        TernaryNode<Data<Integer>> n7 = r.get('d');
        checkNode(n7, 0, invalid);
        TernaryNode<Data<Integer>> n2 = n1.get('b');
        checkNode(n2, 1, new Data<Integer>("ab", 2, n3));
        TernaryNode<Data<Integer>> n4 = n3.get('c');
        checkNode(n4, 0, new Data<Integer>("bc", 4, r));
        TernaryNode<Data<Integer>> n5 = n3.get('a');
        checkNode(n5, 1, new Data<Integer>(n1));
        TernaryNode<Data<Integer>> n8 = n2.get('c');
        checkNode(n8, 1, new Data<Integer>(n4));
        TernaryNode<Data<Integer>> n6 = n5.get('b');
        checkNode(n6, 0, new Data<Integer>("bab", 6, n2));
        TernaryNode<Data<Integer>> n9 = n8.get('d');
        checkNode(n9, 1, new Data<Integer>(n7));
        TernaryNode<Data<Integer>> n10 = n9.get('e');
        checkNode(n10, 0, new Data<Integer>("abcde", 10, r));
    }

    @Test
    public void matchResults() {
        AhoCorasick<Integer> m = newInst();

        Match<Integer>[] results = m.matchAll("abcde").toArray(new Match[0]);
        assertArrayEquals(new Match[] {
            new Match<Integer>(0, "ab", 2),
            new Match<Integer>(1, "bc", 4),
            new Match<Integer>(3, "d", 7),
            new Match<Integer>(0, "abcde", 10),
        }, results);
    }
}
