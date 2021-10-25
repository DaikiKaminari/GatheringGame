package gatheringgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Joueur extends Remote {
    public void moveX(int x) throws RemoteException;
    public void moveY(int y) throws  RemoteException;
    public int getX() throws  RemoteException;
    public int getY() throws RemoteException;
}
