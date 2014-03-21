package net.kaoriya.qb.words_finder;

import org.junit.Test;
import static org.junit.Assert.*;

public class RankUtilsTest {

    @Test
    public void ctor() {
        assertNotNull(new RankUtils());
    }

    @Test
    public void rank0() {
        assertEquals(0, RankUtils.rank(null));
        assertEquals(0, RankUtils.rank(""));
    }

    @Test
    public void rank1() {
        assertEquals(1, RankUtils.rank("A"));
        assertEquals(1, RankUtils.rank("AB"));
        assertEquals(1, RankUtils.rank("B"));
    }

    @Test
    public void rank2() {
        assertEquals(2, RankUtils.rank("AA"));
        assertEquals(2, RankUtils.rank("AAB"));
        assertEquals(2, RankUtils.rank("AABB"));
        assertEquals(2, RankUtils.rank("ABB"));
        assertEquals(2, RankUtils.rank("BB"));
    }

    @Test
    public void rank3() {
        assertEquals(3, RankUtils.rank("AAA"));
        assertEquals(3, RankUtils.rank("AAAB"));
        assertEquals(3, RankUtils.rank("AAABB"));
        assertEquals(3, RankUtils.rank("AAABBB"));
        assertEquals(3, RankUtils.rank("AABBB"));
        assertEquals(3, RankUtils.rank("ABBB"));
        assertEquals(3, RankUtils.rank("BBB"));
    }
}
