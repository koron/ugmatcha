package net.kaoriya.ugmatcha;

import org.junit.Test;
import static org.junit.Assert.*;

public class WordTest {

    @Test
    public void stringify() {
        assertEquals("Word{text=null index=0 rank=0}",
                new Word(null, 0).toString());
    }

    @Test
    public void equality1() {
        Word w1 = new Word(null, 0);
        Word w2 = new Word("foo", 0);
        assertTrue("match with it self", w1.equals(w1));
        assertFalse("unmatch with null", w1.equals(null));
        assertFalse("unmatch with other class", w1.equals(new Object()));
        assertFalse("unmatch between null and String", w1.equals(w2));
    }

    @Test
    public void equality2() {
        Word w1 = new Word("foo", 0);
        Word w2 = new Word("foo", 0);
        Word w3 = new Word("bar", 0);
        Word w4 = new Word("foo", 1);

        assertTrue("different object but same contenents", w1.equals(w2));
        assertTrue("reverse", w2.equals(w1));
        assertFalse("different text", w1.equals(w3));
        assertFalse("different index", w1.equals(w4));
    }

    @Test
    public void equality3() {
        Word w1 = new Word(null, 0);
        Word w2 = new Word(null, 0);
        Word w3 = new Word(null, 1);

        assertTrue(w1.equals(w2));
        assertTrue(w2.equals(w1));
        assertFalse(w1.equals(w3));
        assertFalse(w3.equals(w1));
        assertFalse(w3.equals(w2));
        assertFalse(w2.equals(w3));
    }
}
