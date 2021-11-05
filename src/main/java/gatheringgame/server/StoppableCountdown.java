package gatheringgame.server;

public interface StoppableCountdown {
    public int getSecondesRestantes();
    public void pause();
    public void start();
    public void reset();
    public boolean enRoute();

}
