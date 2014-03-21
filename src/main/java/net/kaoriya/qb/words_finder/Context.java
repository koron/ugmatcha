package net.kaoriya.qb.words_finder;

import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

class Context {

    static final boolean DEBUG = true;

    final Finder finder;

    int foundCount;

    final int[] state;

    boolean terminated;

    boolean verbose;

    Context(Finder finder, int len) {
        this.finder = finder;
        this.foundCount = 0;
        this.state = new int[len];
        this.terminated = false;
    }

    void clear() {
        Arrays.fill(this.state, 0);
    }

    private boolean onFound(Event ev, Handler handler, int max, int index) {
        ++this.foundCount;
        if (!handler.found(this.finder, ev.id, index) ||
                max > 0 && this.foundCount >= max)
        {
            this.terminated = true;
        }
        return this.terminated;
    }

    boolean put(List<Event> events, Handler handler, int max, int index) {
        if (DEBUG && this.verbose) {
            System.out.format("Context#put events=%s index=%d",
                    events, index);
            System.out.println("");
        }
        boolean found = false;
        final int end = this.state.length;
        int eventIter = 0;
        int prevW = -1;
        for (int w = 0; w < end;) {
            if (DEBUG && this.verbose) {
                System.out.format("   w=%d\n", w);
            }
            // Clear remained state when no more events.
            if (eventIter >= events.size()) {
                Arrays.fill(this.state, w, end, 0);
                break;
            }

            // Clear state till next event.
            Event first = events.get(eventIter);
            if (w < first.id) {
                Arrays.fill(this.state, w, first.id, 0);
                w = first.id;
            }
            int nextW = first.nextId;

            // is first lead event?
            if (first.index == 0) {
                if (first.last) {
                    found = true;
                    onFound(first, handler, max, index);
                } else {
                    ArrayUtils.shiftRight(this.state, w, nextW, 1);
                    this.state[w] = 1;
                }
                ++w;
                ++eventIter;
            }

            // Apply events for the word.
            boolean padding = false;
            for (int w2 = w; w2 < nextW;) {
                if (DEBUG && this.verbose) {
                    System.out.format("    w2=%d eventIter=%d\n",
                            w2, eventIter);
                }
                if (eventIter >= events.size()) {
                    break;
                }
                Event ev = events.get(eventIter);
                if (ev.id != first.id) {
                    Arrays.fill(this.state, w2, nextW, 0);
                    break;
                }
                // Update state
                if (this.state[w2] < ev.index) {
                    this.state[w2] = 0;
                    padding = true;
                    ++w2;
                } else if (this.state[w2] == ev.index) {
                    if (ev.last) {
                        this.state[w2] = 0;
                        padding = true;
                        found = true;
                        onFound(ev, handler, max, index);
                    } else {
                        this.state[w2] += 1;
                    }
                    ++w2;
                    ++eventIter;
                } else {
                    ++eventIter;
                }
            }

            // Padding state.
            if (padding) {
                for (int r = w; r < nextW; ++r) {
                    if (this.state[r] != 0) {
                        this.state[w] = this.state[r];
                        ++w;
                    }
                }
                Arrays.fill(this.state, w, nextW, 0);
            }
            w = nextW;
        }

        if (DEBUG && this.verbose) {
            System.out.format("  state=%s", ArrayUtils.toString(this.state));
            System.out.println("");
            System.out.format("  found=%s", found);
            System.out.println("");
        }

        return found;
    }
}
