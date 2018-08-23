package configurations;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Configurations {

    public static final String ENCRYPT = "abcdefghijklmnopqrstuvwxyz.@+/,<>;1234567890*&#$%^ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static final String DELIMITER = "-";

    public static final String BACK = ".";
	
	private static Configurations configurations;
	
	private final Map<String, String> config;

    private final File file;
	
	public Configurations() {
		this(new File(BACK+"/data/configurations.cfg"));
	}
	
	public Configurations(final File file) {
		this.config = load(file, DELIMITER);
        this.file = file;
        save();
    }
	
	public void put(final String key, final String value) {
		config.put(key, value);
	}
	
	public String retreive(final String key) {
        if(!config.containsKey(key))
            System.err.println("No key "+ key + " found");
		return config.getOrDefault(key, "");
	}

    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(final Map.Entry<String, String> entry : config.entrySet()) {
                writer.write(encrypt(entry.getKey(), 12) + "-" + encrypt(entry.getValue(), 15));
                writer.newLine();
            }
            writer.close();
        } catch(final Exception ex) {
            ex.printStackTrace();
        }
    }
	
	
	public static Configurations getConfig() {
		if(configurations == null)
			configurations = new Configurations();
		return configurations;
	}


    private final static String encrypt(final String s, final int offset) {
        final StringBuilder builder = new StringBuilder();
        for(final char c : s.toCharArray()) {
            builder.append(ENCRYPT.charAt((ENCRYPT.indexOf(c) + offset)%(ENCRYPT.length())));
        }
        return builder.toString();
    }

    /**
     * Decrypts the configurations file
     * @return Map of values loaded, set to configurations map
     */
	
	
	public static Map<String, String> load(final File file, final String delimiter) {
		final Map<String, String> map = new HashMap<String, String>();
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(file));
			for(String s = ""; (s = reader.readLine()) != null;) {
				final String[] parts = s.split(delimiter);
				map.put(encrypt(parts[0].trim(), ENCRYPT.length() - 12), encrypt(parts[1].trim(), ENCRYPT.length() -15));
			}
			reader.close();
		} catch(final Exception ex) {
			ex.printStackTrace();
		}

        return map;
	}

}









       /* map.put("email", "bussinesswisecpa@gmail.com");
        map.put("emailp", "13267941286149");
        map.put("emailreceiver", "swm321@gmail.com");
        map.put("questionamount", "20");
        map.put("password", "hello123");
        map.put("testtime", "300");  */
