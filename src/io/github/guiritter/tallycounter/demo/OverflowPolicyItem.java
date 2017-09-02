package io.github.guiritter.tallycounter.demo;

import io.github.guiritter.tallycounter.demo.Main.OverflowPolicy;
import static io.github.guiritter.tallycounter.demo.Main.OverflowPolicy.CLEAR;
import static io.github.guiritter.tallycounter.demo.Main.OverflowPolicy.KEEP;
import static io.github.guiritter.tallycounter.demo.Main.OverflowPolicy.SET;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * OverflowPolicies combo box item that links
 * {@link io.github.guiritter.tallycounter.demo.Main.OverflowPolicy}
 * to its {@link java.lang.String} representation.
 * @author Guilherme Alan Ritter
 */
public final class OverflowPolicyItem {

    private static final OverflowPolicyItem array[];

    public final String name;

    public final OverflowPolicy value;

    public static OverflowPolicyItem[] getArray() {
        return Arrays.copyOf(array, array.length);
    }

    public static OverflowPolicyItem getByValue(OverflowPolicy value) {
        for (OverflowPolicyItem item : array) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    public OverflowPolicyItem(OverflowPolicy value, String name) {
        this.value = value;
        this.name = name;
    }

    static {
        List<OverflowPolicyItem> overflowPolicyList = new ArrayList<>();
        overflowPolicyList.add(new OverflowPolicyItem(CLEAR, "Clear"));
        overflowPolicyList.add(new OverflowPolicyItem(KEEP, "Keep"));
        overflowPolicyList.add(new OverflowPolicyItem(SET, "Set"));
        array = overflowPolicyList.toArray(new OverflowPolicyItem[]{});
    }
}
