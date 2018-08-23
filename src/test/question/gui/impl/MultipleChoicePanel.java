package test.question.gui.impl;

import test.question.gui.QuestionEditorFrame;
import test.question.Question;

import javax.swing.*;

import test.question.gui.QuestionPanel;
import test.question.impl.*;

import java.awt.*;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Wasay
 * Date: 1/27/15
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultipleChoicePanel extends QuestionPanel<MultipleChoiceQuestion> {

    final JRadioButton[] buttons;
    final ButtonGroup group = new ButtonGroup();

    public MultipleChoicePanel(final MultipleChoiceQuestion question) {
        super(question);

        this.buttons = new JRadioButton[question.getAnswers().length];
        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = new JRadioButton(question.getAnswers()[i]);
            group.add(buttons[i]);
        }
    }

    public JPanel draw() {
        if(panel != null)
            return panel;
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        final JLabel questionLabel = new JLabel(question.question);
        questionLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(questionLabel, BorderLayout.NORTH);


        final JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, 1));
        for(int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("Dialog", Font.TRUETYPE_FONT, 14));
            centre.add(buttons[i]);
        }

        panel.add(centre, BorderLayout.CENTER);
        return this.panel = panel;
    }

    @Override public String toString() {
        final StringBuilder builder = new StringBuilder("<strong>Question: ").append(question.question).append("</strong><br><div>");

        for(int i = 0; i < question.getAnswers().length; i++) {
            int state = 0;
            if(i == getSelectedButton())
                state |= AnswerState.CHOSEN.flag;
            if(i == question.correctAnswer)
                state |= AnswerState.CORRECT.flag;
            builder.append("&#09;").append(i+1).append(") ");
            builder.append(AnswerState.for_state(state)).append(": ");
            builder.append(question.getAnswers()[i]);
            if((state&AnswerState.CHOSEN.flag) != 0 && (state&AnswerState.CORRECT.flag) == 0)
                builder.replace(builder.indexOf("[CHOSEN]"), builder.indexOf("[CHOSEN]") + "[CHOSEN]".length(),
                        "<span color=\"red\">[INCORRECT CHOICE]:</span>");
            builder.append("<br>");
        }

        builder.append("</div><br>");

        return builder.toString();
    }

    public boolean isCorrect() {
        return Integer.valueOf(question.correctAnswer).equals(getSelectedButton());
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

    private enum AnswerState {
        CHOSEN(1 << 1, "[CHOSEN]"),
        CORRECT(1 << 2, "<span color=\"green\">[CORRECT]</span>");

        public final String display;
        public final int flag;

        private AnswerState(final int flag, final String display) {
            this.flag = flag;
            this.display = display;
        }

        public static final String for_state(final int state) {
            final StringBuilder builder = new StringBuilder();
            for(final AnswerState as : values())
                if((as.flag&state) != 0)
                    builder.append(as.display);
            return builder.toString();
        }
    }

}
