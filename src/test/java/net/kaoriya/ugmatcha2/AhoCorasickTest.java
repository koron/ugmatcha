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
            TernaryNode<AhoCorasick.Data<Integer>> node,
            int size,
            AhoCorasick.Data<Integer> expected)
    {
        assertEquals(size, node.size());
        AhoCorasick.Data<Integer> actually = node.getValue();
        assertEquals(expected, actually);
    }

    @Test
    public void treeStructure() {
        AhoCorasick<Integer> m = newInst();
        TernaryNode<AhoCorasick.Data<Integer>> r = m.trie.root();
        AhoCorasick.Data<Integer> invalid = new AhoCorasick.Data<Integer>(r);
        checkNode(r, 3, invalid);
        TernaryNode<AhoCorasick.Data<Integer>> n1 = r.get('a');
        checkNode(n1, 1, invalid);
        TernaryNode<AhoCorasick.Data<Integer>> n3 = r.get('b');
        checkNode(n3, 2, invalid);
        TernaryNode<AhoCorasick.Data<Integer>> n7 = r.get('d');
        checkNode(n7, 0, invalid);
        TernaryNode<AhoCorasick.Data<Integer>> n2 = n1.get('b');
        checkNode(n2, 1, new AhoCorasick.Data<Integer>("ab", 2, n3));
        TernaryNode<AhoCorasick.Data<Integer>> n4 = n3.get('c');
        checkNode(n4, 0, new AhoCorasick.Data<Integer>("bc", 4, r));
        TernaryNode<AhoCorasick.Data<Integer>> n5 = n3.get('a');
        checkNode(n5, 1, new AhoCorasick.Data<Integer>(n1));
        TernaryNode<AhoCorasick.Data<Integer>> n8 = n2.get('c');
        checkNode(n8, 1, new AhoCorasick.Data<Integer>(n4));
        TernaryNode<AhoCorasick.Data<Integer>> n6 = n5.get('b');
        checkNode(n6, 0, new AhoCorasick.Data<Integer>("bab", 6, n2));
        TernaryNode<AhoCorasick.Data<Integer>> n9 = n8.get('d');
        checkNode(n9, 1, new AhoCorasick.Data<Integer>(n7));
        TernaryNode<AhoCorasick.Data<Integer>> n10 = n9.get('e');
        checkNode(n10, 0, new AhoCorasick.Data<Integer>("abcde", 10, r));
    }
}
