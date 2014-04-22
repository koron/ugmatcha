package net.kaoriya.uj_matcha;

import org.junit.Test;
import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void stringify() {
        assertEquals("Event{id=0 nextId=0 index=0 last=false}",
                new Event(0, 0, 0, false).toString());
    }

}
