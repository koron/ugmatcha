package net.kaoriya.ugmatcha;

public interface MatchHandler {

    /**
     * Called when matched.
     *
     * To get matched word use matcher.getWord(wordId).
     *
     * @param text match target text.
     * @param index tail position of matched.
     */
    boolean matched(UGMatcher matcher, int wordId, String text, int index);

}
