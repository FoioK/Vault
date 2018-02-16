package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.Filters.TextFieldFilter;
import com.wojo.Vault.Service.PaymentService;
import com.wojo.Vault.Service.impl.PaymentServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.math.BigDecimal;

public class PaymentsController {

    private RootController rootController;
    private PaymentService paymentService = new PaymentServiceImpl();


    @FXML
    private JFXButton backToDesktopPane;

    @FXML
    private JFXTextField recipientName;

    @FXML
    private Label badRecipientNameMessage;

    @FXML
    private JFXTextField recipientAccountNumber;

    @FXML
    private Label badRecipientNumberMessage;

    @FXML
    private JFXTextField paymentValue;

    @FXML
    private Label badPaymentValueMessage;

    @FXML
    private JFXTextField title;

    @FXML
    private Label badTitleMessage;

    @FXML
    private JFXButton sendTransfer;

    @FXML
    private Label badSendTransferMessage;

    @FXML
    void initialize() {
        initTextLimiters();
        setErrorMessage(false);
        addEventHandlers();
    }

    private void initTextLimiters() {
        TextFieldFilter.lengthLimiter(recipientName, 75);

        //TODO split entered text
        TextFieldFilter.lengthLimiter(recipientAccountNumber, 26);
        TextFieldFilter.typeInteger(recipientAccountNumber);

        //TODO entered value with ','
        TextFieldFilter.lengthLimiter(paymentValue, 15);

        TextFieldFilter.lengthLimiter(title, 75);
    }

    private void setErrorMessage(boolean state) {
        badRecipientNameMessage.setVisible(state);
        badRecipientNumberMessage.setVisible(state);
        badPaymentValueMessage.setVisible(state);
        badTitleMessage.setVisible(state);
        badSendTransferMessage.setVisible(state);
    }

    private void addEventHandlers() {
        sendTransfer.addEventHandler(ActionEvent.ACTION, e -> {
            if (sendTransferProcess()) {
                loadDesktopPane();
            } else {
                badSendTransferMessage.setVisible(true);
            }
        });

        backToDesktopPane.addEventHandler(ActionEvent.ACTION, e -> {
            loadDesktopPane();
        });
    }

    private boolean sendTransferProcess() {
        setErrorMessage(false);
        return checkData() && sendTransfer();
    }

    public boolean checkData() {
        boolean isCorrect = true;
        if (recipientName.getText().equals("")) {
            badRecipientNameMessage.setVisible(true);
            isCorrect = false;
        }
        if (recipientAccountNumber.getText().equals("")
                || recipientAccountNumber.getText().length() != 26) {
            badRecipientNumberMessage.setVisible(true);
            isCorrect = false;
        }
        if (paymentValue.getText().equals("")) {
            badPaymentValueMessage.setVisible(true);
            isCorrect = false;
        } else {
            try {
                if (new BigDecimal(paymentValue.getText())
                        .compareTo(new BigDecimal("0")) <= 0) {
                    badPaymentValueMessage.setVisible(true);
                    isCorrect = false;
                }
            } catch (NumberFormatException e) {
                badPaymentValueMessage.setVisible(true);
                isCorrect = false;
            }
        }
        if (title.getText().equals("")) {
            badTitleMessage.setVisible(true);
            isCorrect = false;
        }
        return isCorrect;
    }

    private boolean sendTransfer() {
        return paymentService.sendTransfer(recipientName.getText()
                , recipientAccountNumber.getText(), title.getText()
                , new BigDecimal(paymentValue.getText()));
    }

    private void loadDesktopPane() {
        rootController.loadDesktopPane();
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
