package net.kaoriya.ugmatcha;

import org.junit.Test;
import static org.junit.Assert.*;

public class StateMachineTest {
    @Test
    public void dumpState() {
        StateMachine m = new StateMachine(10);
        m.clear();
        m.dumpState(0, 10, 5, "dumpState:");
    }
}
