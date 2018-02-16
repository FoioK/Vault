package com.wojo.Vault.Filters;

import com.jfoenix.controls.JFXTextField;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class TextFieldFilterTest extends ApplicationTest {

    @Test
    public void lengthLimiterTest() {

        String textWithFifteenChar = "1234567890asdfg";

        JFXTextField maxTwo = new JFXTextField();
        TextFieldFilter.lengthLimiter(maxTwo, 2);
        maxTwo.setText(textWithFifteenChar);
        assertEquals(2, maxTwo.getText().length());

        JFXTextField maxFive = new JFXTextField();
        TextFieldFilter.lengthLimiter(maxFive, 5);
        maxFive.setText(textWithFifteenChar);
        assertEquals(5, maxFive.getText().length());

        JFXTextField maxTen = new JFXTextField();
        TextFieldFilter.lengthLimiter(maxTen, 10);
        maxTen.setText(textWithFifteenChar);
        assertEquals(10, maxTen.getText().length());
    }

    private static final Integer TEXT_LENGTH = 6;

    @Test
    public void typeIntegerTest() {
        String textWithTwoInvalidChar = "77a65W90";
        JFXTextField twoInvalidChar = new JFXTextField();
        TextFieldFilter.typeInteger(twoInvalidChar);
        for (int i = 0; i < textWithTwoInvalidChar.length(); i++) {
            String actualText = twoInvalidChar.getText();
            twoInvalidChar.setText(actualText + textWithTwoInvalidChar.substring(i, i + 1));
        }
        assertEquals((int)TEXT_LENGTH, twoInvalidChar.getText().length());

        String textWithOneInvalidChar = "33Y4792";
        JFXTextField oneInvalidChar = new JFXTextField();
        TextFieldFilter.typeInteger(oneInvalidChar);
        for (int i = 0; i < textWithOneInvalidChar.length(); i++) {
            String actualText = oneInvalidChar.getText();
            oneInvalidChar.setText(actualText + textWithOneInvalidChar.substring(i, i + 1));
        }
        assertEquals((int)TEXT_LENGTH, oneInvalidChar.getText().length());

        String textWithOnlyInvalidChar = "aCf";
        JFXTextField allInvalidChar = new JFXTextField();
        TextFieldFilter.typeInteger(allInvalidChar);
        for (int i = 0; i < textWithOnlyInvalidChar.length(); i++) {
            String actualText = allInvalidChar.getText();
            allInvalidChar.setText(actualText + textWithOnlyInvalidChar.substring(i, i + 1));
        }
        assertEquals(0, allInvalidChar.getText().length());
    }

    @Test
    public void typeStringTest() {
        String textWithTwoInvalidChar = "AB7DhU8I";
        JFXTextField twoInvalidChar = new JFXTextField();
        TextFieldFilter.typeString(twoInvalidChar);
        for (int i = 0; i < textWithTwoInvalidChar.length(); i++) {
            String actualText = twoInvalidChar.getText();
            twoInvalidChar.setText(actualText + textWithTwoInvalidChar.substring(i, i + 1));
        }
        assertEquals((int)TEXT_LENGTH, twoInvalidChar.getText().length());

        String textWithOneInvalidChar = "qa5HcGT";
        JFXTextField oneInvalidChar = new JFXTextField();
        TextFieldFilter.typeString(oneInvalidChar);
        for (int i = 0; i < textWithOneInvalidChar.length(); i++) {
            String actualText = oneInvalidChar.getText();
            oneInvalidChar.setText(actualText + textWithOneInvalidChar.substring(i, i + 1));
        }
        assertEquals((int)TEXT_LENGTH, oneInvalidChar.getText().length());

        String textWithOnlyInvalidChar = "99762";
        JFXTextField allInvalidChar = new JFXTextField();
        TextFieldFilter.typeString(allInvalidChar);
        for (int i = 0; i < textWithOnlyInvalidChar.length(); i++) {
            String actualText = allInvalidChar.getText();
            allInvalidChar.setText(actualText + textWithOnlyInvalidChar.substring(i, i + 1));
        }
        assertEquals(0, allInvalidChar.getText().length());
    }
}