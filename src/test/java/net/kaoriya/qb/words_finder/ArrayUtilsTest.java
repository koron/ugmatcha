package net.kaoriya.qb.words_finder;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayUtilsTest {

    @Test
    public void ctor() {
        ArrayUtils u = new ArrayUtils();
        assertNotNull(u);
    }

    @Test
    public void stringifyArray() {
        assertEquals("[0, 0]", ArrayUtils.toString(new int[] {0, 0}));
    }
}
