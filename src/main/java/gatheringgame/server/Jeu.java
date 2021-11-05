package gatheringgame.server;

import java.rmi.*;
import java.util.Collection;
import java.util.List;

public interface Jeu extends Remote {
    public Joueur join() throws RemoteException;
    public List<Joueur> getJoueurs() throws RemoteException;
    public Usine getUsine() throws RemoteException;
    public List<Resource> getResources() throws RemoteException;
    public void ajouterResource() throws RemoteException;
    public boolean aCommence() throws RemoteException;
    public int getNbJoueur() throws RemoteException;
    public int getSecondesRestantes() throws RemoteException;

    public void finir() throws RemoteException;
    public boolean estFini() throws RemoteException;
    public Equipe equipeGagnante() throws RemoteException;
}
