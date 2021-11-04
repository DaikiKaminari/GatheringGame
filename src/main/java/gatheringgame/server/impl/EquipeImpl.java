package gatheringgame.server.impl;

import gatheringgame.server.Equipe;
import gatheringgame.server.Joueur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class EquipeImpl extends UnicastRemoteObject implements Equipe {

    public static final int JOUEUR_PAR_EQUIPE = 5;
    private int score;
    private int numero; // 1 ou 2
    private List<Joueur> joueurs;

    /**
     *
     * @param numero 1 pour équipe 1 et 2 pour équipe 2
     * @throws RemoteException
     */
    EquipeImpl(int numero) throws RemoteException, Exception {
        joueurs = new ArrayList<>(JOUEUR_PAR_EQUIPE);
        this.score = 0;
        if(numero != 1 && numero != 2) {
            throw new Exception("le numéro d'équipe doit être égal à 1 ou 2");
        }
        this.numero = numero;
    }

    @Override
    public int getScore() throws RemoteException {
        return this.score;
    }

    @Override
    public void incrScore() throws RemoteException {
        this.score++;
    }

    @Override
    public boolean ajouterJoueur(Joueur j) throws RemoteException {
        if(joueurs.size() == JOUEUR_PAR_EQUIPE)
            return false;
        joueurs.add(j);
        return true;
    }

    @Override
    public void enleverJoueur(Joueur j) throws RemoteException {
        joueurs.remove(j);
    }

    @Override
    public void resetScore() throws RemoteException {
        this.score = 0;
    }

    @Override
    public int getNumero() throws RemoteException {
        return this.numero;
    }

    @Override
    public List<Joueur> getJoueurs() throws RemoteException {
        return this.joueurs;
    }
}
