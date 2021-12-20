package gatheringgame.server.impl;

import gatheringgame.server.Jeu;
import gatheringgame.server.Matchmaking;

import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class MatchmakingImpl extends UnicastRemoteObject implements Matchmaking {


    private final Map<Integer, Jeu> jeux;
    private int nbJeu;

    public MatchmakingImpl() throws Exception {
        jeux = new HashMap<>();
        jeux.put(0, new JeuImpl());
        nbJeu = 1;
    }

    /**
     * @return le jeu
     * @throws Exception
     */
    @Override
    public synchronized Jeu getJeu() throws Exception {
        Jeu jeu;
        if (jeux.get(nbJeu - 1).aCommence()) {
            jeu = this.creerJeu();
        } else {
            jeu = jeux.get(nbJeu - 1);
        }

        return jeu;
    }

    private Jeu creerJeu() throws Exception {
        Jeu jeu = new JeuImpl();
        jeux.put(nbJeu, jeu);
        nbJeu++;
        return jeu;
    }
}
