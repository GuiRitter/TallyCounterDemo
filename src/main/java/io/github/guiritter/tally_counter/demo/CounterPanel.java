package io.github.guiritter.tally_counter.demo;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Shows a graphical representation of a TallyCounter,
 * mimicking a mechanical counter.
 * @author Guilherme Alan Ritter
 */
public final class CounterPanel {

    private final JTextField digitArray[];

    private int i;

    public final JPanel panel;

    public void setEnabled(boolean enabled) {
        for (i = 0; i < digitArray.length; i++) {
            digitArray[i].setEnabled(enabled);
        }
    }

    public void setText(String textArray[]) {
        if (textArray == null) {
            throw new IllegalArgumentException(
             "textArray is null.");
        }
        if (textArray.length < digitArray.length) {
            throw new IllegalArgumentException(
             "textArray contains less elements than there are digits.");
        }
        for (i = 0; i < digitArray.length; i++) {
            digitArray[i].setText(textArray[i]);
        }
        panel.repaint();
    }

    public CounterPanel(int digitAmount) {
        if (digitAmount < 1) {
            throw new IllegalArgumentException(
             "digitAmount must be a positive nonzero integer.");
        }
        digitArray = new JTextField[digitAmount];

        JPanel innerPanel = new JPanel();
        BoxLayout layout = new BoxLayout(innerPanel, BoxLayout.X_AXIS);
        innerPanel.setLayout(layout);
        innerPanel.add(GUI.createRigidArea());

        for (i = 0; i < digitAmount; i++) {
            digitArray[i] = new JTextField() {

                @Override
                public Dimension getMinimumSize() {
                    return getPreferredSize();
                }
            };
            digitArray[i].setEditable(false);
            digitArray[i].setHorizontalAlignment(JTextField.CENTER);
            innerPanel.add(digitArray[i]);
            innerPanel.add(GUI.createRigidArea());
        }

        panel = new JPanel();
        layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.add(GUI.createRigidArea());
        panel.add(innerPanel);
        panel.add(GUI.createRigidArea());
    }

    public static void main(String args[]) {
        CounterPanel counterPanel = new CounterPanel(5);
        counterPanel.setText(new String[]{"0", "1", "2", "3", "4"});
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(counterPanel.panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
