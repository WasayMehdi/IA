package gui.impl;

import configurations.Configurations;
import configurations.Resource;
import gui.Gui;
import gui.IPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DirectionPanel extends IPanel {
    public static final int ID = 1;

    //shows directions, loaded & displayed as html
    public DirectionPanel(final PropertyChangeListener listener) {
        super(listener, ID);

        final JEditorPane area = new JEditorPane();
        area.setContentType("text/html");

        //load all lines from directions.html
        try {
            final java.util.List<String> list = Files.readAllLines(Paths.get(new File(Configurations.BACK + "/data/directions.html").toURI()), Charset.defaultCharset());
            final StringBuilder builder = new StringBuilder();
            for (final String line : list)
                builder.append(line);
            area.setText(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //display text
        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}
