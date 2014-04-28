package net.kaoriya.ugmatcha;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

class StateMachine {

    static final boolean DEBUG = false;

    final int[] state;

    int zeroBorder;

    FireHandler fireHandler;

    boolean verbose;

    StateMachine(int count) {
        this.state = new int[count];
        this.zeroBorder = 0;
    }

    void clear() {
        Arrays.fill(this.state, 0);
        this.zeroBorder = 0;
    }

    void put(List<Event> events) {
        if (DEBUG && this.verbose) {
            System.out.format("StateMachine#put2: %s\n", events);
        }

        int curr = -1;
        int next = 0;
        int ptr = 0;
        boolean need_padding = false;

        for (int i = 0, N = events.size(); i < N; ++i) {
            Event event = events.get(i);

            if (DEBUG && this.verbose) {
                System.out.format("  event=%s\n", event);
            }

            if (curr != event.id) {
                if (need_padding) {
                    padding(curr, next, "  PADDING1");
                }
                if (next < event.id) {
                    Arrays.fill(this.state, next, event.id, 0);
                }
                curr = event.id;
                next = event.nextId;
                ptr = curr;
                need_padding = false;
            }

            if (DEBUG && this.verbose) {
                System.out.format(
                        "  curr=%d next=%d ptr=%d need_padding=%s\n",
                        curr, next, ptr, need_padding);
            }

            while (ptr < next) {
                int s = this.state[ptr];
                if (DEBUG && this.verbose) {
                    dumpState(curr, next, ptr, "    PRE :");
                }
                if (s < event.index) {
                    this.state[ptr] = 0;
                    need_padding = true;
                    ++ptr;
                } else if (s == event.index) {
                    if (event.last) {
                        if (DEBUG && this.verbose) {
                            System.out.format("    *fire: %s\n", event);
                        }
                        this.state[ptr] = 0;
                        need_padding = true;
                        if (this.fireHandler.fired(this, event)) {
                            return;
                        }
                    } else {
                        if (DEBUG && this.verbose) {
                            System.out.format("    *add: %d\n",
                                    ptr - curr);
                        }
                        this.state[ptr] += 1;
                    }
                    ++ptr;
                    break;
                } else {
                    if (event.index == 0) {
                        if (DEBUG && this.verbose) {
                            System.out.format("    *new: %d\n", ptr - curr);
                        }
                        ArrayUtils.shiftRight(this.state, ptr, next, 1);
                        this.state[ptr] = 1;
                        ++ptr;
                    } else {
                        if (DEBUG && this.verbose) {
                            System.out.format("    *skip\n");
                        }
                    }
                    break;
                }
            }
            if (DEBUG && this.verbose) {
                dumpState(curr, next, ptr, "    POST:");
            }
        }

        if (need_padding) {
            padding(curr, next, "  PADDING2");
        }

        if (next < this.zeroBorder) {
            Arrays.fill(this.state, next, this.zeroBorder, 0);
        }
        this.zeroBorder = next;

        if (DEBUG && this.verbose) {
            System.out.println("");
        }
    }

    void padding(int start, int end, String header) {
        if (DEBUG && this.verbose) {
            dumpState(start, end, -1, header + " PRE :");
        }

        int w = start;
        for (int r = start; r < end; ++r) {
            if (this.state[r] != 0) {
                this.state[w] = this.state[r];
                ++w;
            }
        }
        Arrays.fill(this.state, w, end, 0);

        if (DEBUG && this.verbose) {
            dumpState(start, end, -1, header + " POST:");
        }
    }

    void dumpState(int start, int end, int ptr, String header) {
        StringBuilder s = new StringBuilder(header);
        for (int i = start; i < end; ++i) {
            if (i == ptr) {
                s.append("[").append(this.state[i]).append("]");
            } else {
                s.append(" ").append(this.state[i]).append(" ");
            }
        }
        System.out.println(s.toString());
    }
}
