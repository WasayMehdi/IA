package test.question.gui.impl;

import configurations.Resource;
import test.question.Question;
import test.question.QuestionType;
import test.question.gui.QuestionEditorFrame;
import test.question.impl.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wasay
 * Date: 1/26/15
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultipleChoiceEditor extends QuestionEditorFrame<MultipleChoiceQuestion> {

    private final List<QuestionChoiceEditor> choices = new ArrayList<QuestionChoiceEditor>(2);
    private final ButtonGroup group = new ButtonGroup();
    private final JPanel center;

    public MultipleChoiceEditor(final DefaultListModel<Question> list) {
        super(list);

        center = new JPanel();
        center.setLayout(new BoxLayout(center, 1));


        final JButton add = new JButton("Add Choice");
        add.setIcon(Resource.loadImageIcon("add.png"));

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add(new QuestionChoiceEditor(MultipleChoiceEditor.this));
            }
        });

        getContentPane().add(center);

        final JButton submit = new JButton("Submit");


        south.add(add, BorderLayout.EAST);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Question build = build();
                if (old != null) {
                    final int oldIndex = list.indexOf(old);
                    list.remove(oldIndex);
                    list.add(oldIndex, build);
                } else
                    list.addElement(build);
                dispose();

            }
        });

        south.revalidate();
        south.repaint();
    }

    @Override
    public void load(final MultipleChoiceQuestion question) {
        this.old = question;
        for(final QuestionChoiceEditor panel : choices)
            this.remove(panel);
        for(int i = 0; i < question.getAnswers().length; i++) {
            add(new QuestionChoiceEditor(this));
            choices.get(i).setText(question.getAnswers()[i]);
            if(question.correctAnswer == i)
                choices.get(i).button.setSelected(true);
        }

        this.questionField.setText(question.question);
        center.repaint();
        center.revalidate();

        repaint();
        revalidate();

    }

    public Question build() {
        final List<String> answers = new ArrayList<String>();
        if(getSelectedButton() == -1 || group.getButtonCount() < 2)
            throw new IllegalStateException(getSelectedButton() == -1 ? "Correct answer not selected" : "Not enough answer choices");

        for(final QuestionChoiceEditor editor : choices)
            answers.add(editor.getAnswer());
        return new MultipleChoiceQuestion(QuestionType.MULTIPLE_CHOICE, questionField.getText(), getSelectedButton(), answers.toArray(new String[answers.size()]));
    }


    public synchronized void add(final QuestionChoiceEditor frame) {
        choices.add(frame);
        center.add(frame);
        center.revalidate();
        center.repaint();
        pack();
    }

    public synchronized void remove(final QuestionChoiceEditor frame) {
        choices.remove(frame);
        center.remove(frame);
        group.remove(frame.button);
        center.revalidate();
        center.repaint();
        pack();
    }

    private int getSelectedButton() {
        int index = -1;
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            index++;
            if (button.isSelected()) {
                return index;
            }
        }
        return -1;
    }

    private static final class QuestionChoiceEditor extends JPanel{

        private final JRadioButton button = new JRadioButton("Correct");
        private final JTextField choice = new JTextField();

        public QuestionChoiceEditor(final MultipleChoiceEditor parent) {
            parent.group.add(button);
            setLayout(new BorderLayout());
            add(button, BorderLayout.WEST);
            add(choice);

            final JButton delete = new JButton();
            delete.setIcon(Resource.loadImageIcon(16, 16, "remove.png"));
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parent.remove(QuestionChoiceEditor.this);
                }
            });
            add(delete, BorderLayout.EAST);
        }

        public String getAnswer() {
            return choice.getText();
        }

        public void setText(final String s) {
            choice.setText(s);
        }
    }
}
