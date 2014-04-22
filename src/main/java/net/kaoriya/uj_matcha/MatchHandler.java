package net.kaoriya.uj_matcha;

public interface MatchHandler {

    /**
     * Called when matched.
     *
     * To get matched word use matcher.getWord(wordId).
     *
     * @param text match target text.
     * @param index tail position of matched.
     */
    boolean matched(UJMatcher matcher, int wordId, String text, int index);

}
