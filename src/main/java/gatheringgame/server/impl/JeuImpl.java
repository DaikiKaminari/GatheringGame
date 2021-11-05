package gatheringgame.server.impl;

import gatheringgame.server.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class JeuImpl extends UnicastRemoteObject implements Jeu {

    private int nbJoueur;
    private Map<Integer, Joueur> joueurs;
    private Equipe equipeUn;
    private Equipe equipeDeux;
    private Usine usine;
    private boolean started;

    public JeuImpl() throws Exception {
        nbJoueur = 0;
        joueurs = new HashMap<Integer, Joueur>();
        equipeUn = new EquipeImpl(1);
        equipeDeux = new EquipeImpl(2);
        usine = new UsineImpl(this);
        usine.ajouterEquipe(equipeUn);
        usine.ajouterEquipe(equipeDeux);

        this.started = false;
    }

    @Override
    public synchronized Joueur join() throws RemoteException {

        if(nbJoueur==Matchmaking.NB_MAX_JOUEUR)
            return null;

        Joueur j = new JoueurImpl(50, 50, nbJoueur % 2 == 0 ? equipeUn : equipeDeux);
        joueurs.put(nbJoueur, j);
        nbJoueur++;

        if(nbJoueur == Matchmaking.NB_MAX_JOUEUR) {
            this.commenceJeu();
        }
        return j;
    }

    @Override
    public List<Joueur> getJoueurs() throws RemoteException {
        return new ArrayList<Joueur>(joueurs.values());
    }

    @Override
    public Usine getUsine() throws RemoteException {
        return this.usine;
    }

    @Override
    public List<Resource> getResources() throws RemoteException {
        //TODO
        return null;
    }

    @Override
    public synchronized void ajouterResource() throws RemoteException {
        //TODO Appelé périodiquement par un thread de génération de ressource
    }

    @Override
    public boolean aCommence() throws RemoteException {
        return this.started;
    }

    @Override
    public int getNbJoueur() throws RemoteException {
        return this.nbJoueur;
    }

    private void commenceJeu() {
        this.started = true;
    }
}
