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

        setLayout(new BorderLayout());

        // chat area
        chatPane = new JTextPane();
        chatPane.setEditable(false);
        chatPane.setBackground(chatAreaColor);
        chatPane.setForeground(textColor);
        chatPane.setFont(textFont);

        JScrollPane scrollPane = new JScrollPane(chatPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // input field made
        inputField = new JTextField();
        inputField.setBorder(BorderFactory.createCompoundBorder(
                inputField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputField.setBackground(chatAreaColor);
        inputField.setForeground(textColor);
        inputField.setFont(textFont);

        // listen for "Enter"
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        // submit button
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

        // Adding all things to the frame
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(backgroundColor);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);

        getContentPane().setBackground(backgroundColor);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        // animation for carrot typing
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

        // Sframe properties to exit on close and width/size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void sendMessage() {
        String userInput = inputField.getText().trim();
        if (!userInput.isEmpty()) {
            appendToChat("User: " + userInput, true);
            // WE NEED TO ADD CHAT BOT LOGIC HERE

            appendToChat("ChatBot: " + userInput, false); // place holder. remove once ready to add logic
            inputField.setText(""); // place holder. remove once ready to add logic
        }
    }

    private void appendToChat(String message, boolean isUser) {
        StyledDocument styledDoc = chatPane.getStyledDocument();
        addStyledText(styledDoc, message + "\n", isUser ? userPromptColor : robotResponseColor);
        // Typing displayed
        displayToChat();
    }

    private void displayToChat() {
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
        StyleConstants.setBold(set, true);
        StyleConstants.setItalic(set, false);
        StyleConstants.setFontFamily(set, "Montserrat");
        StyleConstants.setFontSize(set, 14);
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
