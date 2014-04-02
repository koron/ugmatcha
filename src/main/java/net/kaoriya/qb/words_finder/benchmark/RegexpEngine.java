package net.kaoriya.qb.words_finder.benchmark;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexpEngine implements Engine {

    private ArrayList<String> list = new ArrayList<>();

    private Pattern pattern = null;

    public void add(String word) {
        this.list.add(word);
        this.pattern = null;
    }

    public String findOne(String text) {
        Pattern p = getPattern();
        Matcher m = p.matcher(text);
        if (!m.find()) {
            return null;
        }
        return m.group();
    }

    Pattern getPattern() {
        if (this.pattern != null) {
            return this.pattern;
        }
        StringBuilder s = new StringBuilder();
        for (String word : list) {
            if (s.length() > 0) {
                s.append("|");
            }
            // FIXME: need regexp escape.
            s.append(word);
        }
        return this.pattern = Pattern.compile(s.toString());
    }

    public String getName() {
        return "Regexp";
    }
}
