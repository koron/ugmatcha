package net.kaoriya.qb.words_finder.benchmark;

import java.util.Random;
import java.util.ArrayList;
import java.io.File;
import java.util.List;

public class GenerateMain {

    public static final String WORDS_PATH = "tmp/words.txt";
    public static final int WORDS_SIZE = 100;
    public static final int WORD_MINLEN = 16;
    public static final int WORD_MAXLEN = 24;
    public static final String WORD_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";

    public static final String TARGETS_PATH = "tmp/targets.txt";
    public static final int TARGETS_SIZE = 10000;
    public static final float TARGETS_RATIO = 0.03f;
    public static final int TARGET_MINLEN = 64;
    public static final int TARGET_MAXLEN = 128;

    public static String generateRandomString(Random r, int len) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            s.append(WORD_CHARS.charAt(r.nextInt(WORD_CHARS.length())));
        }
        return s.toString();
    }

    public static List<String> generateRandomWords(Random r, int size) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            int len = WORD_MINLEN + r.nextInt(WORD_MAXLEN - WORD_MINLEN + 1);
            String w = generateRandomString(r, len);
            list.add(w);
        }
        return list;
    }

    public static String generateHitString(
            Random r,
            int len,
            List<String> words)
    {
        String w = words.get(r.nextInt(words.size()));
        int len2 = len - w.length();
        int preLen = r.nextInt(len2 - 1) + 1;
        int postLen = len2 - preLen;
        return generateRandomString(r, preLen) + w
            + generateRandomString(r, postLen);
    }

    public static List<String> generateTargets(Random r, List<String> words) {
        ArrayList<String> targets = new ArrayList<>();
        for (int i = 0; i < TARGETS_SIZE; ++i) {
            String w = null;
            int len = TARGET_MINLEN
                + r.nextInt(TARGET_MAXLEN - TARGET_MINLEN + 1);
            if (r.nextFloat() < TARGETS_RATIO) {
                w = generateHitString(r, len, words);
            } else {
                w = generateRandomString(r, len);
            }
            targets.add(w);
        }
        return targets;
    }

    public static void main(String[] args) throws Exception {
        List<String> words = generateRandomWords(new Random(0), WORDS_SIZE);
        Utils.writeLines(new File(WORDS_PATH), words);
        List<String> targets = generateTargets(new Random(1), words);
        Utils.writeLines(new File(TARGETS_PATH), targets);
    }
}
