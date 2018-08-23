package test.question.impl;

import org.json.simple.JSONObject;
import test.question.Question;
import test.question.QuestionType;
import test.question.gui.QuestionEditorFrame;
import test.question.gui.impl.FreeResponseEditor;
import test.question.gui.impl.FreeResponsePanel;
import test.question.gui.QuestionPanel;

import javax.swing.*;

public class FreeResponseQuestion extends Question {
	
    public final String defaultInput;
	
	public FreeResponseQuestion(final QuestionType type, final String question, final String defaultInput) {
		super(type, question);
        this.defaultInput = defaultInput;
	}

    public JSONObject toJSONObject() {
        final JSONObject object = new JSONObject();
        object.put("type", 1);
        object.put("question", question);
        object.put("default-input", defaultInput);
        return object;
    }

    public QuestionPanel panel() {
        if(panel != null)
            return panel;
        return panel = new FreeResponsePanel(this);
    }

    @Override
    public QuestionEditorFrame<FreeResponseQuestion> getEditor(final DefaultListModel<Question> list) {
        final FreeResponseEditor editor = new FreeResponseEditor(list);
        editor.load(this);
        return editor;
    }

}


