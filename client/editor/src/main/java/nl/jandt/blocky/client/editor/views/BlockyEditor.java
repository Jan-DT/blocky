package nl.jandt.blocky.client.editor.views;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class BlockyEditor {
    public static void init() {

        SwingUtilities.invokeLater(() -> {
            FlatDarkLaf.setup();
//            FlatInspector.install( "ctrl shift alt" );

            new MainWindow().setVisible(true);
        });

    }
}
