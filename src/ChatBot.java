import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatBot {
    private static Map<String, String> medicalInfo = Data.getMedicalInfo();

    public static String respondTo(String userInput) {
        // Medical chat bot logic
        String randomResponse = Data.getRandomMedicalResponse();

        // Check if user input matches any key in medicalInfo
        for (String key : medicalInfo.keySet()) {
            if (betterSearch(userInput, key)) {
                return medicalInfo.get(key);
            }
        }

        return randomResponse;
    }

    public static boolean betterSearch(String resp, String key) {
        String pattern = "\\b" + Pattern.quote(key) + "\\b";
        Pattern sentence = Pattern.compile(pattern);

        Matcher matcher = sentence.matcher(resp);

        return matcher.find();
    }
}
