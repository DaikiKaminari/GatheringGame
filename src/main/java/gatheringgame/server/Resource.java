package gatheringgame.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Resource extends Remote {
	String getName() throws RemoteException;
	Position getPosition() throws RemoteException;
}
