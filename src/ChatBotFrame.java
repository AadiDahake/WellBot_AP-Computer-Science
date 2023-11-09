import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBotFrame extends JFrame {
    private JTextPane chatPane;
    private JTextField inputField;
    private JButton submitButton;
    private Timer cursorTimer;

    // Declare user and robot response colors
    private Color userPromptColor = new Color(255, 255, 255);
    private Color robotResponseColor = new Color(180, 90, 180); // Purple
    private Color backgroundColor = new Color(40, 40, 40);
    private Color textColor = new Color(255, 255, 255);
    private Color robotResponseBoxColor = new Color(220, 180, 255); // Light Purple


    public ChatBotFrame(String title) {
        super(title);

        // Set pastel dark color scheme
        Color chatAreaColor = new Color(60, 60, 60);
        Font textFont = new Font("Montserrat", Font.PLAIN, 14);

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create chat pane
        chatPane = new JTextPane();
        chatPane.setEditable(false);
        chatPane.setBackground(chatAreaColor);
        chatPane.setForeground(textColor);
        chatPane.setFont(textFont);

        // Create input field
        inputField = new JTextField();
        inputField.setBorder(BorderFactory.createCompoundBorder(
                inputField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputField.setBackground(chatAreaColor);
        inputField.setForeground(textColor);
        inputField.setFont(textFont);

        // Create submit button with icon
        ImageIcon submitIcon = new ImageIcon("path/to/your/icon.png"); // Replace with your icon path
        submitButton = new JButton(submitIcon);
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
        getContentPane().add(chatPane, BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        // Set up animated typing cursor
        cursorTimer = new Timer(500, new ActionListener() {
            private boolean cursorVisible = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cursorVisible) {
                    inputField.setCaretColor(backgroundColor);
                } else {
                    inputField.setCaretColor(textColor);
                }
                cursorVisible = !cursorVisible;
            }
        });
        cursorTimer.start();

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void sendMessage() {
        String userInput = inputField.getText().trim();
        if (!userInput.isEmpty()) {
            appendToChat("User: " + userInput, userPromptColor, backgroundColor);
            // Add your chatbot logic here
            // For simplicity, let's just echo the user input
            appendToChat("ChatBot: " + userInput, robotResponseColor, robotResponseBoxColor);
            inputField.setText("");
        }
    }


    private void appendToChat(String message, Color textColor, Color boxColor) {
        StyledDocument doc = chatPane.getStyledDocument();

        // Create a style with the specified text color
        SimpleAttributeSet textStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(textStyle, textColor);

        // Create a style with the specified box color
        SimpleAttributeSet boxStyle = new SimpleAttributeSet();
        StyleConstants.setBackground(boxStyle, boxColor);

        try {
            // Insert the colored box
            doc.insertString(doc.getLength(), "  ", boxStyle);
            // Insert the text with the specified text color
            doc.insertString(doc.getLength(), message, textStyle);
            // Move caret position to the end
            chatPane.setCaretPosition(doc.getLength());
            // Typing animation
            animateTyping(chatPane, doc.getLength() - message.length(), doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    private void animateTyping(JTextComponent component, int start, int end) {
        Timer timer = new Timer(50, new ActionListener() {
            private int currentIndex = start;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex <= end) {
                    try {
                        component.setCaretPosition(currentIndex);
                        component.setSelectionStart(currentIndex);
                        component.setSelectionEnd(currentIndex + 1);
                    } catch (IllegalArgumentException ex) {
                        // Ignore
                    }
                    currentIndex++;
                } else {
                    ((Timer) e.getSource()).stop();
                    component.setCaretPosition(component.getDocument().getLength());
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatBotFrame("ChatBot");
        });
    }
}
