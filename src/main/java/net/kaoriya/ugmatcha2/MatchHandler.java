package net.kaoriya.ugmatcha2;

public interface MatchHandler<T> {

    /**
     * Called when find pattern.
     *
     * @param index Position where pattern starts.
     * @param pattern Found pattern.
     * @param value Value mapped to that pattern.
     */
    boolean matched(int index, String pattern, T value);

}
