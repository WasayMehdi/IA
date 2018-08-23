package test.question;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import test.question.gui.QuestionEditorFrame;
import test.question.gui.QuestionPanel;
import test.question.impl.*;

import javax.swing.*;

public abstract class Question {

    public final QuestionType type;
	public final String question;
    protected QuestionPanel panel;
	
	public Question(final QuestionType type, final String question) {
        this.type = type;
		this.question = question;
	}
	
    public abstract JSONObject toJSONObject();
    public abstract QuestionPanel panel();
    public abstract QuestionEditorFrame getEditor(final DefaultListModel<Question> list);


    public boolean is(final QuestionType type) {
        return this.type.equals(type);
    }
}
