package com.univnantes.test.rmi;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class StartServer {
    private static final int PORT = 8080;
    protected static final String URL = "rmi://localhost:" + PORT;
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(PORT);
            InformationImpl informationImpl = new InformationImpl();
            System.out.println("Enregistrement de l'objet avec l'url : " + URL + "/informationImpl");
            Naming.rebind(URL + "/informationImpl", informationImpl);

            System.out.println("Serveur lancé");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
