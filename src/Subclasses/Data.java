package Subclasses;

import java.util.*;


public class Data {
    private static String[] genericResponses = {
            "Sorry, I don't understand. I'm here to assist you with health-related questions. Can you share more details about your symptoms or concerns? For example, you could ask me about fevers, cough, etc.",
            "I don't understand what you are asking. Feel free to ask about specific conditions, symptoms, or general wellness advice. For example, you could ask me about a cold, nutrition, or exercise.",
            "Not sure what you're looking for? You can ask about topics like nutrition, exercise, or specific symptoms for personalized information.",
            "My apologies; I'm confused. I'm here to provide support and information. If you're uncertain about a health issue or want advice, go ahead and ask! You could ask me about muscle or joint pain, fatigue, and many more topics!",
            "I don't understand that. Let's talk about health! Whether it's a query about healthy habits or concerns about specific symptoms, I'm here to help.",
            "Sorry, I do not understand. I'm here to assist you with health-related questions. Can you share more details about your symptoms or concerns (for example, fever, cough, nutrition)?",
            "If you're unsure what to ask, you can try specific health-related keywords, or ask about general wellness tips.",
            "If you have a specific health topic in mind, feel free to mention it, and I'll do my best to provide information.",
            "I'm here to help with anything related to health and well-being. If you're not sure where to start, you can ask about common symptoms, healthy habits, or medical conditions.",
            "If you're looking for information on a particular health issue, please provide more details so I can assist you better.",
            "If you have any health concerns or questions, feel free to let me know, and I'll provide information to the best of my ability. You could ask me about muscle or joint pain, fatigue, and many more topics!",
            "Sometimes providing more context or specifying your question can help me give you more accurate information. How can I assist you today?",
            "Sorry, I didn't quite catch that. Could you please provide more details or rephrase your question? I'm here to help with health-related information. Feel free to ask about nausea, fractures, or many more topics!",
            "Apologies, I'm here to assist with health-related queries. Could you give me more information about your symptoms or concerns? Feel free to ask about nausea, fractures, or many more topics!",
            "I may not have understood your input correctly. Feel free to share more details about what you're looking for, such as symptoms or health-related questions. Feel free to ask about nausea, fractures, or many more topics!",
            "Sorry if I'm not getting it right. Please provide more context or clarify your question, and I'll do my best to assist you with health-related information. For example, you could ask me about a cold, nutrition, or exercise.",
            "I want to help, but I might need more information. Could you please provide additional details or specify your health-related question?",
            "If my previous response was unclear, please let me know. I'm here to provide information on health-related topics. What can I assist you with? Feel free to ask about topics like nausea, fractures, or many more topics!",
            "I'm here to assist you, but I may need more information to understand your query better. Feel free to provide more details or ask a specific health-related question. For example, you could ask me about a cold, nutrition, or exercise.",
            "Apologies if my response was not helpful. To better assist you, could you share more information or specify your health concerns?"
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
        medicalInfo.put("muscle", new SymptomInfo("Muscle Pain is common, and it could be resolved by some medications do you want me to provide you some medications", true));
        medicalInfo.put("tired", new SymptomInfo("Tired or Muscle fatigue is common and it is curable. It could be felt when working hard or during worn out times. ", true));
        medicalInfo.put("cancer", new SymptomInfo("It is curable during the initial stages and rush to doctor immediately", false));
        medicalInfo.put("stomach", new SymptomInfo("The pain could have been felt because of some improper food and it could be cured with some medications.", true));
        medicalInfo.put("fracture", new SymptomInfo("It could have been caused due to a break in the bone it is curable and rush to the doctor", true));
        medicalInfo.put("bone", new SymptomInfo("It could have been caused due to a break in the bone it is curable and rush to the doctor", true));
        medicalInfo.put("break", new SymptomInfo("It could have been caused due to a break in the bone it is curable and rush to the doctor", true));
        medicalInfo.put("breath", new SymptomInfo("Shortness of breath can be a sign of respiratory or cardiovascular issues. Seek immediate medical attention if it is severe or persistent.", true));
        medicalInfo.put("nausea", new SymptomInfo("Nausea can be caused by various factors, including infections, motion sickness, or digestive issues. If persistent, consult with a healthcare professional.", true));
        medicalInfo.put("dizziness", new SymptomInfo("Dizziness may result from various causes, such as low blood pressure, dehydration, or inner ear problems. If recurrent, seek medical advice.", true));
        medicalInfo.put("anxiety", new SymptomInfo("Anxiety can manifest with symptoms like restlessness and increased heart rate. If it interferes with daily life, consider talking to a mental health professional.", true));
        medicalInfo.put("vision", new SymptomInfo("Changes in vision can indicate eye problems or other underlying health issues. Schedule an eye examination if you experience persistent vision changes.", true));
        medicalInfo.put("rash", new SymptomInfo("Skin rashes can result from allergies, infections, or autoimmune conditions. Consult with a dermatologist for proper diagnosis and treatment.", true));
        medicalInfo.put("joint", new SymptomInfo("Joint pain can be caused by arthritis, injury, or inflammation. Consult with a healthcare provider for a thorough evaluation and appropriate management.", true));
        medicalInfo.put("memory", new SymptomInfo("Memory loss can have various causes, including stress, aging, or medical conditions. Consult with a healthcare professional for a comprehensive assessment.", true));
        medicalInfo.put("pressure", new SymptomInfo("Monitoring blood pressure is essential for cardiovascular health. Maintain a healthy lifestyle and consult with a healthcare provider if you have concerns about your blood pressure.", false));
        medicalInfo.put("diabetes", new SymptomInfo("Diabetes requires careful management of blood sugar levels through lifestyle changes and, in some cases, medication. Regularly monitor blood sugar and follow your healthcare provider's guidance.", false));
        medicalInfo.put("allergies", new SymptomInfo("Allergies can cause symptoms like sneezing, itching, and congestion. Identify triggers and consider consulting an allergist for appropriate management.", true));
        medicalInfo.put("thyroid", new SymptomInfo("Thyroid disorders can affect metabolism and energy levels. If you experience symptoms like fatigue or weight changes, consult with a healthcare professional for thyroid function tests.", true));
        medicalInfo.put("mental", new SymptomInfo("Mental well-being is crucial for overall health. Practice stress management techniques and seek support if you are struggling with mental health issues.", false));
        medicalInfo.put("cold", new SymptomInfo("A persistent cold may be a sign of respiratory or other health issues. It is advisable to consult with a healthcare professional for a proper diagnosis.", true));
        medicalInfo.put("nose", new SymptomInfo("A persistent cold may be a sign of respiratory or other health issues. It is advisable to consult with a healthcare professional for a proper diagnosis.", true));

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
                return Arrays.asList("Please go to a doctor as soon as possible for emergency please take Tylenol", "Have a good sleep and drink Hot Water");
            case "cough":
            case "cold":
            case "nose":
                return Arrays.asList("Please go to the doctor as soon as possible for emergency", "please take hot water", "If the cough or cold is irresistible please use Benadryl syrup");
            case "muscle":
                return Arrays.asList("Use ice on the pain area and give a hot water bath", "Don't stress a lot in that area take some rest because the affected area needed some time to get back to its healthy state");
            case "tired":
                return Arrays.asList("Take 10-12 hours of sleep", "Maintain your sleep time visit a doctor if you feel tired for a long period of time");
            case "stomach":
                return Arrays.asList("Drink a lot of water, take a lot of fresh juices, eat fruits, eat easily digestible food", "Please go to the doctor as soon as possible for emergency please take PAN 40");
            case "fracture":
            case "bone":
            case "break":
                return Arrays.asList("Please rush to the doctor immediately", "For emergency put a emergency cast around the area give some warm bath and rush to the doctor immediately");
            case "breath":
                return Arrays.asList("Find a comfortable place to sit and try to relax.", "If shortness of breath persists, seek immediate medical attention.");
            case "nausea":
                return Arrays.asList("Drink small sips of clear, non-caffeinated liquids.", "Avoid spicy or greasy foods until nausea subsides.");
            case "dizziness":
                return Arrays.asList("Sit or lie down in a safe place to prevent injury.", "If dizziness continues, consult with a healthcare professional.");
            case "anxiety":
                return Arrays.asList("Practice deep breathing exercises to help calm the mind.", "Consider talking to a mental health professional for support.");
            case "vision":
                return Arrays.asList("If sudden vision changes occur, seek prompt medical attention.", "Have regular eye check-ups to monitor and address any vision issues.");
            case "rash":
                return Arrays.asList("Avoid scratching to prevent further irritation.", "Use over-the-counter creams or ointments for relief, but consult a dermatologist for persistent rashes.");
            case "joint":
                return Arrays.asList("Apply ice to the affected joint to reduce inflammation.", "Consider gentle exercises and consult with a healthcare provider for appropriate pain management.");
            case "memory":
                return Arrays.asList("Practice memory-enhancing activities, such as puzzles or games.", "If memory loss is concerning, consult with a healthcare professional for evaluation.");
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