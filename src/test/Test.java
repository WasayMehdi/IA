package test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static configurations.Configurations.BACK;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import test.question.Question;
import test.question.QuestionType;


public final class Test{
	
	public final List<Question> questions;
	private int index = -1;
	private final int questionCount;
	
	private Test(int questionCount) throws FileNotFoundException, IOException, ParseException {
		this.questionCount = questionCount;
		questions = loadQuestions();

		arbitrarilyFilter();
		randomize();
	}
	
	private void arbitrarilyFilter() {
		while(questions.size() > questionCount) {
			final Question random_key = questions.get((int)(Math.random() * questions.size()));
			System.out.printf("Removing: %s\n", random_key);
			questions.remove(random_key);	
		}
	}
	
	private void randomize() {
        Collections.shuffle(questions);
    }
	
	public int getIndex() {
		return index;
	}
	
	public boolean hasNext() {
		return index < questions.size() - 1;
	}
	
	public boolean hasPrevious() {
		return index > 0;
	}
	
	public Question nextQuestion() {
		return questions.get(++index);
	}
	
	public Question getPreviousQuestion() {
		return questions.get(--index);
	}
	
	public List<Question> getQuestions() { 
		return questions;
	}


    public static Test of(final int questions) {
        try {
            return new Test(questions);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static final JSONArray getJSONQuestions() throws IOException, ParseException {
        final File file = new File(BACK + "/data/questiondata.json");
        final JSONParser parser = new JSONParser();
        return (JSONArray)parser.parse(new FileReader(file));
    }

    public static final List<Question> loadQuestions() throws IOException, ParseException {
        final List<Question> list = new ArrayList<Question>();
        final JSONArray array = getJSONQuestions();
        @SuppressWarnings("unchecked")
        final Iterator<? extends Object> $it = array.iterator();
        while($it.hasNext()) {
            final JSONObject object = (JSONObject)$it.next();
            list.add(QuestionType.values()[Integer.valueOf(object.get("type").toString())].load(object));
        }
        return list;
    }

    public static void saveToJSON(final Question... questions) {
        final JSONArray array = new JSONArray();
        for(final Question question : questions) {
            array.add(question.toJSONObject());
            System.out.println(question.toJSONObject().toJSONString());
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(new File(BACK+"/data/questiondata.json")));
            writer.write(array.toJSONString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
