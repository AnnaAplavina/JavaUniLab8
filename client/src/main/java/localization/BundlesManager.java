package localization;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class BundlesManager {
    private static final Logger logger = Logger.getLogger(BundlesManager.class.getName());

    private final ResourceBundle english;
    private final ResourceBundle russian;
    private final ResourceBundle lithuanian;
    private final ResourceBundle norwegian;
    private final Properties selected;

    public BundlesManager() throws IOException {
        english = ResourceBundle.getBundle("langs", new Locale("en", "IE"));
        russian = ResourceBundle.getBundle("langs", new Locale("ru", "RU"));
        lithuanian = ResourceBundle.getBundle("langs", new Locale("lt", "LT"));
        norwegian = ResourceBundle.getBundle("langs", new Locale("no", "NO"));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("prop.properties");
        selected = new Properties();
        selected.load(inputStream);
        inputStream.close();
    }

    public String getValue(String key){
        String currentLanguage = selected.getProperty("selected");
        switch (currentLanguage){
            case "en_IE": return english.getString(key);
            case "ru": return russian.getString(key);
            case "lt": return lithuanian.getString(key);
            case "no": return norwegian.getString(key);
            default:
                logger.info("current language in selected_lang.properties is incorrect");
                return english.getString(key);
        }
    }

    public void setLanguage(String newLanguage){
        try {
            try {
                File file = new File(getClass().getClassLoader().getResource("prop.properties").toURI());
                selected.setProperty("selected", newLanguage);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                selected.store(fileOutputStream, null);
                fileOutputStream.close();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
