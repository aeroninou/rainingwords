package com.word.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class WordInput extends JFrame{
    private final JLabel wordLabel = new JLabel("Word: ");
    private final JTextField wordField = new JTextField(16);

    // Used to show user if their input matched a word or not.
    private final JLabel messageLabel = new JLabel();

    // Words to be input by the user.
    private final Collection<String> words;

    public WordInput(Collection<String> words) {
        super("Raining Words");
        buildUI();
        setFrameOptions();
        this.words = words;
        setVisible(true);
    }

    public boolean allWordsMatched() {
        return words.isEmpty();
    }

    private void buildUI() {
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(3, 2));
        pane.add(wordLabel);
        pane.add(wordField);
        pane.add(messageLabel);

        wordField.addActionListener(new WordMatchListener());
        wordField.setFont(new Font("SansSerif", Font.BOLD, 20));
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
    }

    private void setFrameOptions() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Top left of screen.
        setLocation(0, 0);
        setPreferredSize(new Dimension(300, 120));
        pack();
    }

    // Close the JFrame.
    private void close() {
//        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        setVisible(false);
        dispose();
    }

    private class WordMatchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            // Determine if input matched the list of words, and remove if so.
            String input = wordField.getText();
            boolean matches = words.contains(input);
            if (matches) {
                words.remove(input);
            }
            // Display colored message to suggest if match was found.
            messageLabel.setForeground(matches? Color.GREEN: Color.RED);
            messageLabel.setText(input);

            // Reset the text field input.
            wordField.setText("");

            // Close the window if words have been matched.
            if (allWordsMatched()) {
                close();
            }
        }
    }
}
