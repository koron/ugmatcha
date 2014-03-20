package net.kaoriya.qb.words_finder;

import org.junit.Test;
import static org.junit.Assert.*;

public class MatchTest {

    @Test
    public void stringify() {
        assertEquals("Match { text=\"null\" index=0 }",
                new Match(null, 0).toString());
    }

}
