package gatheringgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Matchmaking extends Remote {
    public static final int NB_MAX_JOUEUR = 4;

    public Jeu getJeu() throws Exception;
}
