package gatheringgame.client;

import gatheringgame.server.Jeu;
import gatheringgame.server.Joueur;
import gatheringgame.server.Resource;
import gatheringgame.server.Usine;
import gatheringgame.server.impl.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.rmi.Remote;
import java.util.List;
import java.rmi.RemoteException;

public class Display extends Canvas {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private Jeu jeu;
    private BufferStrategy buffer;


    private Image usineSprite;
    private Image screwSprite;
    private Image boltSprite;
    private Image gearSprite;

    Display(Jeu j) {
        setSize(WIDTH, HEIGHT);
        this.setIgnoreRepaint(true);
        setVisible(true);

        jeu = j;

        usineSprite = new ImageIcon("sprites/factory.png").getImage();

        screwSprite = new ImageIcon("sprites/screw.png").getImage();
        boltSprite = new ImageIcon("sprites/bolt.png").getImage();
        gearSprite = new ImageIcon("sprites/gear.png").getImage();


    }

    public void makeBufferStrategy() {
        createBufferStrategy(2);
        buffer = this.getBufferStrategy();
    }


    public void render() throws RemoteException {
        Graphics g = buffer.getDrawGraphics();

        resetAffichage(g);


        /**
         * Affichage des élements du jeu
         */
        afficherUsine(g, this.jeu.getUsine());
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
        if(joueur.getEquipe().getNumero() == 1 ) {
            g.setColor(Color.blue);
        } else {
            g.setColor(Color.red);
        }
        g.drawRect((int)joueur.getX(), (int)joueur.getY(), 30, 30);
    }

    private void afficherUsine(Graphics g, Usine usine) throws RemoteException {
        g.drawImage(this.usineSprite, usine.getPosition().getX(), usine.getPosition().getY(), this);

        List<Item> demandeEquipeUn = usine.getDemande(1);
        List<Item> demandeEquipeDeux = usine.getDemande(2);

        int espaceDemandes = 40;
        int espaceObjets = 40;

        for(int i = 0; i < demandeEquipeUn.size(); i++) {
            Item item = demandeEquipeUn.get(i);
            switch(item) {
                case SCREW:
                    g.drawImage(this.screwSprite, usine.getPosition().getX() + i * espaceObjets, usine.getPosition().getY() - espaceDemandes, this);
                    break;
                case BOLT:
                    g.drawImage(this.boltSprite, usine.getPosition().getX() + i * espaceObjets, usine.getPosition().getY() - espaceDemandes, this);
                    break;
                case GEAR:
                    g.drawImage(this.gearSprite, usine.getPosition().getX() + i * espaceObjets, usine.getPosition().getY() - espaceDemandes, this);
                    break;

            }
        }
        for(int i = 0; i < demandeEquipeDeux.size(); i++) {
            Item item = demandeEquipeDeux.get(i);
            switch(item) {
                case SCREW:
                    g.drawImage(this.screwSprite, usine.getPosition().getX() + i * espaceObjets, usine.getPosition().getY() - 2 * espaceDemandes, this);
                    break;
                case BOLT:
                    g.drawImage(this.boltSprite, usine.getPosition().getX() + i * espaceObjets, usine.getPosition().getY() - 2 * espaceDemandes, this);
                    break;
                case GEAR:
                    g.drawImage(this.gearSprite, usine.getPosition().getX() + i * espaceObjets, usine.getPosition().getY() - 2 * espaceDemandes, this);
                    break;
            }
        }

    }

    private void afficherResources(List<Resource> resources) throws RemoteException {
        // TODO, afficher les ressoruces selon leurs labels, à la bonne position
    }
}
