package gatheringgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Joueur extends Remote {
    public void moveX(double x) throws RemoteException;
    public void moveY(double y) throws  RemoteException;
    public double getX() throws  RemoteException;
    public double getY() throws RemoteException;
    public double getSpeed() throws RemoteException;
}
