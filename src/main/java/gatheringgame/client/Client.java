package gatheringgame.client;

import gatheringgame.server.Jeu;
import gatheringgame.server.Joueur;
import gatheringgame.server.Server;

import javax.naming.ldap.Control;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {


    private double elapsed;

    private Joueur joueur;

    private Jeu jeu;
    private ControleurJoueur ctrlJoueur;


    Client(Jeu jeu, Joueur joueur) {
        this.jeu = jeu;
        this.joueur = joueur;
        this.ctrlJoueur = new ControleurJoueur();
    }

    public static void main(String[] args) {
        try {
            Jeu jeu = (Jeu) Naming.lookup(Server.URL+"/jeuImpl");
            Joueur j = jeu.join();
            Display d = new Display(jeu);
            Window window = new Window(d);

            Client c = new Client(jeu, j);
            d.addKeyListener(c.getControleurJoueur());


            d.makeBufferStrategy(); // for buffer rendering

            double timePrev = System.currentTimeMillis();

            for(;;) {

                double timeNow = System.currentTimeMillis();
                c.setElapsedTime(timeNow - timePrev);

                d.render();
                c.updateGame();

                timePrev = timeNow;
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateGame() throws RemoteException {
        if(this.ctrlJoueur.getStatus(ControleurJoueur.Action.DROITE)) {
            joueur.moveX(joueur.getSpeed() * this.elapsed);
        } if(this.ctrlJoueur.getStatus(ControleurJoueur.Action.GAUCHE)) {
            joueur.moveX(- joueur.getSpeed() * this.elapsed);
        } if(this.ctrlJoueur.getStatus(ControleurJoueur.Action.HAUT)) {
            joueur.moveY(- joueur.getSpeed() * this.elapsed);
        } if(this.ctrlJoueur.getStatus(ControleurJoueur.Action.BAS)) {
            joueur.moveY(joueur.getSpeed() * this.elapsed);
        }
    }

    public ControleurJoueur getControleurJoueur() {
        return this.ctrlJoueur;
    }

    private void setElapsedTime(double elapsed) {
        this.elapsed = elapsed;
    }
}
