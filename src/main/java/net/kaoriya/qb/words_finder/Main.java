package net.kaoriya.qb.words_finder;

public class Main {

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

        boolean error = false;
        if (result.length != expected.length) {
            System.out.format("length is different: %d (expected:%d)",
                    result.length, expected.length);
            System.out.println("");
            error = true;
        }
        int len = Math.min(result.length, expected.length);
        for (int i = 0; i < len; ++i) {
            if (!expected[i].equals(result[i])) {
                System.out.format("item #%d is different: %s (expected:%s)",
                        i, result[i], expected[i]);
                System.out.println("");
                error = true;
                break;
            }
        }
        if (!error) {
            System.out.format("OK for text=\"%s\"", text);
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        findCheck(
                new String[] { "foo", },
                "foo",
                new Match[] {
                    new Match("foo", 0),
                },
                true);
    }

}
