package io.github.guiritter.tally_counter.demo;

import static io.github.guiritter.graphical_user_interface.LabelledComponentFactory.buildLabelledComponent;
import static io.github.guiritter.tally_counter.demo.Main.OverflowPolicy.KEEP;
import static javax.swing.BorderFactory.createTitledBorder;
import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import io.github.guiritter.tally_counter.TallyCounter;

/**
 * Shows the graphical user interface window.
 * @author Guilherme Alan Ritter
 */
public abstract class GUI {

    private final JSpinner amountSpinner;

    private final JButton buildButton;

    private final JButton clearButton;

    private CounterPanel counter;

    private final JPanel counterPanel;

    public static final Font font = new Font("DejaVu Sans", 0, 12); // NOI18N

    public final JFrame frame;

    private final JButton incrementButton;

    private final JTextField maximumArrayField;

    private final JSpinner maximumSpinner;

    private final JComboBox<OverflowPolicyItem> overflowPolicyComboBox;

    private final JTextField overflowValueField;

    private final JButton resetButton;

    public static final int spaceLargeValue;

    private final JComboBox<TypeItem> typeComboBox;

    static {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        UIManager.put("Button.font",             font);
        UIManager.put("CheckBox.font",           font);
        UIManager.put("ComboBox.font",           font);
        UIManager.put("InternalFrame.titleFont", font);
        UIManager.put("Label.font",              font);
        UIManager.put("List.font",               font);
        UIManager.put("MenuItem.font",           font);
        UIManager.put("Spinner.font",            font);
        UIManager.put("TextField.font",          font);
        UIManager.put("TitledBorder.font",       font);
        UIManager.put("ToolTip.font",            font);
        spaceLargeValue = getSpace(false);
    }

    public static final Component createRigidArea() {
        return Box.createRigidArea(new Dimension(spaceLargeValue, spaceLargeValue));
    }

    public final int getDigitAmount() {
        return ((SpinnerNumberModel) amountSpinner.getModel())
         .getNumber().intValue();
    }

    public final int getMaximumValue() {
        return ((SpinnerNumberModel) maximumSpinner.getModel())
         .getNumber().intValue();
    }

    public final String getMaximumArray() {
        return maximumArrayField.getText();
    }

    public final OverflowPolicyItem getOverflowPolicy() {
        return overflowPolicyComboBox.getItemAt(
         overflowPolicyComboBox.getSelectedIndex());
    }

    public final Dimension getPreferredSizeTextFieldWithBorder(
     String maximumString, String title) {
        JTextField field = new JTextField(maximumString);
        TitledBorder border = createTitledBorder(title);
        field.setBorder(border);
        Dimension fieldSize = field.getPreferredSize();
        Dimension borderSize = border.getMinimumSize(field);
        return new Dimension(Math.max(fieldSize.width, borderSize.width),
         Math.max(fieldSize.height, borderSize.height));
    }

    /**
     * Computes the size of the space to be used between
     * {@link javax.swing.JComponent}s,
     * based on the preferred width of a {@link javax.swing.JLabel}.
     * The text of this label contains
     * either the least or the most wide character up to {@code \u00ff}.
     * @param small <code>true</code> for least wide character,
     * <code>false</code> otherwise
     * @return the size of the space to be used between components
     */
    public static final int getSpace(boolean small) {
        JLabel label;
        int space = small ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        for (int i = 0; i < 256; i++) {
            label = new JLabel(Character.toChars(i)[0] + "");
            if (small) {
                if (label.getPreferredSize().width > 0) {
                    space = Math.min(space, label.getPreferredSize().width);
                }
            } else {
                space = Math.max(space, label.getPreferredSize().width);
            }
        }
        return space;
    }

    public final TypeItem getType() {
        return typeComboBox.getItemAt(typeComboBox.getSelectedIndex());
    }

    public abstract void onBuildButtonPressed();

    public abstract void onClearButtonPressed();

    public abstract void onIncrementButtonPressed();

    public abstract void onResetButtonPressed();

    public final void replaceCounter(int digitAmount) {
        if (digitAmount < 1) {
            throw new IllegalArgumentException(
             "digitAmount must be greater than 0");
        }
        counterPanel.removeAll();
        counterPanel.add((counter = new CounterPanel(digitAmount)).panel);
        frame.revalidate();
    }

    public final void setCounterText(String text[]) {
        counter.setText(text);
    }

    public final void setEnabled(boolean type, boolean digitAmount,
     boolean maximumValue, boolean maximumValueArray, boolean build,
     boolean clear, boolean counter, boolean increment, boolean reset,
     boolean overflowValue, boolean overflowPolicy) {
        typeComboBox.setEnabled(type);
        amountSpinner.setEnabled(digitAmount);
        maximumSpinner.setEnabled(maximumValue);
        maximumArrayField.setEnabled(maximumValueArray);
        buildButton.setEnabled(build);
        clearButton.setEnabled(clear);
        this.counter.setEnabled(counter);
        incrementButton.setEnabled(increment);
        resetButton.setEnabled(reset);
        overflowValueField.setEnabled(overflowValue);
        overflowPolicyComboBox.setEnabled(overflowPolicy);
        if (maximumValueArray) {
            setEnabledExclusive();
        }
    }

    private void setEnabledExclusive() {
        if (maximumArrayField.getText().trim().isEmpty()) {
            typeComboBox.setEnabled(true);
            amountSpinner.setEnabled(true);
            maximumSpinner.setEnabled(true);
        } else {
            typeComboBox.setSelectedItem(
             TypeItem.getByValue(TallyCounter.Type.NORMAL));
            typeComboBox.setEnabled(false);
            amountSpinner.setEnabled(false);
            maximumSpinner.setEnabled(false);
        }
    }

    public final void setOverflowValueText(String text) {
        overflowValueField.setText(text);
    }

    public final void showMessageDialog(
     Object message, String title, int messageType) {
        JOptionPane.showMessageDialog(frame, message, title, messageType);
    }

    public GUI(String title, TypeItem typeItems[],
     OverflowPolicyItem overflowPolicyItems[]) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        BoxLayout layout
         = new BoxLayout(frame.getContentPane(), Y_AXIS);
        frame.setLayout(layout);
        frame.add(createRigidArea());
        int spaceSmall = getSpace(true);

        JPanel panel = new JPanel();
        layout = new BoxLayout(panel, X_AXIS);
        panel.setLayout(layout);
        panel.add(createRigidArea());

        typeComboBox = new JComboBox<>(typeItems);
        panel.add(buildLabelledComponent(
         "Type", typeComboBox, Component.LEFT_ALIGNMENT,
         Component.BOTTOM_ALIGNMENT, spaceSmall));
        panel.add(createRigidArea());

        amountSpinner = new JSpinner();
        amountSpinner.setModel(
         new SpinnerNumberModel(9, 1, Integer.MAX_VALUE, 1));
        panel.add(buildLabelledComponent(
         "Digit Amount", amountSpinner, Component.LEFT_ALIGNMENT,
         Component.BOTTOM_ALIGNMENT, spaceSmall));
        panel.add(createRigidArea());

        frame.add(panel);
        frame.add(createRigidArea());

        panel = new JPanel();
        layout = new BoxLayout(panel, X_AXIS);
        panel.setLayout(layout);
        panel.add(createRigidArea());

        maximumSpinner = new JSpinner();
        maximumSpinner.setModel(
         new SpinnerNumberModel(9, 1, Long.MAX_VALUE, 1));
        panel.add(buildLabelledComponent(
         "Maximum value", maximumSpinner, Component.LEFT_ALIGNMENT,
         Component.BOTTOM_ALIGNMENT, spaceSmall));
        panel.add(createRigidArea());

        frame.add(panel);
        frame.add(createRigidArea());

        panel = new JPanel();
        layout = new BoxLayout(panel, X_AXIS);
        panel.setLayout(layout);
        panel.add(createRigidArea());

        maximumArrayField = new JTextField();
        maximumArrayField.getDocument().addDocumentListener(
         new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                setEnabledExclusive();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setEnabledExclusive();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setEnabledExclusive();
            }
        });
        panel.add(buildLabelledComponent(
         "Maximum value array", maximumArrayField, Component.LEFT_ALIGNMENT,
         Component.BOTTOM_ALIGNMENT, spaceSmall));
        panel.add(createRigidArea());

        buildButton = new JButton("Build");
        buildButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        buildButton.addActionListener((ActionEvent e) -> {
            onBuildButtonPressed();
        });
        panel.add(buildButton);
        panel.add(createRigidArea());

        clearButton = new JButton("Clear");
        clearButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        clearButton.addActionListener((ActionEvent e) -> {
            onClearButtonPressed();
        });
        panel.add(clearButton);
        panel.add(createRigidArea());

        frame.add(panel);
        frame.add(createRigidArea());

        counterPanel = new JPanel();
        layout = new BoxLayout(counterPanel, X_AXIS);
        counterPanel.setLayout(layout);
        counterPanel.add((counter = new CounterPanel(9)).panel);

        frame.add(counterPanel);
        frame.add(createRigidArea());

        panel = new JPanel();
        layout = new BoxLayout(panel, X_AXIS);
        panel.setLayout(layout);
        panel.add(createRigidArea());
        panel.add(Box.createHorizontalGlue());
        panel.add(createRigidArea());
        panel.add(Box.createHorizontalGlue());

        incrementButton = new JButton("Increment");
        incrementButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        incrementButton.addActionListener((ActionEvent e) -> {
            onIncrementButtonPressed();
        });
        panel.add(incrementButton);
        panel.add(Box.createHorizontalGlue());
        panel.add(createRigidArea());
        panel.add(Box.createHorizontalGlue());

        resetButton = new JButton("Reset");
        resetButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        resetButton.addActionListener((ActionEvent e) -> {
            onResetButtonPressed();
        });
        panel.add(resetButton);
        panel.add(Box.createHorizontalGlue());
        panel.add(createRigidArea());
        panel.add(Box.createHorizontalGlue());
        panel.add(createRigidArea());

        frame.add(panel);
        frame.add(createRigidArea());

        panel = new JPanel();
        layout = new BoxLayout(panel, X_AXIS);
        panel.setLayout(layout);
        panel.add(createRigidArea());

        overflowValueField = new JTextField();
        overflowValueField.setEditable(false);
        panel.add(buildLabelledComponent(
         "Overflow value", overflowValueField, Component.LEFT_ALIGNMENT,
         Component.BOTTOM_ALIGNMENT, spaceSmall));
        panel.add(createRigidArea());

        overflowPolicyComboBox = new JComboBox<>(overflowPolicyItems);
        overflowPolicyComboBox.setSelectedItem(OverflowPolicyItem.getByValue(KEEP));
        panel.add(buildLabelledComponent(
         "Overflow policy", overflowPolicyComboBox, Component.LEFT_ALIGNMENT,
         Component.BOTTOM_ALIGNMENT, spaceSmall));
        panel.add(createRigidArea());

        frame.add(panel);
        frame.add(createRigidArea());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
