package gatheringgame.client;

import javax.swing.*;

public class Window {
    Window(Display d) {
        JFrame app = new JFrame();
        app.setVisible(true);
        app.setSize(Display.WIDTH, Display.HEIGHT);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setResizable(false);
        app.add(d);
        app.pack();
    }
}
