package gatheringgame.server;

import gatheringgame.server.impl.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Usine extends Remote {
    boolean satisfaireDemande(Joueur j) throws RemoteException;

    List<Item> getDemande(Equipe e) throws RemoteException;

    void ajouterEquipe(Equipe e) throws RemoteException;

    Position getPosition() throws RemoteException;

    List<Item> getDemande(int equipe) throws RemoteException;
}
