package net.kaoriya.qb.words_finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Finder {

    boolean verbose = false;

    final ArrayList<Word> wordsTable = new ArrayList<>();

    int wordsIndex = 0;

    Word[] id2word;

    final HashMap<Character, ArrayList<Event>> eventsMap = new HashMap<>();

    public Finder(List<String> words) {
        wordsInit();
        for (String word : words) {
            wordsAdd(word);
        }
        wordsFinish();
    }

    public Finder(String[] words) {
        this(Arrays.asList(words));
    }

    private void wordsInit() {
        this.wordsTable.clear();
        this.wordsIndex = 0;
        this.eventsMap.clear();
    }

    private void wordsAdd(String text) {
        // Add words.
        Word w = new Word(text, this.wordsIndex);
        this.wordsTable.add(w);
        this.wordsIndex += w.rank;
        // Generate eventsMap.
        for (int i = 0, last = text.length() - 1; i <= last ; ++i) {
            char ch = text.charAt(i);
            ArrayList<Event> list = eventsMap.get(ch);
            if (list == null) {
                list = new ArrayList<>();
                eventsMap.put(ch, list);
            }
            boolean is_last = i == last;
            list.add(new Event(w.index, w.index + w.rank, i, is_last));
        }
    }

    private void wordsFinish() {
        this.id2word = new Word[this.wordsIndex];
        int index = 0;
        for (Word w : this.wordsTable) {
            for (int next = index + w.rank; index < next; ++index) {
                this.id2word[index] = w;
            }
        }
    }

    public Word getWord(int id) {
        return this.id2word[id];
    }

    public boolean scan(String text, Handler handler) {
        return scan(text, handler, 0);
    }

    public boolean scan(String text, Handler handler, int max) {
        Context c = new Context(this, this.wordsIndex);
        return c.scan(text, handler, max);
    }

    public List<Match> findMatches(String text) {
        return findMatches(text, 0);
    }

    public List<Match> findMatches(String text, int max) {
        Context c = new Context(this, this.wordsIndex);
        return c.findMatches(text, max);
    }

    public Context newContext() {
        return new Context(this, this.wordsIndex);
    }
}
