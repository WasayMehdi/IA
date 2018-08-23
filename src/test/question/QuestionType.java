package test.question;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import test.question.impl.FreeResponseQuestion;
import test.question.impl.MultipleChoiceQuestion;

/**
 * Created with IntelliJ IDEA.
 * User: Wasay
 * Date: 2/5/15
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 */
public enum QuestionType {
    MULTIPLE_CHOICE {
        @Override
        public Question load(final JSONObject object) {
            @SuppressWarnings("unchecked")
            Object[] array2 = ((JSONArray)object.get("answers")).toArray(
                    new String[((JSONArray)object.get("answers")).size()]);
            return new MultipleChoiceQuestion(this, (String)object.get("question"),
                    Integer.valueOf((object.get("correctAnswer")).toString()),
                    (String[])array2);
        }

    },
    FREE_RESPONSE {
        @Override
        public Question load(final JSONObject object) {
            return new FreeResponseQuestion(this, (String)object.get("question"), (String)object.get("default-input"));
        }


    };

    public Question load(final JSONObject object) {
        throw new AbstractMethodError("Will not happen");
    }

}
