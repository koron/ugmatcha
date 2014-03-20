package net.kaoriya.qb.words_finder;

import org.junit.Test;
import static org.junit.Assert.*;

public class FinderTest {

    private static void findCheck(
            String[] words,
            String text,
            Match[] expected)
    {
        findCheck(words, text, expected, false);
    }

    private static void findCheck(
            String[] words,
            String text,
            Match[] expected,
            boolean verbose)
    {
        Finder f = new Finder(words);
        f.verbose = verbose;
        Match[] result = f.findMatches(text).toArray(new Match[0]);
        assertArrayEquals("error detected scanning: \"" + text + "\"",
                expected, result);
    }

    @Test
    public void findEmpty() {
        findCheck(new String[] {}, "abc", new Match[]{});
    }

    @Test
    public void findSimple() {
        findCheck(
                new String[] { "abc", },
                "abc",
                new Match[] {
                    new Match("abc", 0),
                });
        findCheck(
                new String[] { "abc", "def", "ghi" },
                "abcdefghi",
                new Match[] {
                    new Match("abc", 0),
                    new Match("def", 3),
                    new Match("ghi", 6),
                });
    }

    @Test
    public void findMultipleChar() {
        findCheck(
                new String[] { "foo", },
                "foo",
                new Match[] {
                    new Match("foo", 0),
                });
        findCheck(
                new String[] { "a", },
                "aaa",
                new Match[] {
                    new Match("a", 0),
                    new Match("a", 1),
                    new Match("a", 2),
                });
    }
}
