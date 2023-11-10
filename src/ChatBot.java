import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatBot {
    private static Map<String, String> medicalInfo = Data.getMedicalInfo();

    public static String respondTo(String userInput) {
        // Check if user input matches any key in medicalInfo
        String medicalResponse = getMedicalResponse(userInput);
        if (medicalResponse != null) {
            return medicalResponse;
        }

        // If no medical match, handle generic queries
        return handleGenericQuery(userInput);
    }

    private static String getMedicalResponse(String userInput) {
        for (String key : medicalInfo.keySet()) {
            if (containsKeyword(userInput, key)) {
                return medicalInfo.get(key);
            }
        }
        return null;
    }

    private static String handleGenericQuery(String userInput) {
        // Check for synonyms and match them with medicalInfo keys
        String[] synonyms = getSynonyms(userInput);
        for (String synonym : synonyms) {
            String medicalResponse = getMedicalResponse(synonym);
            if (medicalResponse != null) {
                return medicalResponse;
            }
        }

        // If no synonym match, check for misspelled words
        String correctedWord = getCorrectedWord(userInput);
        if (correctedWord != null) {
            String medicalResponse = getMedicalResponse(correctedWord);
            if (medicalResponse != null) {
                return medicalResponse;
            }
        }

        // If no synonym or misspelling match, handle as a generic query
        return "I'm a chatbot that specializes in medical information. If you have non-medical questions, I might not have the answer. How can I help you with your health?";
    }

    private static boolean containsKeyword(String input, String key) {
        String pattern = "\\b" + Pattern.quote(key) + "\\b";
        Pattern sentence = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

        Matcher matcher = sentence.matcher(input);

        return matcher.find();
    }

    private static String[] getSynonyms(String word) {
        return getWordsFromApi("https://api.datamuse.com/words?ml=" + word);
    }

    private static String getCorrectedWord(String word) {
        String[] suggestions = getWordsFromApi("https://api.datamuse.com/sug?s=" + word);
        if (suggestions.length > 0) {
            return suggestions[0]; // Choose the first suggestion as the corrected word
        }
        return null;
    }

    private static String[] getWordsFromApi(String apiUrl) {
        try {
            // Create a URL object for the Datamuse API
            URL url = new URL(apiUrl);

            // Open a connection to the API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response from the API
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Parse the JSON response to extract words
            String json = response.toString();
            return parseWords(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String[0]; // Return an empty array in case of an error
    }

    private static String[] parseWords(String json) {
        // Parse the JSON response to extract words
        // Here, you can use a JSON parsing library like Gson for a more robust solution
        // For simplicity, we'll use basic string manipulation
        String[] parts = json.split("\"word\":\"");
        String[] words = new String[parts.length - 1];
        for (int i = 1; i < parts.length; i++) {
            String word = parts[i].split("\"")[0];
            words[i - 1] = word;
        }
        return words;
    }
}

class Data {
    private static Map<String, String> medicalInfo = new HashMap<>();

    static {
        medicalInfo.put("fever", "A fever is a common symptom of various infections. Make sure to rest and stay hydrated. If it persists, consult with a healthcare professional.");
        medicalInfo.put("headache", "Headaches can be caused by various factors, including stress, dehydration, or underlying health issues. If persistent, seek medical advice.");
        medicalInfo.put("cough", "A persistent cough may be a sign of respiratory or other health issues. It is advisable to consult with a healthcare professional for a proper diagnosis.");
        medicalInfo.put("nutrition", "Maintaining a balanced diet is crucial for overall health. Include a variety of fruits, vegetables, and whole grains in your meals.");
        medicalInfo.put("exercise", "Regular exercise is essential for maintaining good health. Aim for at least 150 minutes of moderate-intensity exercise per week.");
        medicalInfo.put("sleep", "Quality sleep is crucial for overall well-being. Aim for 7-9 hours of sleep per night for optimal health.");
        medicalInfo.put("hydration", "Staying hydrated is important for various bodily functions. Drink at least 8 glasses of water a day.");
        medicalInfo.put("hi", "Hello! How can I assist you today?");
        medicalInfo.put("hello", "Hi there! How can I assist you today?");
        medicalInfo.put("joke", "Why did the doctor carry a red pen? In case they needed to draw blood!");
        medicalInfo.put("thanks", "You're welcome! If you have more questions, feel free to ask.");
        medicalInfo.put("bye", "Goodbye! Take care of your health.");
    }

    public static Map<String, String> getMedicalInfo() {
        return medicalInfo;
    }
}
