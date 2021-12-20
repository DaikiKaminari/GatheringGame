package gatheringgame.server;

import java.rmi.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Jeu extends Remote {
    public Joueur join() throws RemoteException;
    public List<Joueur> getJoueurs() throws RemoteException;
    public Usine getUsine() throws RemoteException;
    public List<Resource> getResources() throws RemoteException;
    public void ajouterResource(Resource resource) throws RemoteException;
    public void retirerResource(Resource resource) throws RemoteException;
    public boolean aCommence() throws RemoteException;
    public int getNbJoueur() throws RemoteException;
    public int getSecondesRestantes() throws RemoteException;
    public Map<?, ?> getConfig() throws RemoteException;

    public void finir() throws RemoteException;
    public boolean estFini() throws RemoteException;
    public Equipe equipeGagnante() throws RemoteException;
    public boolean veutRamasser(Joueur j) throws RemoteException;
    public boolean veutDeposer(Joueur j) throws RemoteException;

    public void recommencer() throws RemoteException;

    public List<Equipe> getEquipes() throws RemoteException;
}
