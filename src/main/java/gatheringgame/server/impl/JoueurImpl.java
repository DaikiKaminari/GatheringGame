package gatheringgame.server.impl;

import gatheringgame.server.Joueur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class JoueurImpl extends UnicastRemoteObject implements Joueur {

    private int posX;
    private int posY;

    public JoueurImpl(int x, int y) throws RemoteException {
        this.posX = x;
        this.posY = y;
    }

    @Override
    public void moveX(int x) throws RemoteException {
        this.posX+=x;
    }

    @Override
    public void moveY(int y) throws RemoteException {
        this.posY+=y;
    }

    @Override
    public int getX() throws RemoteException {
        return posX;
    }

    @Override
    public int getY() throws RemoteException {
        return posY;
    }
}
