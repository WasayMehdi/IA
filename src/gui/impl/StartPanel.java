package gui.impl;

import configurations.Resource;
import gui.Gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.*;

import gui.IPanelManager;
import test.Test;
import configurations.Configurations;

@SuppressWarnings("serial")
public class StartPanel extends JPanel {
	
	
	public StartPanel(final Gui listener) {
		
		setLayout(new GridLayout(4, 1, 20, 20));

        final Dimension dimension = new Dimension(50, 100);

        final JLabel logo = new JLabel(
                "BWCpa Entrance Exam", Resource.loadImageIcon("accounting.png"), JLabel.CENTER
        );
        logo.setFont(new Font("DialogInput", Font.BOLD, 45));
        add(logo);
		
		final JButton startTest = of("Start Test");
		final JButton directions = of("Directions");
		final JButton adminControlPanel = of("ACP");

        //add and size all buttons
        for(final JButton button : new JButton[]{startTest, directions, adminControlPanel}) {
            button.setPreferredSize(dimension);
            add(button);
        }

		startTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.propertyChange(
						new PropertyChangeEvent(this, "panelchange", null,
								new TestPanel(Test.of(
										Integer.parseInt(Configurations.getConfig().retreive("questionamount"))))));
			}
		});
		
		directions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                ((Gui)listener).changePanelId(DirectionPanel.ID);
			}
		});

        adminControlPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Gui)listener).changePanelId(AdminCP.ID);
            }
        });

    }

    //creates a JButton with correct font and size
	private JButton of(final String string) {
		final JButton button = new JButton(string);
		button.setFont(new Font("Serif", Font.BOLD, 25));
		button.setPreferredSize(new Dimension(100, 60));
		return button;
	}

}
