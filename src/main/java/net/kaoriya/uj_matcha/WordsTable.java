package net.kaoriya.uj_matcha;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class WordsTable {

    final ArrayList<Word> table = new ArrayList<>();

    final HashMap<Character, ArrayList<Event>> eventsMap = new HashMap<>();

    int index = 0;

    Word[] array;

    WordsTable() {
    }

    void addAll(List<String> words) {
        for (String word : words) {
            add(word);
        }
    }

    void add(String word) {
        // Add words.
        Word w = new Word(word, this.index);
        this.table.add(w);
        this.index += w.rank;

        // Generate eventsMap.
        for (int i = 0, last = word.length() - 1; i <= last ; ++i) {
            char ch = word.charAt(i);
            ArrayList<Event> list = this.eventsMap.get(ch);
            if (list == null) {
                list = new ArrayList<>();
                this.eventsMap.put(ch, list);
            }
            boolean isLast = i == last;
            list.add(new Event(w.index, w.index + w.rank, i, isLast));
        }
    }

    void finish() {
        this.array = new Word[this.index];
        int index = 0;
        for (Word w : this.table) {
            for (int next = index + w.rank; index < next; ++index) {
                this.array[index] = w;
            }
        }
    }

    Word getWord(int wordId) {
        if (wordId < 0 || wordId >= this.array.length) {
            return null;
        }
        return this.array[wordId];
    }

    List<Event> getEvents(char ch) {
        return this.eventsMap.get(ch);
    }
}
