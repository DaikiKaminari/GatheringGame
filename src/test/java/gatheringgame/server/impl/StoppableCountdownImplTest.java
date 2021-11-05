package gatheringgame.server.impl;

import gatheringgame.server.StoppableCountdown;
import org.junit.Test;

import static org.junit.Assert.*;

public class StoppableCountdownImplTest {

    @Test
    public void getSecondesRestantes() throws InterruptedException {
        StoppableCountdown countdown = new StoppableCountdownImpl(10);
        countdown.start();
        Thread.sleep(2000);
        System.out.println(countdown.getSecondesRestantes());
        countdown.reset();

    }
}