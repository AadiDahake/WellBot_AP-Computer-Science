import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class ChatBotFrame extends JFrame {
    private JTextPane chatPane;
    private JTextField inputField;
    private JButton submitButton;
    private Timer cursorTimer;

    private Color backgroundColor = new Color(40, 40, 40);
    private Color chatAreaColor = new Color(43, 43, 43);
    private Color textColor = new Color(253, 250, 236);
    private Color userPromptColor = new Color(255, 251, 240);
    private Color robotResponseColor = new Color(206, 102, 206);
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
        chatPane.setEditorKit(new WrapEditorKit());


        JScrollPane scrollPane = new JScrollPane(chatPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // input field made
        inputField = new JTextField("Type here...");
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(chatAreaColor, 2, true), // Rounded border
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        ));
        inputField.setBackground(chatAreaColor);
        inputField.setForeground(new Color(169, 169, 169));
        inputField.setFont(textFont);

        // Grey line above text box
        JPanel linePanel = new JPanel();
        linePanel.setBackground(Color.GRAY);
        linePanel.setPreferredSize(new Dimension(400, 2)); // Adjust the width as needed

        // Clear the placeholder text
        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("Type here...".equals(inputField.getText())) {
                    inputField.setText("");
                    inputField.setForeground(textColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setText("Type here...");
                    inputField.setForeground(new Color(169, 169, 169));
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
        inputPanel.add(linePanel, BorderLayout.NORTH);
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
                inputField.setCaretColor(textColor);
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

        // Set frame properties to exit on close and width/size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void sendMessage() {
        String userInput = inputField.getText().trim();
        if (!userInput.isEmpty()) {
            appendToChat("User: " + userInput, true);
            // WE NEED TO ADD CHAT BOT LOGIC HERE

            String botResponse = ChatBot.respondTo(userInput);
            appendToChat("ChatBot: " + botResponse, false);

            inputField.setText("");
        }
    }

    private void appendToChat(String message, boolean isUser) {
        StyledDocument styledDoc = chatPane.getStyledDocument();

        addStyledText(styledDoc, isUser ? "User: " : "ChatBot: ", isUser ? new Color(148, 218, 255) : new Color(88, 144, 255));

        addStyledText(styledDoc, message.substring(isUser ? 5 : 8) + "\n", isUser ? userPromptColor : robotResponseColor);

        // Text Display
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

class WrapEditorKit extends StyledEditorKit {
    ViewFactory defaultFactory = new WrapColumnFactory();

    public ViewFactory getViewFactory() {
        return defaultFactory;
    }

    static class WrapColumnFactory implements ViewFactory {
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new WrapLabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            return new LabelView(elem);
        }
    }

    static class WrapLabelView extends LabelView {
        public WrapLabelView(Element elem) {
            super(elem);
        }

        public float getMinimumSpan(int axis) {
            switch (axis) {
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid axis: " + axis);
            }
        }
    }
}
