package gatheringgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Equipe extends Remote {
    int getScore() throws RemoteException;

    void incrScore() throws RemoteException;

    boolean ajouterJoueur(Joueur j) throws RemoteException;

    void enleverJoueur(Joueur j) throws RemoteException;

    void resetScore() throws RemoteException;

    int getNumero() throws RemoteException;

    List<Joueur> getJoueurs() throws RemoteException;
}
