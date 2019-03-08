package io.github.guiritter.tally_counter.demo;

import io.github.guiritter.tally_counter.TallyCounter;
import javax.swing.JOptionPane;

/**
 * Demonstration's main class. Allows a TallyCounter to be built
 * with one of the 4 ways available, shows the counter's values, allows it
 * to be incremented and reset. Manages the
 * {@link io.github.guiritter.tally_counter.TallyCounter} and the
 * {@link io.github.guiritter.tally_counter.demo.GUI}.
 * @author Guilherme Alan Ritter
 */
@SuppressWarnings("CallToPrintStackTrace")
public final class Main {

    private static TallyCounter counter;

    private static final GUI gui;

    /**
     * <code>true</code> for TallyCounter built, <code>false</code> otherwise.
     */
    private static boolean state = false;

    /**
     * What will happen with the value of the overflow flag
     * when the user resets the
     * {@link io.github.guiritter.tally_counter.TallyCounter}.
     */
    public enum OverflowPolicy {

        /**
         * The overflow flag will be set to <code>false</code>.
         */
        CLEAR,

        /**
         * The overflow flag value will not be changed.
         */
        KEEP,

        /**
         * The overflow flag will be set to <code>true</code>.
         */
        SET,
    }

    private static void setEnabled(boolean built) {
        gui.setEnabled(!built, !built, !built, !built, !built,
         built, built, built, built, built, built);
        state = built;
    }

    static {
        gui = new GUI("TallyCounter Demo",
         TypeItem.getArray(), OverflowPolicyItem.getArray()) {

            private void setCounterText() {
                long[] arrayLong = counter.getReadableArray();
                String arrayString[] = new String[arrayLong.length];
                for (int i = 0; i < arrayLong.length; i++) {
                    arrayString[i] = arrayLong[i] + "";
                }
                setCounterText(arrayString);
                setOverflowValueText(counter.overflowFlag + "");
            }

            @Override
            public void onBuildButtonPressed() {
                String arrayString = getMaximumArray();
                if ((arrayString != null) && (!arrayString.trim().isEmpty())) {
                    String fields[] = arrayString.split(",");
                    long arrayLong[] = new long[fields.length];
                    for (int i = 0; i < fields.length; i++) {
                        try {
                            arrayLong[i] = Long.parseLong(fields[i]);
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                            showMessageDialog("Maximum value array contains a non long integer value.",
                             "Invalid data", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    counter = new TallyCounter(arrayLong);
                    replaceCounter(arrayLong.length);
                    Main.setEnabled(true);
                    setCounterText();
                } else {
                    try {
                        counter = new TallyCounter(getDigitAmount(),
                     getType().value, getMaximumValue());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        showMessageDialog(ex.getMessage(),
                         "Invalid data", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    replaceCounter(counter.getArray().length);
                    Main.setEnabled(true);
                    setCounterText();
                }
            }

            @Override
            public void onClearButtonPressed() {
                counter = null;
                Main.setEnabled(false);
            }

            @Override
            public void onIncrementButtonPressed() {
                counter.increment();
                setCounterText();
            }

            @Override
            public void onResetButtonPressed() {
                OverflowPolicy overflowPolicy = getOverflowPolicy().value;
                if (overflowPolicy == OverflowPolicy.KEEP) {
                    counter.reset();
                } else {
                    counter.reset(overflowPolicy == OverflowPolicy.SET);
                }
                setCounterText();
            }
         };
    }

    public static void main(String args[]) {
        setEnabled(state);
    }
}
