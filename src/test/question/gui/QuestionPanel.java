package test.question.gui;

import javax.swing.*;

import test.question.Question;

public abstract class QuestionPanel<T extends Question> {


	protected final T question;

    /**
     * Ignore data encapsulation as field is final and internal representation will not change
     * Calls {@link test.question.gui.QuestionPanel#draw()} and represents it as this panel
     */
    protected JPanel panel;
	
	public QuestionPanel(final T question) {
		this.question = question;
	}

    public abstract JPanel draw();
    @Override public abstract String toString();
    public abstract boolean isCorrect();

}
