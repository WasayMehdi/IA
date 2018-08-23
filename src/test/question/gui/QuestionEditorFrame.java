package test.question.gui;

import test.question.Question;
import test.question.impl.MultipleChoiceQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Wasay
 * Date: 1/26/15
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class QuestionEditorFrame<T extends Question> extends JFrame {

    private static final JLabel QUESTION = new JLabel("Question: ");
    protected final JTextField questionField = new JTextField();
    protected final DefaultListModel<Question> list;

    protected final JPanel south;

    protected Question old;

    public QuestionEditorFrame(final DefaultListModel<Question> list) {
        this.list = list;

        setPreferredSize(new Dimension(400, 220));
        getContentPane().setLayout(new BorderLayout());
        final JPanel north = new JPanel();
        north.setLayout(new GridLayout(1, 2));

        north.add(QUESTION);
        north.add(questionField);

        getContentPane().add(north, BorderLayout.NORTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        south = new JPanel();
        south.setLayout(new BorderLayout());
        final JButton submit = new JButton("Submit");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    final Question build = build();
                    if(old != null) {
                        final int oldIndex = list.indexOf(old);
                        list.remove(oldIndex);
                        list.add(oldIndex, build);
                    } else
                        list.addElement(build);
                    dispose();
                }catch(IllegalStateException exception) {
                    JOptionPane.showMessageDialog(QuestionEditorFrame.this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        south.add(submit);

        getContentPane().add(south, BorderLayout.SOUTH);
    }


    public abstract void load(final T question);
    public abstract Question build();

}
