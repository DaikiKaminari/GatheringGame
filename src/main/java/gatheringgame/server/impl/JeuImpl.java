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

    public JeuImpl() throws Exception {
        nbJoueur = 0;
        joueurs = new HashMap<Integer, Joueur>();
        equipeUn = new EquipeImpl(1);
        equipeDeux = new EquipeImpl(2);
        usine = new UsineImpl(this);
        usine.ajouterEquipe(equipeUn);
        usine.ajouterEquipe(equipeDeux);
    }

    @Override
    public synchronized Joueur join() throws RemoteException {
        Joueur j = new JoueurImpl(50, 50, equipeUn);
        joueurs.put(nbJoueur, j);
        nbJoueur++;
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
    public void ajouterResource() throws RemoteException {
        //TODO Appelé périodiquement par un thread de génération de ressource
    }
}
