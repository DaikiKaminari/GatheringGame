package gatheringgame.server.impl;

import gatheringgame.server.Generator;
import gatheringgame.server.Jeu;
import gatheringgame.server.Position;
import gatheringgame.server.Resource;
import gatheringgame.server.util.Rand;

import java.rmi.RemoteException;

public class GeneratorImpl extends Thread implements Generator {
    Jeu jeu;
    Position minPos;
    Position maxPos;
    Rand rand;

    public GeneratorImpl(Jeu jeu, Position minPos, Position maxPos) {
        this.jeu = jeu;
        this.minPos = minPos;
        this.maxPos = maxPos;
        this.rand = new Rand();
    }

    public GeneratorImpl(Jeu jeu, Position minPos, Position maxPos, Rand rand) {
        this.jeu = jeu;
        this.minPos = minPos;
        this.maxPos = maxPos;
        this.rand = rand;
    }

    @Override
    public Resource randomResource() throws RemoteException {
        Position pos = new PositionImpl(
                rand.randomInt((int) minPos.getX(), (int) maxPos.getY()),
                rand.randomInt((int) minPos.getY(), (int) maxPos.getY())
        );
        return (new ResourceImpl(Item.randomItem(), pos));
    }

    @Override
    public Resource pseudoRandomResource(Item item) throws RemoteException {
        Position pos = new PositionImpl(
                rand.randomInt((int) minPos.getX(), (int) maxPos.getY()),
                rand.randomInt((int) minPos.getY(), (int) maxPos.getY())
        );
        return (new ResourceImpl(item, pos));
    }

    @Override
    public void run() {
        try {
            final int maxResourceEachType = (int) ((double) jeu.getConfig().get("maxResourcesEachType"));
            while (!this.isInterrupted()) {
                boolean isFull;
                synchronized (jeu.getResources()) {
                    isFull = maxResourceEachType * Item.SIZE <= jeu.getResources().size();
                    if (!isFull) {
                        Item it = Resource.leastOccurrenceItem(jeu.getResources());
                        jeu.ajouterResource(pseudoRandomResource(it));
                    }
                }
                if (isFull) {
                    synchronized (this) {
                        wait();
                    }
                }
            }
        } catch (RemoteException | InterruptedException e) {
            // (RemoteException) : N'est pas censé arriver, car c'est une méthode exécutée uniquement côté serveur
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }


}
