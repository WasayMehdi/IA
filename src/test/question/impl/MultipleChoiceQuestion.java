package test.question.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import test.question.Question;
import test.question.QuestionType;
import test.question.gui.QuestionEditorFrame;
import test.question.gui.impl.MultipleChoiceEditor;
import test.question.gui.impl.MultipleChoicePanel;
import test.question.gui.QuestionPanel;

import javax.swing.*;

public class MultipleChoiceQuestion extends Question {
	
	public final int correctAnswer;
	private final String[] answers;

	public MultipleChoiceQuestion(final QuestionType type, final String question, final int correctAnswer, final String[] answers) {
		super(type, question);
		this.correctAnswer = correctAnswer;
		this.answers = answers;

	}

    public JSONObject toJSONObject() {
        final JSONObject object = new JSONObject();
        object.put("type", 0);
        object.put("question", question);
        object.put("correctAnswer", correctAnswer);
        final JSONArray array = new JSONArray();
        for(final String answer : answers) {
            array.add(answer);
        }
        object.put("answers", array);
        return object;
    }

    public String[] getAnswers() {
        return answers.clone();
    }


    @Override
    public QuestionPanel panel() {
        if(panel != null)
            return panel;
        return panel = new MultipleChoicePanel(this);
    }

    @Override
    public QuestionEditorFrame<MultipleChoiceQuestion> getEditor(final DefaultListModel<Question> list) {
        final MultipleChoiceEditor editor = new MultipleChoiceEditor(list);
        editor.load(this);
        return editor;
    }

}
