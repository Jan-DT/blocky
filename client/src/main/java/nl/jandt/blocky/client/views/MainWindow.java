package nl.jandt.blocky.client.views;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setSize(800, 600);
        setLayout(new BorderLayout());

        setJMenuBar(new MenuBar());

        final var footer = new FooterBar();
        footer.setPreferredSize(new Dimension(this.getWidth(), 25));
        add(footer, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
