package io.github.guiritter.tallycounter.demo;

import io.github.guiritter.tallycounter.TallyCounter.Type;
import static io.github.guiritter.tallycounter.TallyCounter.Type.NORMAL;
import static io.github.guiritter.tallycounter.TallyCounter.Type.UNIQUE_COMBINATION;
import static io.github.guiritter.tallycounter.TallyCounter.Type.UNIQUE_NUMBERS;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Types combo box item that links
 * {@link io.github.guiritter.tallycounter.TallyCounter.Type}
 * to its {@link java.lang.String} representation.
 * @author Guilherme Alan Ritter
 */
public final class TypeItem {

    private static final TypeItem array[];

    public final String name;

    public final Type value;

    public static TypeItem[] getArray() {
        return Arrays.copyOf(array, array.length);
    }

    public static TypeItem getByValue(Type value) {
        for (TypeItem item : array) {
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

    public TypeItem(Type value, String name) {
        this.value = value;
        this.name = name;
    }

    static {
        List<TypeItem> typeItemList = new ArrayList<>();
        typeItemList.add(new TypeItem(NORMAL, "Normal"));
        typeItemList.add(new TypeItem(UNIQUE_NUMBERS, "Unique numbers"));
        typeItemList.add(new TypeItem(UNIQUE_COMBINATION, "Unique combination"));
        array = typeItemList.toArray(new TypeItem[]{});
    }
}
