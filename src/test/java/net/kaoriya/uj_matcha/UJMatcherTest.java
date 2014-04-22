package net.kaoriya.uj_matcha;

import org.junit.Test;
import static org.junit.Assert.*;

public class UJMatcherTest {

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
        UJMatcher m = UJMatcher.newMatcher(words);
        m.verbose = verbose;
        Match[] result = m.find(text).toArray(new Match[0]);
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

    @Test
    public void findMultiUsedChars() {
        findCheck(
                new String[] { "foo", "bar", "baz" },
                "barbazbafoo",
                new Match[] {
                    new Match("bar", 0),
                    new Match("baz", 3),
                    new Match("foo", 8),
                });
    }

    @Test
    public void findCyclicText() {
        findCheck(
                new String[] { "aba" },
                "ababa",
                new Match[] {
                    new Match("aba", 0),
                    new Match("aba", 2),
                });
    }

    @Test
    public void duplicatedWords() {
        findCheck(
                new String[] { "a", "a" },
                "a",
                new Match[] {
                    new Match("a", 0),
                    new Match("a", 0),
                });
        findCheck(
                new String[] { "bb", "b" },
                "b",
                new Match[] {
                    new Match("b", 0),
                });
        findCheck(
                new String[] { "ccc", "c" },
                "c",
                new Match[] {
                    new Match("c", 0),
                });
        findCheck(
                new String[] { "ddd", "dd" },
                "d",
                new Match[] {
                });
    }
}
