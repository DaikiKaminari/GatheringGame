package gatheringgame.client;

import gatheringgame.server.*;
import gatheringgame.server.impl.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.rmi.RemoteException;
import java.util.List;

public class Display extends Canvas {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private final Jeu jeu;
    private final Joueur joueur;
    private final Image usineSprite;
    private final Image screwSprite;
    private final Image boltSprite;
    private final Image gearSprite;
    private BufferStrategy buffer;

    Display(Jeu j, Joueur joueur) {
        setSize(WIDTH, HEIGHT);
        this.setIgnoreRepaint(true);
        setVisible(true);

        jeu = j;
        this.joueur = joueur;

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

        if (!jeu.aCommence()) {
            this.afficherAttente(g);
        } else if (jeu.estFini()) {
            this.afficherResultat(g);
        } else {
            afficherUsine(g, this.jeu.getUsine());
            afficherJoueurs(g);
            afficherInventaire(g);
            afficherCompteARebours(g);
            synchronized (jeu.getResources()) {
                afficherRessources(g, jeu.getResources());
            }
            afficherScores(g);

        }

        g.setColor(Color.BLACK);

        g.dispose();
        buffer.show();
    }

    private void afficherResultat(Graphics g) throws RemoteException {
        g.setColor(Color.red);
        g.setFont(new Font("Purisa", Font.PLAIN, 23));
        Equipe gagnante = jeu.equipeGagnante();
        if (gagnante == null) {
            g.drawString("EGALITE", 250, 260);
        } else if (gagnante.getNumero() == 1) {
            g.drawString("L'EQUIPE GAGNANTE EST BLEUE AVEC " + gagnante.getScore() + " POINTS !", 250, 260);
        } else if (gagnante.getNumero() == 2) {
            g.drawString("L'EQUIPE GAGNANTE EST ROUGE AVEC " + gagnante.getScore() + " POINTS !", 250, 260);
        }

        int nbJoueurPret = 0;

        for (Joueur j : jeu.getJoueurs()) {
            if (j.estPret()) {
                nbJoueurPret = nbJoueurPret + 1;
            }
        }

        g.drawString("TOUCHE ESPACE POUR PRÊT. Joueurs prêts :  " + nbJoueurPret + "/" + jeu.getNbJoueur(), 250, 300);
    }

    private void afficherCompteARebours(Graphics g) throws RemoteException {
        g.setColor(Color.red);
        g.setFont(new Font("Purisa", Font.PLAIN, 23));
        g.drawString("secondes restantes : " + jeu.getSecondesRestantes(), 250, 50);
    }

    private void afficherInventaire(Graphics g) throws RemoteException {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Purisa", Font.PLAIN, 20));
        g.drawString("INVENTAIRE : ", 10, 575);

        if (joueur.getItem() != null) {
            switch (joueur.getItem()) {
                case SCREW:
                    g.drawImage(this.screwSprite, 150, 555, this);
                    break;
                case BOLT:
                    g.drawImage(this.boltSprite, 150, 555, this);
                    break;
                case GEAR:
                    g.drawImage(this.gearSprite, 150, 555, this);
                    break;
            }
        }
    }


    private void afficherAttente(Graphics g) throws RemoteException {
        g.setColor(Color.red);
        g.setFont(new Font("Purisa", Font.PLAIN, 23));
        g.drawString("ATTENTE DE JOUEURS : " + jeu.getNbJoueur() + " / " + Matchmaking.NB_MAX_JOUEUR, 250, 260);
    }

    private void resetAffichage(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    private void afficherScores(Graphics g) throws RemoteException {
        List<Equipe> equipes = jeu.getEquipes();

        //affichage score équipe 1
        g.setColor(Color.blue);
        g.setFont(new Font("Purisa", Font.PLAIN, 23));
        g.drawString("score équipe 1: " + equipes.get(0).getScore(), 0, 20);

        //affichage score équipe 2
        g.setColor(Color.red);
        g.setFont(new Font("Purisa", Font.PLAIN, 23));
        g.drawString("score équipe 2: " + equipes.get(1).getScore(), 250, 20);

    }


    private void afficherJoueurs(Graphics g) {
        try {
            List<Joueur> joueurs = jeu.getJoueurs();
            for (Joueur joueur : joueurs) {
                afficherJoueur(g, joueur);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void afficherJoueur(Graphics g, Joueur joueur) throws RemoteException {
        if (joueur.getEquipe().getNumero() == 1) {
            g.setColor(Color.blue);
        } else {
            g.setColor(Color.red);
        }
        int sizePlayer = (int) ((double) jeu.getConfig().get("sizePlayer"));
        g.drawRect((int) joueur.getPos().getX(), (int) joueur.getPos().getY(), sizePlayer, sizePlayer);
    }

    private void afficherUsine(Graphics g, Usine usine) throws RemoteException {
        g.drawImage(this.usineSprite, (int) usine.getPosition().getX(), (int) usine.getPosition().getY(), this);

        List<Item> demandeEquipeUn = usine.getDemande(1);
        List<Item> demandeEquipeDeux = usine.getDemande(2);

        int espaceDemandes = 40;
        int espaceObjets = 40;


        int demandeXPos = 650;
        int demandeYPos = 80;

        g.setColor(Color.BLUE);
        g.setFont(new Font("Purisa", Font.PLAIN, 10));
        g.drawString("demande bleue ", demandeXPos - 80, demandeYPos - espaceDemandes + 20);

        for (int i = 0; i < demandeEquipeUn.size(); i++) {
            Item item = demandeEquipeUn.get(i);
            switch (item) {
                case SCREW:
                    g.drawImage(this.screwSprite, demandeXPos + i * espaceObjets, demandeYPos - espaceDemandes, this);
                    break;
                case BOLT:
                    g.drawImage(this.boltSprite, demandeXPos + i * espaceObjets, demandeYPos - espaceDemandes, this);
                    break;
                case GEAR:
                    g.drawImage(this.gearSprite, demandeXPos + i * espaceObjets, demandeYPos - espaceDemandes, this);
                    break;
            }
        }

        g.setColor(Color.RED);
        g.setFont(new Font("Purisa", Font.PLAIN, 10));
        g.drawString("demande rouge ", demandeXPos - 80, demandeYPos - 2 * espaceDemandes + 20);
        for (int i = 0; i < demandeEquipeDeux.size(); i++) {
            Item item = demandeEquipeDeux.get(i);
            switch (item) {
                case SCREW:
                    g.drawImage(this.screwSprite, demandeXPos + i * espaceObjets, demandeYPos - 2 * espaceDemandes, this);
                    break;
                case BOLT:
                    g.drawImage(this.boltSprite, demandeXPos + i * espaceObjets, demandeYPos - 2 * espaceDemandes, this);
                    break;
                case GEAR:
                    g.drawImage(this.gearSprite, demandeXPos + i * espaceObjets, demandeYPos - 2 * espaceDemandes, this);
                    break;
            }
        }

    }

    private void afficherRessource(Graphics g, Resource resource) throws RemoteException {
        Item item = resource.getItem();
        switch (item) {
            case SCREW:
                g.drawImage(this.screwSprite, (int) resource.getPos().getX(), (int) resource.getPos().getY(), this);
                break;
            case BOLT:
                g.drawImage(this.boltSprite, (int) resource.getPos().getX(), (int) resource.getPos().getY(), this);
                break;
            case GEAR:
                g.drawImage(this.gearSprite, (int) resource.getPos().getX(), (int) resource.getPos().getY(), this);
                break;
        }
    }

    private void afficherRessources(Graphics g, List<Resource> resources) throws RemoteException {
        for (Resource r : resources) {
            afficherRessource(g, r);
        }
    }
}
