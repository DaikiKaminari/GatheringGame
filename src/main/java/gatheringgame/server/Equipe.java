package gatheringgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Equipe extends Remote {
    public int getScore() throws RemoteException;
    public void incrScore() throws RemoteException;
    public boolean ajouterJoueur(Joueur j) throws RemoteException;
    public void enleverJoueur(Joueur j) throws RemoteException;
    public void resetScore() throws RemoteException;
    public int getNumero() throws RemoteException;
    public List<Joueur> getJoueurs() throws RemoteException;
}
