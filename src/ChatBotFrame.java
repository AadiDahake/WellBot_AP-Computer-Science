import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatBotFrame extends JFrame {
    private JTextPane chatPane;
    private JTextField inputField;
    private JButton submitButton;
    private Timer cursorTimer;

    private Color backgroundColor = new Color(40, 40, 40);
    private Color chatAreaColor = new Color(60, 60, 60);
    private Color textColor = new Color(255, 255, 255);
    private Color userPromptColor = new Color(240, 240, 240); // Off-white
    private Color robotResponseColor = new Color(180, 90, 180); // Pastel purple
    private Font textFont = new Font("Montserrat", Font.PLAIN, 14);

    public ChatBotFrame(String title) {
        super(title);

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create chat area
        chatPane = new JTextPane();
        chatPane.setEditable(false);
        chatPane.setBackground(chatAreaColor);
        chatPane.setForeground(textColor);
        chatPane.setFont(textFont);

        // Set up styled document for formatting
        StyledDocument styledDoc = chatPane.getStyledDocument();

        JScrollPane scrollPane = new JScrollPane(chatPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Create input field
        inputField = new JTextField();
        inputField.setBorder(BorderFactory.createCompoundBorder(
                inputField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputField.setBackground(chatAreaColor);
        inputField.setForeground(textColor);
        inputField.setFont(textFont);

        // Make pressing "Enter" submit
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        // Create submit button with resized icon
        ImageIcon submitIcon = new ImageIcon(getClass().getResource("/icon.png")); // Replace with your icon file name
        ImageIcon resizedIcon = new ImageIcon(submitIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        submitButton = new JButton(resizedIcon);
        submitButton.setBackground(chatAreaColor);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Add components to the frame
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(backgroundColor);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);

        getContentPane().setBackground(backgroundColor);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        // Set up animated typing cursor
        cursorTimer = new Timer(500, new ActionListener() {
            private boolean cursorVisible = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setCaretColor(textColor); // Change cursor color to white
                cursorVisible = !cursorVisible;
            }
        });
        cursorTimer.start();

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void sendMessage() {
        String userInput = inputField.getText().trim();
        if (!userInput.isEmpty()) {
            appendToChat("User: " + userInput, true);
            // Add your chatbot logic here
            // For simplicity, let's just echo the user input
            appendToChat("ChatBot: " + userInput, false);
            inputField.setText("");
        }
    }

    private void appendToChat(String message, boolean isUser) {
        StyledDocument styledDoc = chatPane.getStyledDocument();
        addStyledText(styledDoc, message + "\n", isUser ? userPromptColor : robotResponseColor);
        // Typing animation
        animateTyping();
    }

    private void animateTyping() {
        Timer timer = new Timer(50, new ActionListener() {
            private int currentIndex = inputField.getText().length();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex <= inputField.getText().length()) {
                    inputField.setCaretPosition(currentIndex);
                } else {
                    ((Timer) e.getSource()).stop();
                    inputField.setCaretPosition(inputField.getText().length());
                }
                currentIndex++;
            }
        });
        timer.start();
    }

    private void addStyledText(StyledDocument doc, String text, Color color) {
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, color);
        StyleConstants.setBold(set, true); // Make the text bold
        StyleConstants.setItalic(set, false); // Make the text not italic
        StyleConstants.setFontFamily(set, "Montserrat"); // Set the font family
        StyleConstants.setFontSize(set, 14); // Set the font size
        try {
            doc.insertString(doc.getLength(), text, set);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatBotFrame("ChatBot");
        });
    }
}
