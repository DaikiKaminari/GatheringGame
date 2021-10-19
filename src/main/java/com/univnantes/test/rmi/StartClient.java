package com.univnantes.test.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class StartClient {
    public static void main(String[] args) {
        System.out.println("Lancement du client");
        try {
            Remote r = Naming.lookup(StartServer.URL + "/informationImpl");
            String s;
            if (r instanceof Information) {
                s = ((Information) r).getInformation();
                System.out.println(s);
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
