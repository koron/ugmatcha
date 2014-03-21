package net.kaoriya.qb.words_finder;

import org.junit.Test;
import static org.junit.Assert.*;

public class MatchTest {

    @Test
    public void stringify() {
        assertEquals("Match { text=\"null\" index=0 }",
                new Match(null, 0).toString());
    }

    @Test
    public void equality() {
        Match m1 = new Match(null, 0);
        Match m1b = new Match(null, 0);
        Match m1c = new Match(null, 1);
        Match m1d = new Match("qux", 1);

        assertTrue("match with it self", m1.equals(m1));
        assertFalse("unmatch with null", m1.equals(null));
        assertFalse("unmatch with other class", m1.equals(new Object()));
        assertFalse("unmatch between null and String", m1.equals(m1d));

        assertTrue(m1.equals(m1b));
        assertFalse(m1.equals(m1c));

        Match m2a = new Match("foo", 10);
        Match m2b = new Match("foo", 10);
        Match m2c = new Match("foo", 20);
        Match m2d = new Match(null, 10);

        assertTrue(m2a.equals(m2b));
        assertFalse(m2a.equals(m2c));
        assertFalse(m2a.equals(m2d));

        Match m3a = new Match("bar", 10);
        Match m3b = new Match("bar", 20);

        assertFalse(m2a.equals(m3a));
        assertFalse(m2a.equals(m3b));
    }
}
