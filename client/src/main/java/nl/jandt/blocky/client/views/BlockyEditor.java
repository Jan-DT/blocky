package nl.jandt.blocky.client.views;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import org.kordamp.ikonli.Ikonli;
import org.kordamp.ikonli.materialdesign2.MaterialDesignM;

import javax.swing.*;
import java.awt.*;

public class BlockyEditor {
    public static void init() {

        SwingUtilities.invokeLater(() -> {
            FlatDarkLaf.setup();
//            FlatInspector.install( "ctrl shift alt" );

            new MainWindow().setVisible(true);
        });

    }
}
