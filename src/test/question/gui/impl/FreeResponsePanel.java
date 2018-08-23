package test.question.gui.impl;

import test.question.Question;
import test.question.gui.QuestionEditorFrame;
import test.question.gui.QuestionPanel;
import test.question.impl.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Wasay
 * Date: 1/27/15
 * Time: 7:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class FreeResponsePanel extends QuestionPanel<FreeResponseQuestion> {

    public static final Border BORDER =
            BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10));

    private final JTextArea input;

    public FreeResponsePanel(final FreeResponseQuestion question) {
        super(question);
        input = new JTextArea(question.defaultInput);
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        input.setBorder(BORDER);
    }

    @Override
    public JPanel draw() {
        if(panel != null)
            return panel;
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(question.question), BorderLayout.NORTH);
        panel.add(input, BorderLayout.CENTER);
        return this.panel = panel;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<strong>Question: ").append(question.question).append("</strong>").append("<br>");
        builder.append("&#09;<em>").append(input.getText().replace("\n", "<br> &#09;")).append("</em>");
        builder.append("<br><br>");
        return builder.toString();
    }

    @Override
    public boolean isCorrect() {
        return true;
    }



}
