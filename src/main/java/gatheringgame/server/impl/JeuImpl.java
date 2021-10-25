package gatheringgame.server.impl;

import gatheringgame.server.Jeu;
import gatheringgame.server.Joueur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class JeuImpl extends UnicastRemoteObject implements Jeu {

    public int nbJoueur;
    public Map<Integer, Joueur> joueurs;

    public JeuImpl() throws RemoteException {
        nbJoueur = 0;
        joueurs = new HashMap<Integer, Joueur>();
    }

    @Override
    public synchronized Joueur join() throws RemoteException {
        Joueur j = new JoueurImpl(50, 50);
        joueurs.put(nbJoueur, j);
        return j;
    }

    @Override
    public List<Joueur> getJoueurs() throws RemoteException {
        return new ArrayList<Joueur>(joueurs.values());
    }
}
