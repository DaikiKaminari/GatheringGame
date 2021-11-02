package gatheringgame.client;

import gatheringgame.server.Jeu;
import gatheringgame.server.Joueur;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.rmi.RemoteException;

public class Display extends Canvas {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private Jeu jeu;
    private BufferStrategy buffer;

    Display(Jeu j) {
        setSize(WIDTH, HEIGHT);
        this.setIgnoreRepaint(true);
        setVisible(true);

        jeu = j;


    }

    public void makeBufferStrategy() {
        createBufferStrategy(2);
        buffer = this.getBufferStrategy();
    }


    public void render() {
        Graphics g = buffer.getDrawGraphics();

        resetAffichage(g);
        afficherJoueurs(g);
        g.setColor(Color.BLACK);

        g.dispose();
        buffer.show();
    }

    private void resetAffichage(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0, WIDTH, HEIGHT);
    }


    private void afficherJoueurs(Graphics g) {
        try {
            List<Joueur> joueurs = jeu.getJoueurs();
            for(Joueur joueur : joueurs) {
                afficherJoueur(g, joueur);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void afficherJoueur(Graphics g, Joueur joueur) throws RemoteException {
        g.setColor(Color.blue);
        g.drawRect((int)joueur.getX(), (int)joueur.getY(), 30, 30);
    }
}
