package Subclasses;

import java.util.*;


public class Data {
    private static String[] genericResponses = {
            "I'm here to assist you with health-related questions. Can you share more details about your symptoms or concerns?",
            "How are you feeling today?. Feel free to ask about specific conditions, symptoms, or general wellness advice.",
            "Is there anything specific that's bothering you?, please provide more context, and I'll do my best to help.",
            "Not sure what you're looking for? You can ask about topics like nutrition, exercise, or specific symptoms for personalized information.",
            "Your health is important! Have you been in touch with your doctor recently?, or if you have questions about common health topics.",
            "I'm here to provide support and information. If you're uncertain about a health issue or want advice, go ahead and ask!",
            "Is there anything you'd like assistance with regarding your daily activities?", "Is there a book, movie, or TV show you'd like to enjoy right now?","Would you like me to bring you anything to help pass the time?","How about a board game or some music?",
            "Whether it's a question about a specific ailment or general well-being, feel free to share, and I'll provide information to the best of my ability.",
            "Have you noticed any changes in your symptoms? Ask me anything from nutrition tips to advice on staying active.",
            "Your health matters. If you're unsure about something or have a health-related question, don't hesitate to ask for guidance.",
            "Let's talk about health! Whether it's a query about healthy habits or concerns about specific symptoms, I'm here to help.",
            "What did the doctor say about your current condition?","Are you following the prescribed treatment plan?", "Is there anything I can do to make you more comfortable?",
            "Have you been taking your medications as prescribed?", "Are you experiencing any side effects from the medication?", "Have you found any relief from the symptoms?",
            "Is there anything specific you feel like eating or avoiding?", "Have you been able to maintain a healthy diet?", "Are you staying hydrated?","Is there anything on your mind that you want to talk about?",
            "Do you have any specific plans for today?"
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
        medicalInfo.put("muscle pain",new SymptomInfo("Muscle Pain is common, and it could be resolved by some medications do you want me to provide you some medications",true));
        medicalInfo.put("tired",new SymptomInfo("Tired or Muscle fatigue is common and it is curable. It could be felt when working hard or during worn out times. ",true));
        medicalInfo.put("cancer",new SymptomInfo("It is curable during the initial stages and rush to doctor immediately",false));
        medicalInfo.put("stomach pain",new SymptomInfo("The pain could have been felt because of some improper food and it could be cured with some medications.",true));
        medicalInfo.put("fracture",new SymptomInfo("It could have been caused due to a break in the bone it is curable and rush to the doctor",true));
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
            case "fever":
                return Arrays.asList("Please go to a doctor as soon as possible for emergency please take Tylenol", "Have a good sleep and drink Hot Water"); // for everything that is TRUE make new case VIRAT KOHLI Needs it
            case "cough":
                return Arrays.asList("Please go to the doctor as soon as possible for emergency please take hot water", "If the cough is irresistible please Benadryl syrup");
            case "muscle pain":
                return Arrays.asList("Use ice on the pain area and give a hot water bath", "Don't stress a lot in that area take some rest because the affected area needed some time to get back to its healthy state");
            case "tired":
                return Arrays.asList("Take 10-12 hours of sleep", "Maintain your sleep time visit a doctor if you feel tired for a long period of time");
            case "stomach pain":
                return Arrays.asList("Drink a lot of water, take a lot of fresh juices, eat fruits, eat easily digestible food", "Please go to the doctor as soon as possible for emergency please take PAN 40");
            case "fracture":
                return Arrays.asList("Please rush to the doctor immediately", "For emergency put a emergency cast around the area give some warm bath and rush to the doctor immediately");

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