package gatheringgame.server.impl;

import gatheringgame.server.Equipe;
import gatheringgame.server.Joueur;
import gatheringgame.server.Resource;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class JoueurImpl extends UnicastRemoteObject implements Joueur {

    public static final double SPEED = 0.2;
    private double posX;
    private double posY;
    private Equipe equipe;
    private Item inventaire;

    public JoueurImpl(int x, int y, Equipe equipe) throws RemoteException {
        this.posX = x;
        this.posY = y;
        this.equipe = equipe;
        this.inventaire = null;
        equipe.ajouterJoueur(this);
    }

    @Override
    public void moveX(double x) throws RemoteException {
        this.posX+=x;
    }

    @Override
    public void moveY(double y) throws RemoteException {
        this.posY+=y;
    }

    @Override
    public double getX() throws RemoteException {
        return posX;
    }

    @Override
    public double getY() throws RemoteException {
        return posY;
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
    public void viderInventaire() throws RemoteException {
        this.inventaire = null;
    }

    @Override
    public void prendreResource(Resource r) throws RemoteException {
        this.inventaire = r.getItem();
    }
}
