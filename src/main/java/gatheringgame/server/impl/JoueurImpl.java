package gatheringgame.server.impl;

import gatheringgame.server.Joueur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class JoueurImpl extends UnicastRemoteObject implements Joueur {

    public static final double SPEED = 0.2;
    private double posX;
    private double posY;

    public JoueurImpl(int x, int y) throws RemoteException {
        this.posX = x;
        this.posY = y;
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
}
