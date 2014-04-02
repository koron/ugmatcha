package net.kaoriya.qb.words_finder.benchmark;

import java.util.ArrayList;

import net.kaoriya.qb.words_finder.Context;
import net.kaoriya.qb.words_finder.Finder;
import net.kaoriya.qb.words_finder.Handler;

public class FinderEngine implements Engine, Handler {

    private ArrayList<String> list = new ArrayList<>();

    private Finder finder = null;

    private Context context = null;

    private String scanResult = null;

    public void add(String word) {
        this.list.add(word);
        this.finder = null;
        this.context = null;
    }

    public String findOne(String text) {
        this.scanResult = null;
        Context c = getContext();
        c.scan(text, this, 1);
        return this.scanResult;
    }

    Context getContext() {
        if (this.context == null) {
            this.context = getFinder().newContext();
        }
        return this.context;
    }

    Finder getFinder() {
        if (this.finder == null) {
            this.finder = new Finder(this.list);
        }
        return this.finder;
    }

    public boolean found(Finder finder, int id, int index) {
        this.scanResult = finder.getWord(id).text;
        return true;
    }

    public String getName() {
        return "Finder";
    }
}
