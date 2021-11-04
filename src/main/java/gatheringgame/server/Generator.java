package gatheringgame.server;

import java.rmi.RemoteException;

public interface Generator {
	Resource randomResource() throws RemoteException;
}
