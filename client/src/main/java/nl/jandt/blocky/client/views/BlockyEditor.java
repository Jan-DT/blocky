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
        FlatDarkLaf.setup();
//        FlatInspector.install( "ctrl shift alt" );

        JFrame frame = new JFrame();

        JMenuBar menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);

        frame.setSize(800,600);

        frame.setVisible(true);
    }
}
