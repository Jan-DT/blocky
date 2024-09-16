package nl.jandt.blocky.client.editor.views.util;


import org.jetbrains.annotations.NotNull;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class Icons {
    public static @NotNull Icon menuIcon(Ikon ikon) {
        final var icon = FontIcon.of(ikon);
        icon.setIconColor(Color.LIGHT_GRAY);
        icon.setIconSize(15);
        return icon;
    }

    public static @NotNull Icon menuBarIcon(Ikon ikon) {
        final var icon = FontIcon.of(ikon);
        icon.setIconColor(Color.LIGHT_GRAY);
        icon.setIconSize(16);
        return icon;
    }
}
