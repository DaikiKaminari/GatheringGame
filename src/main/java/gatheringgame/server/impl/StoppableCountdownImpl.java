package gatheringgame.server.impl;

import gatheringgame.server.Jeu;
import gatheringgame.server.StoppableCountdown;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class StoppableCountdownImpl implements StoppableCountdown {

    private final int secondes;
    private final Timer timer;
    private boolean enRoute;
    private int secondesRestantes;
    private Jeu jeu;

    StoppableCountdownImpl(int secondes) {
        enRoute = false;
        this.secondes = secondes;
        secondesRestantes = secondes;
        timer = new Timer();
    }

    StoppableCountdownImpl(int secondes, Jeu jeu) {
        this(secondes);
        this.jeu = jeu;
    }

    @Override
    public int getSecondesRestantes() {
        return this.secondesRestantes;
    }

    @Override
    public void pause() {
        timer.cancel();
        this.enRoute = false;
    }

    @Override
    public void start() {
        this.enRoute = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (secondesRestantes > 0) {
                    secondesRestantes--;
                } else {
                    try {
                        jeu.finir();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    this.cancel();
                }
            }
        }, 1000, 1000);
    }

    @Override
    public void reset() {
        this.secondesRestantes = this.secondes;
    }

    @Override
    public boolean enRoute() {
        return this.enRoute;
    }

}
