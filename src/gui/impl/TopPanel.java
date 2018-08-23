package gui.impl;

import configurations.Configurations;
import configurations.Resource;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public final class TopPanel extends JPanel implements PropertyChangeListener {
	
	private final Timer timer = new Timer();
	
	private final JLabel count = new JLabel();
	
	private final JLabel timeLeft = new JLabel();
	
	final JPanel testPanel;

    //panel that wll sit on top of gui while test is running
	public TopPanel(final TestPanel testPanel) {
		this.testPanel = testPanel;

		setLayout(new BorderLayout());
		final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
        final JLabel logo = new JLabel("<html>BWCpa Entrance Exam<br><hr></html>", Resource.loadImageIcon(35, 35, "accounting.png"), JLabel.CENTER);
		for(final JLabel label : new JLabel[]{timeLeft, count, logo}) {
			label.setFont(font);
			label.setForeground(new Color(16 << 0xFF | 8 << 0x55 | 0xAA));
		}
		count.setText("1/"+testPanel.test.getQuestions().size());

        //schedules a new timer of testtime in configurations and ticks down
		timer.schedule(new TimerTask() {
			private int time = Integer.parseInt(Configurations.getConfig().retreive("testtime"));
			
			public void run() {
				
				if(--time == 0) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            testPanel.submit();
                        }
                    });
				} else {
					final int seconds = time%60;
					final int minutes = time/60;
					timeLeft.setText(String.format("%d:%s", minutes, seconds < 10 ? "0" + seconds : seconds));
				}
				
				
			}
		}, 1000, 1000);
		this.add(timeLeft, BorderLayout.WEST);
		this.add(count, BorderLayout.EAST);
        this.add(logo);
		
	}
	
    //changes to show correct question
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
        if (arg0.getPropertyName().equals("questionchange")) {
			final String old = count.getText();
			count.setText(arg0.getNewValue()+old.substring(old.indexOf("/")));
		}
	}

    public void stop() {
        timer.cancel();
    }
	
	

}
