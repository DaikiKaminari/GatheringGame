package gatheringgame.client;

import gatheringgame.server.Jeu;
import gatheringgame.server.Joueur;
import gatheringgame.server.Matchmaking;
import gatheringgame.server.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {


    private final Joueur joueur;
    private final Jeu jeu;
    private final ControleurJoueur ctrlJoueur;
    private double elapsed;


    Client(Jeu jeu, Joueur joueur) {
        this.jeu = jeu;
        this.joueur = joueur;
        this.ctrlJoueur = new ControleurJoueur();
    }

    public static void main(String[] args) {
        try {
            Matchmaking matchmaking = (Matchmaking) Naming.lookup(Server.URL + "/jeuImpl");
            Jeu jeu = matchmaking.getJeu();
            Joueur j = jeu.join(); // 1er joueur
            jeu.join(); // 2ème joueur TEST
            jeu.join(); // 3ème joueur TEST
            jeu.join(); // 4ème joueur TEST

            while (j == null) { // Si notre joueur est réfusé car plus de place, alors on essaie à nouveau de trouver une nouvelle partie et de la rejoindre.
                jeu = matchmaking.getJeu();
                j = jeu.join();
            }

            Display d = new Display(jeu, j);
            Window window = new Window(d);

            Client c = new Client(jeu, j);
            d.addKeyListener(c.getControleurJoueur());


            d.makeBufferStrategy(); // for buffer rendering

            double timePrev = System.currentTimeMillis();

            for (; ; ) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateGame() throws RemoteException {

        if (!jeu.aCommence()) // ne rien faire si la partie n'a pas commencé
            return;

        if (jeu.estFini()) {
            if (this.ctrlJoueur.getStatus(ControleurJoueur.Action.INTERRACTION)) {
                joueur.setPret(true);
            }
            return;
        }

        if (this.ctrlJoueur.getStatus(ControleurJoueur.Action.DROITE)) {
            joueur.moveX(joueur.getSpeed() * this.elapsed);
        }
        if (this.ctrlJoueur.getStatus(ControleurJoueur.Action.GAUCHE)) {
            joueur.moveX(-joueur.getSpeed() * this.elapsed);
        }
        if (this.ctrlJoueur.getStatus(ControleurJoueur.Action.HAUT)) {
            joueur.moveY(-joueur.getSpeed() * this.elapsed);
        }
        if (this.ctrlJoueur.getStatus(ControleurJoueur.Action.BAS)) {
            joueur.moveY(joueur.getSpeed() * this.elapsed);
        }
        if (this.ctrlJoueur.getStatus(ControleurJoueur.Action.INTERRACTION)) {
            ctrlJoueur.setStatus(ControleurJoueur.Action.INTERRACTION, false); // pour éviter que l'action se répète plusieurs fois.
            joueur.interraction();
        }
    }

    public ControleurJoueur getControleurJoueur() {
        return this.ctrlJoueur;
    }

    private void setElapsedTime(double elapsed) {
        this.elapsed = elapsed;
    }
}
