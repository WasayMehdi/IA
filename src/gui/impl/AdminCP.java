package gui.impl;

import configurations.Configurations;
import configurations.Resource;
import gui.Gui;
import gui.IPanel;
import gui.impl.admincp.QuestionDisplayPanel;
import test.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: Wasay
 * Date: 1/26/15
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdminCP extends IPanel {

    public static final int ID = 0;

    private static final Configurations config = Configurations.getConfig();

    private static final String PASSWORD = config.retreive("password");

    public AdminCP(final PropertyChangeListener listener) {
        super(listener, ID);

        final JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, 1));
        //display who the email is being sent to and textbox to edit
        northPanel.add(new LabelTextBox("Email sent from: ", "email"));

        //display the email password and ability to edit
        northPanel.add(new LabelTextBox("Email password: ", "emailp"));

        //send to email
        northPanel.add(new LabelTextBox("Email recieved by: ", "emailreceiver"));

        northPanel.add(new LabelTextBox("Question Amount:", "questionamount"));

        northPanel.add(new LabelTextBox("Time (seconds): ", "testtime"));

        northPanel.add(new LabelTextBox("AdminCP Password", "password"));

        add(northPanel, BorderLayout.NORTH);
        add(new QuestionDisplayPanel(), BorderLayout.CENTER);



    }

    @Override
    public void show() {
        final String password = JOptionPane.showInputDialog(this, "Enter the password", "Security Check", JOptionPane.WARNING_MESSAGE);
        if(!password.equals("hello")) {
            JOptionPane.showMessageDialog(this, "Invalid password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        super.show();
    }

    //a component which will have one label, one text box and a reference to a "key" which will be used to replace a part of the map
    private static final class LabelTextBox extends JPanel{

        private final String key;

        public LabelTextBox(final String label_text, final String key) {
            setLayout(new BorderLayout());
            this.key = key;

            final JLabel label = new JLabel(label_text);

            final JTextField field = new JTextField(config.retreive(key));

            add(label, BorderLayout.WEST);
            add(field);

            final JButton save = new JButton(Resource.loadImageIcon("save.png"));

            add(save, BorderLayout.EAST);

            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    config.put(key, field.getText());
                    config.save();
                }
            });
        }



    }

}
