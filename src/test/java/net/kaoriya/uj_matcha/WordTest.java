package net.kaoriya.uj_matcha;

import org.junit.Test;
import static org.junit.Assert.*;

public class WordTest {

    @Test
    public void stringify() {
        assertEquals("Word{text=null index=0 rank=0}",
                new Word(null, 0).toString());
    }

}
