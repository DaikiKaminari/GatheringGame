package gatheringgame.server.impl;

import gatheringgame.server.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class JoueurImpl extends UnicastRemoteObject implements Joueur {

    public static final double SPEED = 0.2;
    private final Position defaultPos;
    private final Equipe equipe;
    private final Jeu jeu;
    private Position pos;
    private Item inventaire;
    private boolean pret;

    public JoueurImpl(int x, int y, Equipe equipe, Jeu jeu) throws RemoteException {
        this.defaultPos = new PositionImpl(x, y);
        this.pos = defaultPos.clonePos();
        this.pret = false;
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
    public boolean estPret() throws RemoteException {
        return pret;
    }

    @Override
    public void setPret(boolean pret) throws RemoteException {
        this.pret = pret;
        jeu.recommencer();
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

    @Override
    public void resetPosition() throws RemoteException {
        this.pos = defaultPos.clonePos();
    }
}
