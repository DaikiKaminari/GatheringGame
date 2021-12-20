package gatheringgame.server;

import java.rmi.Remote;

public interface Matchmaking extends Remote {
    int NB_MAX_JOUEUR = 4;

    Jeu getJeu() throws Exception;
}
