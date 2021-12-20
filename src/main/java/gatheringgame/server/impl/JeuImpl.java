package gatheringgame.server.impl;

import com.google.gson.Gson;
import gatheringgame.server.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class JeuImpl extends UnicastRemoteObject implements Jeu {

    private int nbJoueur;
    private Map<Integer, Joueur> joueurs;
    private Equipe equipeUn;
    private Equipe equipeDeux;
    private final Position minPos;
    private final Position maxPos;
    private Usine usine;
    private final List<Resource> ressources;
    private boolean started;
    private boolean isFinished;
    private StoppableCountdown countdown;
    private final GeneratorImpl resourceGenerator;

    private static Map<?, ?> config;

    public JeuImpl() throws Exception {
        config = new Gson().fromJson(
                Files.newBufferedReader(Paths.get("src/main/java/gatheringgame/config.json")),
                Map.class
        );

        nbJoueur = 0;
        joueurs = new HashMap<Integer, Joueur>();
        equipeUn = new EquipeImpl(1);
        equipeDeux = new EquipeImpl(2);
        minPos = new PositionImpl((double)config.get("mapMinX"), (double)config.get("mapMinY"));
        maxPos = new PositionImpl((double)config.get("mapMaxX"), (double)config.get("mapMaxY"));
        ressources = new ArrayList<>();
        usine = new UsineImpl(this);
        usine.ajouterEquipe(equipeUn);
        usine.ajouterEquipe(equipeDeux);
        countdown = new StoppableCountdownImpl(60, this); // 2 minutes
        resourceGenerator = new GeneratorImpl(this, minPos, maxPos);

        this.started = false;
        this.isFinished = false;

        // this.commenceJeu(); // Pour débugger
    }

    @Override
    public synchronized Joueur join() throws RemoteException {

        if(nbJoueur==Matchmaking.NB_MAX_JOUEUR)
            return null;

        Joueur j = new JoueurImpl(50, 50, nbJoueur % 2 == 0 ? equipeUn : equipeDeux, this);
        joueurs.put(nbJoueur, j);
        nbJoueur++;

        if(nbJoueur == Matchmaking.NB_MAX_JOUEUR) {
            this.commenceJeu();
        }

        return j;
    }

    @Override
    public List<Joueur> getJoueurs() throws RemoteException {
        return new ArrayList<>(joueurs.values());
    }

    @Override
    public Usine getUsine() throws RemoteException {
        return this.usine;
    }

    @Override
    public List<Resource> getResources() throws RemoteException {
        return ressources;
    }

    @Override
    public void ajouterResource(Resource resource) throws RemoteException {
        synchronized (ressources) {
            this.ressources.add(resource);
        }
    }

    @Override
    public void retirerResource(Resource resource) throws RemoteException {
        synchronized (ressources) {
            this.ressources.remove(resource);
            // notifyAll();
        }
    }

    @Override
    public boolean aCommence() throws RemoteException {
        return this.started;
    }

    @Override
    public int getNbJoueur() throws RemoteException {
        return this.nbJoueur;
    }

    @Override
    public int getSecondesRestantes() throws RemoteException {
        return this.countdown.getSecondesRestantes();
    }

    @Override
    public Map<?, ?> getConfig() throws RemoteException {
        return config;
    }

    @Override
    public void finir() {
        this.isFinished = true;
    }

    @Override
    public boolean estFini() throws RemoteException {
        return this.isFinished;
    }

    @Override
    public Equipe equipeGagnante() throws RemoteException {
        if(equipeUn.getScore() > equipeDeux.getScore()) {
            return equipeUn;
        } else {
            if(equipeUn.getScore() == equipeDeux.getScore()) {
                return null;
            } else {
                return equipeDeux;
            }
        }
    }

    private void commenceJeu() {
        this.started = true;
        resourceGenerator.start();
        this.countdown.start();
    }

    /**
     * Le joueur tente de ramasser la ressource sur laquelle il se trouve
     * @param j
     * @return true si il a réussi à prendre une ressource, false sinon
     * @throws RemoteException
     */
    @Override
    public boolean veutRamasser(Joueur j) throws RemoteException {
        double maxRadiusPlayerPickupResource = (double) config.get("radiusPlayer");
        synchronized (ressources) {
            for (Resource r : ressources) {
                if (Position.isOverlapping(j.getPos(), r.getPos(), maxRadiusPlayerPickupResource)) {
                    j.prendreResource(r);
                    retirerResource(r);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Le joueur tente de déposer la ressource qu'il a dans son inventaire, soit à l'usine, soit au sol.
     * @param j Joueur souhaitant déposer la ressource
     * @return true si l'objet a bien été déposé, false sinon
     * @throws Exception
     */

    @Override
    public boolean veutDeposer(Joueur j) throws RemoteException {
        double maxRadiusPlayerPickupResource = (double) config.get("radiusPlayer");
        if (j.getItem() == null) {
            return false;
        }
        if (Position.isOverlapping(j.getPos(), usine.getPosition(), maxRadiusPlayerPickupResource)) {
            // tente de donner l'item à l'usine pour compléter la tâche
            return usine.satisfaireDemande(j);
        } else {
            // relâche l'item au sol
            ajouterResource(new ResourceImpl(j.getItem(), j.getPos().clonePos()));
            j.viderInventaire();
            return true;
        }
    }

    @Override
    public List<Equipe> getEquipes() throws RemoteException {
        LinkedList<Equipe> equipes = new LinkedList<Equipe>();
        equipes.add(equipeUn);
        equipes.add(equipeDeux);
        return equipes;
    }
}
