package gatheringgame.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ControleurJoueur implements KeyListener {

    private final Map<Action, Boolean> actions;
    private final Map<Integer, Action> bind;

    ControleurJoueur() {
        this.actions = new EnumMap<>(Action.class);
        for(Action action : Action.values()) {
            this.actions.put(action, false);
        }

        this.bind = new HashMap<>();

        this.bind.put(KeyEvent.VK_UP, Action.HAUT);
        this.bind.put(KeyEvent.VK_DOWN, Action.BAS);
        this.bind.put(KeyEvent.VK_LEFT, Action.GAUCHE);
        this.bind.put(KeyEvent.VK_RIGHT, Action.DROITE);
        this.bind.put(KeyEvent.VK_SPACE, Action.INTERRACTION);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Auto Generated
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        Action action = this.bind.get(key);
        actions.put(action, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        Action action = this.bind.get(key);
        actions.put(action, false);
    }

    public boolean getStatus(Action e) {
        return this.actions.get(e);
    }

    public enum Action {
        HAUT,
        BAS,
        GAUCHE,
        DROITE,
        INTERRACTION
    }
}
