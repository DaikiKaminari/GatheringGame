package gatheringgame.server;

public interface StoppableCountdown {
    int getSecondesRestantes();

    void pause();

    void start();

    void reset();

    boolean enRoute();

}
