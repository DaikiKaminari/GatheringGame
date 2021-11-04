package gatheringgame.server.impl;

import gatheringgame.server.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsineImpl extends UnicastRemoteObject implements Usine {


    public static final int NB_RESOURCE_DEMANDE = 3;
    public static final int DEFAULT_X_POS = 400;
    public static final int DEFAULT_Y_POS = 300;

    Map<Equipe, List<Item>> demandes;
    Position position;
    Jeu jeu;

    UsineImpl(Jeu jeu) throws RemoteException {
        demandes = new HashMap<>();
        this.jeu = jeu;
        position = new PositionImpl(DEFAULT_X_POS, DEFAULT_Y_POS);
    }


    /**
     *
     * satisfait une demande d'un objet lorsqu'un joueur transporte un objet.
     * Si le joueur n'a pas d'objet, la méthode ne fait rien.
     * Si le joueur a un objet et qu'il correspond à la demande, alors le joueur donne son objet à l'usine. Si la demande est satisfaite, l'équipe gagne un point
     *
     * @param j
     * @throws Exception
     * @throws RemoteException
     */
    @Override
    public synchronized void satisfaireDemande(Joueur j) throws Exception, RemoteException {
        Equipe equipe = j.getEquipe();
        Item item = j.getItem();
        List<Item> demande = this.getDemande(equipe);

        if(item == null)
            return;

        // Si l'objet correspond bien au premier objet demandé, alors il faut enlever l'objet de la demande et vider l'inventaire du joueur
        if(demande.get(0).getName().equals(item.getName())) {
            demande.remove(0);
            j.viderInventaire();



            // Si la demande est entièrement satisfaite, incrémenter le score de l'équipe du joueur, et génerer une nouvelle demande pour l'équipe
            if(demande.isEmpty()) {
                equipe.incrScore();
                this.demandes.put(equipe, this.genererDemande());
            }
        }

    }

    @Override
    public List<Item> getDemande(Equipe e) throws RemoteException {
        return this.demandes.get(e);
    }

    @Override
    public List<Item> getDemande(int equipe) throws RemoteException {
        for(Equipe e : this.demandes.keySet()){
            if(e.getNumero() == equipe) {
                return this.getDemande(e);
            }
        }
        return null;
    }

    @Override
    public synchronized void ajouterEquipe(Equipe e) throws RemoteException {
        this.demandes.put(e, this.genererDemande());
    }

    private List<Item> genererDemande() {
        List<Item> demande = new ArrayList<>(NB_RESOURCE_DEMANDE);
        for(int i = 0; i < NB_RESOURCE_DEMANDE; i++) {
            demande.add(Item.randomItem());
        }

        return demande;
    }


    @Override
    public Position getPosition() throws RemoteException {
        return this.position;
    }
}
