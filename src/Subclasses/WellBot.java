package Subclasses;

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

    private static String previousUserInput;

    public static String respondTo(String userInput) {
        String medicalResponse = getMedicalResponse(userInput);
        if (medicalResponse != null) {
            return medicalResponse + "\n";
        }

        return handleRandomInput(userInput) + "\n";
    }

    private static String getMedicalResponse(String userInput) {
        for (String key : medicalInfo.keySet()) {
            if (betterSearch(userInput, key)) {
                SymptomInfo symptomInfo = medicalInfo.get(key);
                String response = symptomInfo.getInformation();

                if (symptomInfo.isSymptom()) {
                    response += "\n\nWould you like any medicinal recommendations? Remember that I am not a Medical professional.";
                    previousUserInput = userInput;
                    awaitingRecommendations = true;
                } else {
                    awaitingRecommendations = false;
                }

                return response;
            }
        }
        return null;
    }

    private static String handleRandomInput(String userInput) {
        if (awaitingRecommendations) {
            if (isAffirmativeResponse(userInput)) {

                for (String key : medicalInfo.keySet()) {
                    if (betterSearch(previousUserInput, key)) {
                        List<String> recommendations = Data.getRecommendationsForSymptom(key);
                        return formatRecommendations(recommendations);

                    }
                }
            } else if (isNegativeResponse(userInput)) {
                awaitingRecommendations = false;
                return "Alright. If you have more questions or need assistance, feel free to ask.";
            }
        }


//        String[] words = userInput.split("\\s+");
//        for (String word : words) {
//            String[] synonyms = getSynonyms(word).toArray(new String[0]);
//            for (String synonym : synonyms) {
//                String medicalResponse = getMedicalResponse(synonym);
//                if (medicalResponse != null) {
//                    return medicalResponse;
//                }
//            }
//        }

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
        String pattern = "\\b" + Pattern.quote(key) + "s?\\b";
        Pattern sentence = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

        Matcher matcher = sentence.matcher(input);

        return matcher.find();
    }

//    private static List<String> getSynonyms(String word) {
//        List<String> synonymsList = new ArrayList<>();
//
//        if (!(word.toLowerCase().equals("to")) && !(word.toLowerCase().equals("side")) && !(word.toLowerCase().equals("better")) && !(word.toLowerCase().equals("test"))) {
//            String[] synonyms = getWordsFromApi("https://api.datamuse.com/words?ml=" + word);
//            synonymsList.addAll(Arrays.asList(synonyms));
//        }
//        return synonymsList;
//    }

    private static String getCorrectedWord(String word) {
        String[] suggestions = getWordsFromApi("https://api.datamuse.com/sug?s=" + word);
        if (suggestions.length > 0) {
            return suggestions[0];
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

    private static boolean isNegativeResponse(String userInput) {
        String[] negativeKeywords = {"no", "nope", "nah", "not really", "negative"};

        for (String keyword : negativeKeywords) {
            if (betterSearch(userInput, keyword)) {
                return true;
            }
        }
        return false;
    }


    private static String formatRecommendations(List<String> recommendations) {
        if (recommendations.isEmpty()) {
            return "I don't have specific recommendations for this symptom. If you have persistent symptoms, consult with a healthcare professional.";
        }

        StringBuilder formattedRecommendations = new StringBuilder("Here are some recommendations:\n\n");
        for (String recommendation : recommendations) {
            formattedRecommendations.append("- ").append(recommendation).append("\n");
        }

        return formattedRecommendations.toString();
    }
}
