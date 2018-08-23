package test.question.gui.impl;

import test.question.Question;
import test.question.QuestionType;
import test.question.gui.QuestionEditorFrame;
import test.question.impl.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Wasay
 * Date: 1/27/15
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class FreeResponseEditor extends QuestionEditorFrame<FreeResponseQuestion> {

    private final JTextArea area = new JTextArea();

    public FreeResponseEditor(final DefaultListModel<Question> list) {
        super(list);

        area.setBorder(FreeResponsePanel.BORDER);

        final JPanel center = new JPanel();
        center.setLayout(new BorderLayout());

        final JLabel label = new JLabel("Default Text:");

        center.add(label, BorderLayout.WEST);
        center.add(area);

        getContentPane().add(center, BorderLayout.CENTER);
    }

    @Override
    public void load(final FreeResponseQuestion question) {
        old = question;
        area.setText(question.defaultInput);
        questionField.setText(question.question);
    }

    @Override
    public Question build() {
        if(questionField.getText().isEmpty())
            throw new IllegalStateException("You need to enter a question");
        return new FreeResponseQuestion(QuestionType.FREE_RESPONSE, questionField.getText(), area.getText());
    }



}
