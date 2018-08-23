package gui.impl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import test.question.Question;
import test.Test;
import configurations.Email;
import configurations.Resource;
import test.question.gui.QuestionPanel;

@SuppressWarnings("serial")
public class TestPanel extends JPanel implements PropertyChangeListener{
	
	private QuestionPanel current;
	public final Test test;
	private final TopPanel topPanel;

	public TestPanel(final Test test) {
		this.test = test;
		setLayout(new BorderLayout());
		
		current = test.nextQuestion().panel();
		topPanel = new TopPanel(this);
		
		final JPanel south = new JPanel();
		south.setLayout(new BorderLayout());
		
		final JButton next = new JButton(), previous = new JButton();
		previous.setIcon(Resource.loadImageIcon(24, 16, "backbutton.png"));
		next.setIcon(Resource.loadImageIcon(24, 16, "forward.png"));
		south.add(next, BorderLayout.EAST);
		south.add(previous, BorderLayout.WEST);
		previous.setEnabled(false);
		
		add(south, BorderLayout.SOUTH);
		add(current.draw(), BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);

        final class Progress {
            void next() {
                if(!next.isEnabled())
                    return;
                if(!test.hasNext()) {
                    submit();
                    return;
                }
                progress();
                if(!test.hasNext()) {
                    next.setIcon(null);
                    next.setText("Submit");
                }
                previous.setEnabled(true);
            }

            void previous() {
                if(!previous.isEnabled())
                    return;
                regress();
                if(!test.hasPrevious())
                    previous.setEnabled(false);
                next.setIcon(Resource.loadImageIcon(24, 16, "forward.png"));
                next.setText("");
            }
        }

        final Progress progress = new Progress();
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				progress.next();
			}
		});
		
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				progress.previous();
			}
		});


	}
	
	@Override public void repaint() {
		super.repaint();
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equalsIgnoreCase("questionchange")) {
			try {
				this.remove((JPanel)evt.getOldValue());
				this.current = ((Question)evt.getNewValue()).panel();
				this.add(current.draw());
				repaint();
				revalidate();
			}catch(ClassCastException e) {
				e.printStackTrace();
			}
		}
	}

	private void progress() {
		update(test.nextQuestion().panel());
	}
	
	private void regress() {
		update(test.getPreviousQuestion().panel());
	}
	
	private void update(final QuestionPanel panel) {
		
		this.remove(current.draw());
		this.current = panel;
		this.add(current.draw());
		
		topPanel.propertyChange(new PropertyChangeEvent(this, "questionchange", 0, test.getIndex() + 1));
		
		repaint();
		revalidate();
		
	}
	
	void submit() {

        removeAll();
        topPanel.stop();

		final JPanel show = new JPanel();
		show.setLayout(new BorderLayout());
		
		final JTextPane area = new JTextPane();
		area.setContentType("text/html");
		
		int right = 0;
		
		final StringBuilder builder = new StringBuilder();
		for(final Question question : test.getQuestions()) {
			builder.append(question.panel());
			if(question.panel().isCorrect()) right++;
		}
		
		builder.append("<br>").append("<strong>Result: ").
			append(right).append("/").append(test.getQuestions().size()).append("</strong><br>");
		builder.append("<em>This assumes that the free-response questions are right</em>");
		
		area.setText(builder.toString());

        //construct an email of title Test results x/N with the results formatted in html
		final Email email = new Email(
				String.format("Test results: %d/%d", right, test.getQuestions().size()), builder.toString());
		email.dispatch();

        //show results to user
		show.add(new JScrollPane(area), BorderLayout.CENTER);
		area.setEditable(false);
		
		this.add(show);
		repaint();
		revalidate();
		
	}
	
	

}
