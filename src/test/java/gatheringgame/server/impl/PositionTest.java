package gatheringgame.server.impl;

import gatheringgame.server.Position;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PositionTest {
    Position p1, p2, p3, p4;

    {
        try {
            p1 = new PositionImpl(1, 1);
            p2 = new PositionImpl(1, 1);
            p3 = new PositionImpl(2, 1);
            p4 = new PositionImpl(1, 2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEqual1() {
        assertEquals(p1, p2);
    }

    @Test
    public void testEqual2() {
        assertNotEquals(p1, p3);
    }

    @Test
    public void testEqual3() {
        assertNotEquals(p1, p4);
    }

    @Test
    public void testEqual4() {
        assertNotEquals(p3, p4);
    }

    @Test
    public void testEqual5() {
        assertEquals(p1, p1);
    }

    @Test
    public void testHash1() {
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testHash2() {
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }
}
