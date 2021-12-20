package gatheringgame.server;

import gatheringgame.server.impl.Item;

import java.rmi.RemoteException;

public interface Generator extends Runnable {
    Resource randomResource() throws RemoteException;

    Resource pseudoRandomResource(Item item) throws RemoteException;

    void run();
}
