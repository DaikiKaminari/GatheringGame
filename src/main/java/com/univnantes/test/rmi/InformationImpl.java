package com.univnantes.test.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class InformationImpl extends UnicastRemoteObject implements Information {
    private static final long serialVersionUID = 2674880711467464646L;

    protected InformationImpl() throws RemoteException {
        super();
    }

    public String getInformation() throws RemoteException {
        System.out.println("Invocation de la méthode getInformation()");
        return "bonjour";
    }
}
