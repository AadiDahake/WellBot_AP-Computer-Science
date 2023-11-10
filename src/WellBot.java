import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WellBot {
    private static Map<String, SymptomInfo> medicalInfo = Data.getMedicalInfo();
    private static String[] genericResponses = Data.getGenericResponses();

    private static boolean awaitingRecommendations = false;

    public static String respondTo(String userInput) {
        String medicalResponse = getMedicalResponse(userInput);
        if (medicalResponse != null) {
            return medicalResponse;
        }

        return handleGenericQuery(userInput);
    }

    private static String getMedicalResponse(String userInput) {
        for (String key : medicalInfo.keySet()) {
            if (betterSearch(userInput, key)) {
                SymptomInfo symptomInfo = medicalInfo.get(key);
                String response = symptomInfo.getInformation();

                if (symptomInfo.isSymptom()) {
                    response += "\n\nWould you like any medicinal recommendations? Remember that I am not a Medical professional.";
                    awaitingRecommendations = true;
                } else {
                    awaitingRecommendations = false;
                }

                return response;
            }
        }
        return null;
    }

    private static String handleGenericQuery(String userInput) {
        if (awaitingRecommendations) {
            if (isAffirmativeResponse(userInput)) {
                // Provide recommendations
                List<String> recommendations = getRecommendationsForSymptom(userInput);
                return formatRecommendations(recommendations);
            } else {
                // User declined recommendations
                awaitingRecommendations = false;
                return "Alright. If you have more questions or need assistance, feel free to ask.";
            }
        }

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

        if (genericResponses.length > 0) {
            Random random = new Random();
            int index = random.nextInt(genericResponses.length);
            return genericResponses[index];
        }

        return null;
    }

    private static boolean betterSearch(String input, String key) {
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
            return suggestions[0]; // first word
        }
        return null;
    }

    private static String[] getWordsFromApi(String apiUrl) {
        try {
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            String json = response.toString();
            return parseWords(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String[0];
    }

    private static String[] parseWords(String json) {
        String[] parts = json.split("\"word\":\"");
        String[] words = new String[parts.length - 1];
        for (int i = 1; i < parts.length; i++) {
            String word = parts[i].split("\"")[0];
            words[i - 1] = word;
        }
        return words;
    }

    private static boolean isAffirmativeResponse(String userInput) {
        String[] affirmativeKeywords = {"yes", "sure", "okay", "fine", "absolutely", "definitely", "yeah", "bet"};

        for (String keyword : affirmativeKeywords) {
            if (betterSearch(userInput, keyword)) {
                return true;
            }
        }
        return false;
    }



    private static List<String> getRecommendationsForSymptom(String symptom) {
        // Add your recommendations for each symptom
        switch (symptom.toLowerCase()) {
            case "fatigue":
                return Arrays.asList("Ensure you are getting enough sleep.", "Stay hydrated throughout the day.");
            case "headache":
                return Arrays.asList("Take a break and rest your eyes if you spend long hours on a screen.", "Consider stress-relief techniques.");
            // Add more cases for other symptoms

            default:
                return Collections.emptyList();
        }
    }

    private static String formatRecommendations(List<String> recommendations) {
        if (recommendations.isEmpty()) {
            return "I don't have specific recommendations for this symptom. If you have persistent symptoms, consult with a healthcare professional.";
        }

        StringBuilder formattedRecommendations = new StringBuilder("Here are some recommendations:\n");
        for (String recommendation : recommendations) {
            formattedRecommendations.append("- ").append(recommendation).append("\n");
        }

        return formattedRecommendations.toString();
    }
}

class SymptomInfo {
    private String information;
    private boolean isSymptom;

    public SymptomInfo(String information, boolean isSymptom) {
        this.information = information;
        this.isSymptom = isSymptom;
    }

    public String getInformation() {
        return information;
    }

    public boolean isSymptom() {
        return isSymptom;
    }
}

class Data {
    private static String[] genericResponses = {
            "I'm here to assist you with health-related questions. Can you share more details about your symptoms or concerns?",
            "It looks like you're seeking health information. Feel free to ask about specific conditions, symptoms, or general wellness advice.",
            "If you have any health-related inquiries or need information, please provide more context, and I'll do my best to help.",
            "Not sure what you're looking for? You can ask about topics like nutrition, exercise, or specific symptoms for personalized information.",
            "Your health is important! Let me know if there's a specific area you'd like advice on, or if you have questions about common health topics.",
            "I'm here to provide support and information. If you're uncertain about a health issue or want advice, go ahead and ask!",
            "Whether it's a question about a specific ailment or general well-being, feel free to share, and I'll provide information to the best of my ability.",
            "Interested in learning more about health and wellness? Ask me anything from nutrition tips to advice on staying active.",
            "Your health matters. If you're unsure about something or have a health-related question, don't hesitate to ask for guidance.",
            "Let's talk about health! Whether it's a query about healthy habits or concerns about specific symptoms, I'm here to help."
    };
    private static Map<String, SymptomInfo> medicalInfo = new HashMap<>();

    static {
        medicalInfo.put("fever", new SymptomInfo("A fever is a common symptom of various infections. Make sure to rest and stay hydrated. If it persists, consult with a healthcare professional.", true));
        medicalInfo.put("headache", new SymptomInfo("Headaches can be caused by various factors, including stress, dehydration, or underlying health issues. If persistent, seek medical advice.", true));
        medicalInfo.put("cough", new SymptomInfo("A persistent cough may be a sign of respiratory or other health issues. It is advisable to consult with a healthcare professional for a proper diagnosis.", true));
        medicalInfo.put("nutrition", new SymptomInfo("Maintaining a balanced diet is crucial for overall health. Include a variety of fruits, vegetables, and whole grains in your meals.", false));
        medicalInfo.put("exercise", new SymptomInfo("Regular exercise is essential for maintaining good health. Aim for at least 150 minutes of moderate-intensity exercise per week.", false));
        medicalInfo.put("sleep", new SymptomInfo("Quality sleep is crucial for overall well-being. Aim for 7-9 hours of sleep per night for optimal health.", false));
        medicalInfo.put("hydration", new SymptomInfo("Staying hydrated is important for various bodily functions. Drink at least 8 glasses of water a day.", false));
        medicalInfo.put("hi", new SymptomInfo("Hello! How can I assist you today?", false));
        medicalInfo.put("hello", new SymptomInfo("Hi there! How can I assist you today?", false));
        medicalInfo.put("joke", new SymptomInfo("Why did the doctor carry a red pen? In case they needed to draw blood!", false));
        medicalInfo.put("thanks", new SymptomInfo("You're welcome! If you have more questions, feel free to ask.", false));
        medicalInfo.put("bye", new SymptomInfo("Goodbye! Take care of your health.", false));
    }

    public static Map<String, SymptomInfo> getMedicalInfo() {
        return medicalInfo;
    }

    public static String[] getGenericResponses() {
        return genericResponses;
    }
}
