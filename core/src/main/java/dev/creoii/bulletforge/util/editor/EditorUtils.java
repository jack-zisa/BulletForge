package dev.creoii.bulletforge.util.editor;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public final class EditorUtils {
    static public class NumberFilter implements TextField.TextFieldFilter {
        public static final NumberFilter INSTANCE = new NumberFilter();

        public boolean acceptChar(TextField textField, char c) {
            return Character.isDigit(c) || (textField.getCursorPosition() == 0 && c == '-') || (textField.getCursorPosition() > 0 && !textField.getText().contains(".") && c == '.');
        }
    }

    static public class IntegerFilter implements TextField.TextFieldFilter {
        public static final IntegerFilter INSTANCE = new IntegerFilter();

        public boolean acceptChar(TextField textField, char c) {
            return Character.isDigit(c) || (textField.getCursorPosition() == 0 && c == '-');
        }
    }
}
