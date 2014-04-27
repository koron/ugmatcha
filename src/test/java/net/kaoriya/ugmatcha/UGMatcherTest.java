package net.kaoriya.ugmatcha;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class UGMatcherTest {

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
        UGMatcher m = UGMatcher.newMatcher(words);
        m.verbose = verbose;
        Match[] result = m.find(text).toArray(new Match[0]);
        assertArrayEquals("error detected scanning: \"" + text + "\"",
                expected, result);
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
    public void findUnmatch() {
        findCheck(
                new String[] { "abc", },
                "def",
                new Match[] {
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

    @Test
    public void getInvalidWord() {
        UGMatcher m = UGMatcher.newMatcher("foo", "bar", "baz");

        assertNull(m.getWord(-1));
        assertEquals("foo", m.getWord(0));
        assertEquals("foo", m.getWord(1));
        assertEquals("bar", m.getWord(2));
        assertEquals("baz", m.getWord(3));
        assertNull(m.getWord(4));
    }

    @Test
    public void interruptMatches() {
        UGMatcher m = UGMatcher.newMatcher("foo", "bar", "baz");
        final ArrayList<Match> found = new ArrayList<>();
        m.match("foobarbaz", new MatchHandler() {
            public boolean matched(UGMatcher matcher, int wordId, String text,
                int index)
            {
                String w = matcher.getWord(wordId);
                found.add(new Match(w, index - w.length() + 1));
                return found.size() < 2 ? true : false;
            }
        });

        Match[] result = found.toArray(new Match[0]);
        assertArrayEquals("interruptMatches failed",
                new Match[] {
                    new Match("foo", 0),
                    new Match("bar", 3),
                },
                result);
    }
}
