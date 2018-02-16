package com.wojo.Vault.Filters;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class TextFieldFilter {

    public static <T extends TextField> void lengthLimiter(final T field
            , final int maxLength) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov
                    , final String oldValue, final String newValue) {
                if (field.getText().length() > maxLength) {
                    String s = field.getText().substring(0, maxLength);
                    field.setText(s);
                }
            }
        });
    }

    public static <T extends TextField> void typeInteger(final T field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable
                    , String oldValue, String newValue) {
                if (newValue.length() < 1) {
                    field.setText("");
                    return;
                }
                char c = newValue.charAt(newValue.length() - 1);
                if (c < '0' || c > '9') {
                    setText((T) field);
                }
            }
        });
    }

    public static <T extends TextField> void typeString(final T field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable
                    , String oldValue, String newValue) {
                if (newValue.length() < 1) {
                    field.setText("");
                    return;
                }
                char c = newValue.charAt(newValue.length() - 1);
                if (c != ' ' && (c < 'A' || c > 'z') && (c < 'a' || c > 'Z')) {
                    setText((T) field);
                }
            }
        });
    }

    private static <T extends TextField> void setText(T field) {
        try {
            String s = field.getText().substring(0, field.getText().length() - 1);
            field.setText(s);
        } catch (StringIndexOutOfBoundsException e) {
            field.setText("");
            throw e;
        }
    }
}
