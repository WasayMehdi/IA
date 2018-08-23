package gui.impl.admincp;

import configurations.Resource;
import test.question.Question;
import test.Test;
import test.question.gui.impl.FreeResponseEditor;
import test.question.gui.impl.MultipleChoiceEditor;
import test.question.gui.QuestionEditorFrame;
import test.question.impl.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Wasay
 * Date: 1/26/15
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionDisplayPanel extends JPanel {

    public QuestionDisplayPanel() {
        setLayout(new BorderLayout());

        final JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, 0));

        final JButton add = new JButton(Resource.loadImageIcon(20, 20, "add.png")),
                edit = new JButton(Resource.loadImageIcon(20, 20, "edit.png")),
                save = new JButton(Resource.loadImageIcon(20, 20, "save.png")),
                remove = new JButton(Resource.loadImageIcon(20, 20, "remove.png"));

        north.add(add);
        north.add(remove);
        north.add(edit);
        north.add(save);



        final DefaultListModel<Question> questions = new DefaultListModel<Question>();
        try {
            for(final Question question : Test.loadQuestions()) {
                questions.addElement(question);
            }
        } catch (final Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        final JList<Question> list = new JList<Question>(questions);
        final ListCellRenderer old = list.getCellRenderer();
        list.setCellRenderer(new ListCellRenderer<Question>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Question> list, Question value, int index, boolean isSelected, boolean cellHasFocus) {
                final JLabel label = (JLabel)old.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final boolean multipleChoice = value instanceof MultipleChoiceQuestion;
                label.setText(
                        String.format("<html><strong>#%d: </strong> <font color=%s><em>%s</em></font> </html>",
                        index, multipleChoice ? "'blue'" : "'red'", value.question));
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        add(new JScrollPane(list));
        add(north, BorderLayout.NORTH);


        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame f = new JFrame();
                f.getContentPane().setLayout(new GridLayout(2, 1, 0, 4));
                final JButton multi, free;
                f.getContentPane().add(multi = new JButton("Multiple Choice Editor"));
                f.getContentPane().add(free = new JButton("Free Response Editor"));

                final ActionListener listener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final QuestionEditorFrame frame = e.getSource().equals(multi) ? new MultipleChoiceEditor(questions) : new FreeResponseEditor(questions);
                        frame.pack();
                        frame.setLocationRelativeTo(QuestionDisplayPanel.this);
                        frame.setVisible(true);
                        f.dispose();
                    }
                };
                multi.addActionListener(listener);
                free.addActionListener(listener);
                f.setLocationRelativeTo(QuestionDisplayPanel.this);
                f.pack();
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setVisible(true);
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list.getSelectedValue() != null) {
                    final QuestionEditorFrame frame = list.getSelectedValue().getEditor(questions);
                    frame.pack();
                    frame.setLocationRelativeTo(QuestionDisplayPanel.this);
                    frame.setVisible(true);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Question[] questionsArray = new Question[questions.size()];
                questions.copyInto(questionsArray);
                Test.saveToJSON(questionsArray);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list.getSelectedValue() != null)
                    questions.removeElement(list.getSelectedValue());
            }
        });



    }

}
