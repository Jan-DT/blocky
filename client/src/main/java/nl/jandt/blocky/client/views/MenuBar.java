package nl.jandt.blocky.client.views;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.ui.FlatBorder;
import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.formdev.flatlaf.ui.FlatEmptyBorder;
import com.formdev.flatlaf.ui.FlatMenuBarBorder;
import nl.jandt.blocky.client.views.util.Icons;
import org.kordamp.ikonli.materialdesign2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuBar extends JMenuBar {
    private static final Logger log = LoggerFactory.getLogger(MenuBar.class);

    public MenuBar() {
        final var fileMenu = fileMenu();
        fileMenu.setMnemonic('f');
        this.add(fileMenu);

        final var editMenu = editMenu();
        editMenu.setMnemonic('e');
        this.add(editMenu);

        final var aboutMenu = aboutMenu();
        aboutMenu.setMnemonic('a');
        this.add(aboutMenu);

        this.add(Box.createHorizontalGlue());

        final var settingsIcon = new FlatButton();
        settingsIcon.setButtonType(FlatButton.ButtonType.toolBarButton);
        settingsIcon.setIcon(Icons.menuBarIcon(MaterialDesignC.COG_OUTLINE));
        settingsIcon.setSelected(false);
        settingsIcon.setOutline(null);
        settingsIcon.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(settingsIcon);

        setBorder(new FlatMenuBarBorder());
    }

    public JMenu fileMenu() {
        final var fileMenu = new JMenu("File");

        final var newProjectItem = new JMenuItem("New Project", Icons.menuIcon(MaterialDesignF.FOLDER_MULTIPLE_PLUS_OUTLINE));
        fileMenu.add(newProjectItem);

        final var openProjectItem = new JMenuItem("Open from File", Icons.menuIcon(MaterialDesignF.FOLDER_OPEN_OUTLINE));
        fileMenu.add(openProjectItem);

        final var openRecentItem = new JMenu("Open Recent");
        openRecentItem.add(new JMenuItem("testfile 1"));
        fileMenu.add(openRecentItem);

        final var closeProjectItem = new JMenuItem("Close Project");
        fileMenu.add(closeProjectItem);

        fileMenu.addSeparator();

        final var projectSettings = new JMenuItem("Project Settings", Icons.menuIcon(MaterialDesignF.FOLDER_COG_OUTLINE));
        fileMenu.add(projectSettings);

        fileMenu.addSeparator();

        final var saveItem = new JMenuItem("Save", Icons.menuIcon(MaterialDesignC.CONTENT_SAVE_OUTLINE));
        fileMenu.add(saveItem);

        final var saveAsItem = new JMenuItem("Save As", Icons.menuIcon(MaterialDesignC.CONTENT_SAVE_EDIT_OUTLINE));
        fileMenu.add(saveAsItem);

        final var exportItem = new JMenuItem("Export", Icons.menuIcon(MaterialDesignE.EXPORT_VARIANT));
        fileMenu.add(exportItem);

        final var vcsItem = new JMenuItem("Version Control", Icons.menuIcon(MaterialDesignS.SOURCE_BRANCH));
        fileMenu.add(vcsItem);

        fileMenu.addSeparator();

        final var closeEditorItem = new JMenuItem("Exit");
        fileMenu.add(closeEditorItem);

        return fileMenu;
    }

    public JMenu editMenu() {
        final var editMenu = new JMenu("Edit");

        final var undoItem = new JMenuItem("Undo", Icons.menuIcon(MaterialDesignU.UNDO));
        undoItem.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_DOWN_MASK));
        editMenu.add(undoItem);

        final var redoItem = new JMenuItem("Redo", Icons.menuIcon(MaterialDesignR.REDO));
        redoItem.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        editMenu.add(redoItem);

        return editMenu;
    }

    public JMenu aboutMenu() {
        final var aboutMenu = new JMenu("About");

        final var helpItem = new JMenuItem("Community Support", Icons.menuIcon(MaterialDesignH.HELP_CIRCLE_OUTLINE));
        aboutMenu.add(helpItem);

        final var wikiItem = new JMenuItem("Wiki", Icons.menuIcon(MaterialDesignB.BOOK_OPEN_OUTLINE));
        aboutMenu.add(wikiItem);

        final var javaDocItem = new JMenuItem("API Documentation", Icons.menuIcon(MaterialDesignB.BOOKSHELF));
        aboutMenu.add(javaDocItem);

        aboutMenu.addSeparator();

        final var reportIssueItem = new JMenuItem("Report an Issue", Icons.menuIcon(MaterialDesignB.BUG_OUTLINE));
        aboutMenu.add(reportIssueItem);

        final var contributeItem = new JMenuItem("Contribute", Icons.menuIcon(MaterialDesignL.LIGHTBULB_OUTLINE));
        aboutMenu.add(contributeItem);

        final var licenseItem = new JMenuItem("License", Icons.menuIcon(MaterialDesignL.LICENSE));
        aboutMenu.add(licenseItem);

        final var checkUpdateItem = new JMenuItem("Check for Updates");
        aboutMenu.add(checkUpdateItem);

        return aboutMenu;
    }
}
