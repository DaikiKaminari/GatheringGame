package gatheringgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Jeu extends Remote {
    Joueur join() throws RemoteException;

    List<Joueur> getJoueurs() throws RemoteException;

    Usine getUsine() throws RemoteException;

    List<Resource> getResources() throws RemoteException;

    void ajouterResource(Resource resource) throws RemoteException;

    void retirerResource(Resource resource) throws RemoteException;

    boolean aCommence() throws RemoteException;

    int getNbJoueur() throws RemoteException;

    int getSecondesRestantes() throws RemoteException;

    Map<?, ?> getConfig() throws RemoteException;

    void finir() throws RemoteException;

    boolean estFini() throws RemoteException;

    Equipe equipeGagnante() throws RemoteException;

    boolean veutRamasser(Joueur j) throws RemoteException;

    boolean veutDeposer(Joueur j) throws RemoteException;

    void recommencer() throws RemoteException;

    List<Equipe> getEquipes() throws RemoteException;
}
