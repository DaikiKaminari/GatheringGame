package gatheringgame.server;

import gatheringgame.server.impl.Item;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Resource extends Remote {
	String getName() throws RemoteException;
	Position getPosition() throws RemoteException;
	Item getItem() throws RemoteException;
}
