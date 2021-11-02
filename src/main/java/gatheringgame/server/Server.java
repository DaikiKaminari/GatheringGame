package gatheringgame.server;

import gatheringgame.server.impl.JeuImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
    private static final int PORT = 8080;
    public static final String URL = "rmi://localhost:" + PORT;
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(PORT);
            JeuImpl jeu = new JeuImpl();
            System.out.println("Enregistrement de l'objet avec l'url : " + URL + "/jeuImpl");
            Naming.rebind(URL + "/jeuImpl", jeu);

            System.out.println("Serveur lancé");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
