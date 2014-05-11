package net.kaoriya.ugmatcha;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Many words Matcher.
 */
public class UGMatcher {

    class WorkingArea implements FireHandler {
        String text;
        MatchHandler handler;
        int max;

        int last;
        int index;
        int foundCount;
        boolean terminated;

        void setup(String text, MatchHandler handler, int max) {
            this.text = text;
            this.handler = handler;
            this.max = max;

            this.last = this.text.length() - 1;
            this.index = -1;
            this.foundCount = 0;
            this.terminated = false;
        }

        void teardown() {
            this.text = null;
            this.handler = null;
        }

        boolean hasNext() {
            return this.index < this.last;
        }

        char next() {
            return this.text.charAt(++this.index);
        }

        public boolean fired(StateMachine src, Event event) {
            ++this.foundCount;
            if (!this.handler.matched(UGMatcher.this, event.id,
                        this.text, this.index)
                    || (this.max > 0 && this.foundCount >= this.max))
            {
                this.terminated = true;
            }
            return this.terminated;
        }
    }

    boolean verbose = false;

    final WordsTable wordsTable;

    final StateMachine stateMachine;

    final WorkingArea work = new WorkingArea();

    UGMatcher(WordsTable wordsTable) {
        this.wordsTable = wordsTable;
        this.stateMachine = new StateMachine(wordsTable.index);
    }

    /**
     * Get text of a matched word.
     *
     * @param wordId Word ID.
     */
    public String getWord(int wordId) {
        Word w = this.wordsTable.getWord(wordId);
        return w != null ? w.text : null;
    }

    /**
     * Match with text.
     *
     * When matched callback with handler.
     *
     * @param text Target text to match.
     * @param handler Callback interface when found match.
     * @param max Max count of match, 0 for ALL.
     *
     * @return True when found one or more, otherwise false.
     */
    public boolean match(String text, MatchHandler handler, int max) {
        // Set up a match.
        this.work.setup(text, handler, max);
        this.stateMachine.reset();
        this.stateMachine.verbose = this.verbose;
        this.stateMachine.fireHandler = this.work;

        while (this.work.hasNext()) {
            char ch = this.work.next();
            List<Event> events = this.wordsTable.getEvents(ch);
            if (events == null) {
                this.stateMachine.reset();
                continue;
            }
            this.stateMachine.put(events);
            if (this.work.terminated) {
                break;
            }
        }

        // Clean up.
        this.stateMachine.fireHandler = null;
        this.work.teardown();

        return this.work.foundCount > 0;
    }

    /**
     * Match with text, for all matches.
     *
     * @param text Target text to match.
     * @param handler Callback interface when found match.
     *
     * @return True when found one or more, otherwise false.
     */
    public boolean match(String text, MatchHandler handler) {
        return match(text, handler, 0);
    }

    /**
     * Find matches as list of Match.
     *
     * @param text Target text to match.
     * @param max Max count of match, 0 for ALL.
     *
     * @return List of matched words.
     */
    public List<Match> find(String text, int max) {
        final ArrayList<Match> found = new ArrayList<>();
        match(text, new MatchHandler() {
            public boolean matched(UGMatcher matcher, int wordId, String text,
                int index)
            {
                String w = matcher.getWord(wordId);
                found.add(new Match(w, index - w.length() + 1));
                return true;
            }
        }, max);
        return found;
    }

    /**
     * Find matches as list of Match.
     *
     * @param text Target text to match.
     *
     * @return List of matched words.
     */
    public List<Match> find(String text) {
        return find(text, 0);
    }

    ////////////////////////////////////////////////////////////////////////
    // Static Methods

    /**
     * Create a new UGMatcher.
     *
     * @param words List of words to match.
     *
     * @return A new matcher.
     */
    public static UGMatcher newMatcher(List<String> words) {
        WordsTable wordsTable = new WordsTable();
        wordsTable.addAll(words);
        wordsTable.finish();
        return new UGMatcher(wordsTable);
    }

    /**
     * Create a new UGMatcher.
     *
     * @param words Array of words to match.
     *
     * @return A new matcher.
     */
    public static UGMatcher newMatcher(String ...words) {
        return newMatcher(Arrays.asList(words));
    }

    /**
     * Create a new clone UGMatcher.
     *
     * @param matcher Original matcher to be cloned.
     *
     * @return A new matcher.
     */
    public static UGMatcher newMatcher(UGMatcher matcher) {
        return new UGMatcher(matcher.wordsTable);
    }
}
