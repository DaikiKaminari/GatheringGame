package gatheringgame.client;

import gatheringgame.server.Jeu;
import gatheringgame.server.Joueur;
import gatheringgame.server.Server;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) {
        try {
            Jeu jeu = (Jeu) Naming.lookup(Server.URL+"/jeuImpl");
            Joueur j = jeu.join();
            Display d = new Display(jeu);
            Window window = new Window(d);
            d.makeBufferStrategy(); // for buffer rendering

            for(;;) {
                d.render();
                j.moveX(1);
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
