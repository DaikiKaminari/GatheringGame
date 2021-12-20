package gatheringgame.server;

import gatheringgame.server.impl.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Joueur extends Remote {
    public void moveX(double x) throws RemoteException;
    public void moveY(double y) throws  RemoteException;
    public Position getPos() throws RemoteException;
    public double getSpeed() throws RemoteException;
    public Equipe getEquipe() throws RemoteException;
    public Item getItem() throws RemoteException;
    public Jeu getJeu() throws  RemoteException;
    public boolean estPret() throws RemoteException;
    public void setPret(boolean pret) throws RemoteException;

    public void viderInventaire() throws RemoteException;
    public void prendreResource(Resource r) throws RemoteException;
    public void interraction() throws RemoteException;

    public void resetPosition() throws RemoteException;
}
