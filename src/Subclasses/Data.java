package Subclasses;

import java.util.*;


public class Data {
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

    public static List<String> getRecommendationsForSymptom(String symptom) {
        switch (symptom.toLowerCase()) {
            case "fatigue":
                return Arrays.asList("Ensure you are getting enough sleep.", "Stay hydrated throughout the day.");
            case "headache":
                return Arrays.asList("Take a break and rest your eyes if you spend long hours on a screen.", "Consider stress-relief techniques.");

            default:
                return Collections.emptyList();
        }
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