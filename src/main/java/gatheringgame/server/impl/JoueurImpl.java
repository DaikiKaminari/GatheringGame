package gatheringgame.server.impl;

import gatheringgame.server.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class JoueurImpl extends UnicastRemoteObject implements Joueur {

    public static final double SPEED = 0.2;
    private final Position pos;
    private final Equipe equipe;
    private Item inventaire;
    private final Jeu jeu;

    public JoueurImpl(int x, int y, Equipe equipe, Jeu jeu) throws RemoteException {
        this.pos = new PositionImpl(x, y);
        this.equipe = equipe;
        this.inventaire = null;
        this.equipe.ajouterJoueur(this);
        this.jeu = jeu;
    }

    @Override
    public void moveX(double x) throws RemoteException {
        pos.moveX(x);
    }

    @Override
    public void moveY(double y) throws RemoteException {
        pos.moveY(y);
    }

    @Override
    public Position getPos() throws RemoteException {
        return pos;
    }

    @Override
    public double getSpeed() throws RemoteException {
        return SPEED;
    }

    @Override
    public Equipe getEquipe() throws RemoteException {
        return this.equipe;
    }

    @Override
    public Item getItem() throws RemoteException {
        return this.inventaire;
    }

    @Override
    public Jeu getJeu() throws RemoteException {
        return this.jeu;
    }

    @Override
    public void viderInventaire() throws RemoteException {
        this.inventaire = null;
    }

    @Override
    public void prendreResource(Resource r) throws RemoteException {
        this.inventaire = r.getItem();
    }

    @Override
    public void interraction() throws RemoteException {
        if (inventaire == null) {
            jeu.veutRamasser(this);
        } else {
            jeu.veutDeposer(this);
        }
    }
}
