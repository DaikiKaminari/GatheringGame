package gatheringgame.server;

import gatheringgame.server.impl.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Usine extends Remote {
    public void satisfaireDemande(Joueur j) throws Exception, RemoteException;
    public List<Item> getDemande(Equipe e) throws RemoteException;
    public void ajouterEquipe(Equipe e) throws RemoteException;
    public Position getPosition() throws RemoteException;
    public List<Item> getDemande(int equipe) throws RemoteException;
}
