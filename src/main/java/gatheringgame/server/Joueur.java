package gatheringgame.server;

import gatheringgame.server.impl.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Joueur extends Remote {
    void moveX(double x) throws RemoteException;

    void moveY(double y) throws RemoteException;

    Position getPos() throws RemoteException;

    double getSpeed() throws RemoteException;

    Equipe getEquipe() throws RemoteException;

    Item getItem() throws RemoteException;

    Jeu getJeu() throws RemoteException;

    boolean estPret() throws RemoteException;

    void setPret(boolean pret) throws RemoteException;

    void viderInventaire() throws RemoteException;

    void prendreResource(Resource r) throws RemoteException;

    void interraction() throws RemoteException;

    void resetPosition() throws RemoteException;
}
