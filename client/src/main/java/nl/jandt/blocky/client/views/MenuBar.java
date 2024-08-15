package nl.jandt.blocky.client.views;

import org.kordamp.ikonli.materialdesign2.*;

import javax.swing.*;
import javax.swing.event.MenuListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        final var fileMenu = fileMenu();
        this.add(fileMenu);

        final var editMenu = editMenu();
        this.add(editMenu);

        final var aboutMenu = aboutMenu();
        this.add(aboutMenu);
    }

    public JMenu fileMenu() {
        final var fileMenu = new JMenu("File");

        final var newProjectItem = new JMenuItem("New Project", Icons.menuBar(MaterialDesignF.FOLDER_MULTIPLE_PLUS_OUTLINE));
        fileMenu.add(newProjectItem);

        final var openProjectItem = new JMenuItem("Open from File", Icons.menuBar(MaterialDesignF.FOLDER_OPEN_OUTLINE));
        fileMenu.add(openProjectItem);

        final var openRecentProject = new JMenuItem("Open Recent");
        fileMenu.add(openRecentProject);

        final var closeProjectItem = new JMenuItem("Close Project");
        fileMenu.add(closeProjectItem);

        fileMenu.addSeparator();

        final var projectSettings = new JMenuItem("Project Settings", Icons.menuBar(MaterialDesignF.FOLDER_COG_OUTLINE));
        fileMenu.add(projectSettings);

        fileMenu.addSeparator();

        final var saveItem = new JMenuItem("Save", Icons.menuBar(MaterialDesignC.CONTENT_SAVE_OUTLINE));
        fileMenu.add(saveItem);

        final var saveAsItem = new JMenuItem("Save As", Icons.menuBar(MaterialDesignC.CONTENT_SAVE_EDIT_OUTLINE));
        fileMenu.add(saveAsItem);

        final var exportItem = new JMenuItem("Export", Icons.menuBar(MaterialDesignE.EXPORT_VARIANT));
        fileMenu.add(exportItem);

        final var vcsItem = new JMenuItem("Version Control", Icons.menuBar(MaterialDesignS.SOURCE_BRANCH));
        fileMenu.add(vcsItem);

        fileMenu.addSeparator();

        final var closeEditorItem = new JMenuItem("Exit");
        fileMenu.add(closeEditorItem);

        return fileMenu;
    }

    public JMenu editMenu() {
        final var editMenu = new JMenu("Edit");

        final var undoItem = new JMenuItem("Undo", Icons.menuBar(MaterialDesignU.UNDO));
        undoItem.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_DOWN_MASK));
        editMenu.add(undoItem);

        final var redoItem = new JMenuItem("Redo", Icons.menuBar(MaterialDesignR.REDO));
        redoItem.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        editMenu.add(redoItem);

        return editMenu;
    }

    public JMenu aboutMenu() {
        final var aboutMenu = new JMenu("About");

        final var helpItem = new JMenuItem("Community Support", Icons.menuBar(MaterialDesignH.HELP_CIRCLE_OUTLINE));
        aboutMenu.add(helpItem);

        final var wikiItem = new JMenuItem("Wiki", Icons.menuBar(MaterialDesignB.BOOK_OPEN_OUTLINE));
        aboutMenu.add(wikiItem);

        final var javaDocItem = new JMenuItem("JavaDoc", Icons.menuBar(MaterialDesignB.BOOKSHELF));
        aboutMenu.add(javaDocItem);

        aboutMenu.addSeparator();

        final var reportIssueItem = new JMenuItem("Report an Issue", Icons.menuBar(MaterialDesignB.BUG_OUTLINE));
        aboutMenu.add(reportIssueItem);

        final var contributeItem = new JMenuItem("Contribute", Icons.menuBar(MaterialDesignL.LIGHTBULB_OUTLINE));
        aboutMenu.add(contributeItem);

        final var licenseItem = new JMenuItem("License", Icons.menuBar(MaterialDesignL.LICENSE));
        aboutMenu.add(licenseItem);

        final var checkUpdateItem = new JMenuItem("Check for Updates");
        aboutMenu.add(checkUpdateItem);

        return aboutMenu;
    }
}
