import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Data {
    private static Map<String, String> medicalInfo = new HashMap<>();

    static {
        // Populate medical information
        medicalInfo.put("fever", "A fever is a common symptom of various infections. Make sure to rest and stay hydrated. If it persists, consult with a healthcare professional.");
        medicalInfo.put("headache", "Headaches can be caused by various factors, including stress, dehydration, or underlying health issues. If persistent, seek medical advice.");
        // Add more medical information as needed
    }

    private static String[] medicalResponses = {
            "I'm not a doctor, but I'll do my best to help. What symptoms are you experiencing?",
            "That's interesting! Have you consulted with a healthcare professional about it?",
            "Tell me more about your medical history. It might help me understand better.",
            "I don't have the expertise to provide medical advice, but I can offer general information. Would you like that?",
            "How can I assist you with health-related questions today?",
            "Let's focus on your well-being. What specific concerns do you have?",
            "I'm here to provide support and information related to health. Feel free to ask me anything."
    };

    public static Map<String, String> getMedicalInfo() {
        return medicalInfo;
    }

    public static String getRandomMedicalResponse() {
        Random random = new Random();
        int index = random.nextInt(medicalResponses.length);
        return medicalResponses[index];
    }
}
