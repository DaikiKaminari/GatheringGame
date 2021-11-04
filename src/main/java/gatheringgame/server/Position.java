package gatheringgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Position extends Remote {
	int getX() throws RemoteException;
	int getY() throws RemoteException;
	void setX(int x) throws RemoteException;
	void setY(int y) throws RemoteException;
}
