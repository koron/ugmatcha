package net.kaoriya.ugmatcha;

import org.junit.Test;
import static org.junit.Assert.*;

public class WordsTableTest {

    @Test
    public void getWord() {
        WordsTable t = new WordsTable();
        t.add("foo");
        t.add("bar");
        t.add("baz");
        t.finish();

        assertNull(t.getWord(-1));
        assertEquals(new Word("foo", 0), t.getWord(0));
        assertEquals(new Word("foo", 0), t.getWord(1));
        assertEquals(new Word("bar", 2), t.getWord(2));
        assertEquals(new Word("baz", 3), t.getWord(3));
        assertNull(t.getWord(4));
    }
}
