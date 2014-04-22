package net.kaoriya.uj_matcha;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

class StateMachine {

    static final boolean DEBUG = false;

    final int[] state;

    FireHandler fireHandler;

    boolean verbose;

    StateMachine(int count) {
        this.state = new int[count];
    }

    void clear() {
        Arrays.fill(this.state, 0);
    }

    void put(List<Event> events) {
        if (DEBUG && this.verbose) {
            System.out.format("StateMachine#put events=%s", events);
            System.out.println("");
        }
        final int end = this.state.length;
        int eventIter = 0;
        int prevW = -1;
        int prevEventIter = -1;
        for (int w = 0; w < end;) {
            if (DEBUG && this.verbose) {
                System.out.format("  w=%d eventIter=%d\n", w, eventIter);
            }

            // Clear remained state when no more events.
            if (eventIter >= events.size()) {
                Arrays.fill(this.state, w, end, 0);
                break;
            }

            // Guard from infinite loop.
            if (w == prevW && eventIter == prevEventIter) {
                if (DEBUG && this.verbose) {
                    System.out.format("  terminated\n");
                }
                throw new RuntimeException("Terminated by possibility of infinite loop.  It maybe bug.  Please contact author");
            }
            prevW = w;
            prevEventIter = eventIter;

            // Clear state till next event.
            int eventIterOrig = eventIter;
            Event first = events.get(eventIter);
            if (w < first.id) {
                Arrays.fill(this.state, w, first.id, 0);
                w = first.id;
            }
            int nextW = first.nextId;

            // is first lead event?
            if (first.index == 0) {
                if (first.last) {
                    if (this.fireHandler.fired(this, first)) {
                        return;
                    }
                } else {
                    ArrayUtils.shiftRight(this.state, w, nextW, 1);
                    this.state[w] = 1;
                }
                ++w;
                ++eventIter;
            }

            // Apply events for the word.
            boolean padding = false;
            if (DEBUG && this.verbose) {
                System.out.format("    nextW=%d eventIter=%d\n", nextW, eventIter);
            }
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
                        if (this.fireHandler.fired(this, ev)) {
                            return;
                        }
                    } else {
                        this.state[w2] += 1;
                    }
                    ++w2;
                    ++eventIter;
                } else {
                    ++eventIter;
                }
            }

            if (eventIter == eventIterOrig) {
                ++eventIter;
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
        }
    }
}
