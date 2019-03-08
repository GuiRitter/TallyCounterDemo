package io.github.guiritter.tally_counter.demo;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Wraps a Swing {@link javax.swing.JComponent}
 * in a {@link javax.swing.JPanel} with a {@link javax.swing.JLabel}
 * using the {@link javax.swing.BoxLayout}.
 * @author Guilherme Alan Ritter
 */
public final class LabelledComponentFactory {

    /**
     * Builds a {@link javax.swing.JPanel}
     * containing a {@link javax.swing.JLabel} with the specified text
     * and the specified {@link javax.swing.JComponent}.
     * @param title the text to be displayed by the {@link javax.swing.JLabel}
     * @param component to be displayed below the {@link javax.swing.JLabel}
     * @param componentAlignment X alignment from {@link javax.swing.SwingConstants}
     * @param panelAlignment Y alignment from {@link javax.swing.SwingConstants}
     * @param labelComponentSpace the space between the label and the component
     * @return the finished {@link javax.swing.JPanel}
     * with a {@link javax.swing.BoxLayout}.
     */
    public static JPanel buildLabelledComponent(String title,
     JComponent component, float componentAlignment,
     float panelAlignment, int labelComponentSpace) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel(title);
        label.setAlignmentX(componentAlignment);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(
         labelComponentSpace, labelComponentSpace)));
        component.setAlignmentX(componentAlignment);
        panel.add(component);
        panel.setAlignmentY(panelAlignment);
        component.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            label.setEnabled(component.isEnabled());
        });
        return panel;
    }
}
