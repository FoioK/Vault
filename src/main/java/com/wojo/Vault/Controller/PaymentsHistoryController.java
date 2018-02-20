package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Service.PaymentService;
import com.wojo.Vault.Service.impl.PaymentServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentsHistoryController {

    @FXML
    private AnchorPane scrollPane;

    @FXML
    private JFXButton backToAccountsPane;

    @FXML
    private Label numberPrettyPaint;

    private static final Integer SCROLL_PANE_WIDTH = 1050;
    private static final Integer HEADER_PANE_HEIGHT = 150;
    private static final Integer PAYMENT_ROW_HEIGHT = 100;

    private RootController rootController;

    private PaymentService paymentService = new PaymentServiceImpl();
    private List<Payment> allPayments = new ArrayList<>();

    @FXML
    void initialize() {
        numberPrettyPaint.setText(getFormatAccountNumber());
        allPayments = getPaymentsList();

        backToAccountsPane.addEventHandler(ActionEvent.ACTION, e ->
                rootController.loadDesktopPane());

        showPaymentsHistoryProcess();
    }

    private String getFormatAccountNumber() {
        return paymentService.getFormatAccountNumber();
    }

    private List<Payment> getPaymentsList() {
        return paymentService.getAllPayment();
    }

    private void showPaymentsHistoryProcess() {
        if (allPayments.size() > 5) {
            scrollPane.setPrefSize(SCROLL_PANE_WIDTH,
                    (allPayments.size() * PAYMENT_ROW_HEIGHT)
                            + HEADER_PANE_HEIGHT + 100);
        }
        final int[] counter = {1};
        allPayments.forEach(payment -> {
            Pane pane = new Pane();
            pane.setPrefSize(SCROLL_PANE_WIDTH, PAYMENT_ROW_HEIGHT);
            pane.setLayoutY(HEADER_PANE_HEIGHT + (counter[0] * PAYMENT_ROW_HEIGHT));
            addComponentToPane(pane, payment);
            scrollPane.getChildren().add(pane);
            counter[0] += 1;
        });
    }

    private void addComponentToPane(Pane pane, Payment payment) {
        Line separator = new Line(0, 0, SCROLL_PANE_WIDTH, 0);

        Label date = new Label(payment.getDate().toString());
        date.setPrefSize(250, 30);
        date.setLayoutX(10);
        date.setLayoutY(40);
        date.setAlignment(Pos.CENTER);

        Label recipientOrSender =
                new Label(payment.getPaymentValue().compareTo(BigDecimal.ZERO) < 0 ?
                        payment.getRecipientName() : payment.getSenderName());
        recipientOrSender.setPrefSize(300, 30);
        recipientOrSender.setLayoutX(405);
        recipientOrSender.setLayoutY(15);
        recipientOrSender.setAlignment(Pos.CENTER);

        Label title = new Label(payment.getTitle());
        title.setPrefSize(480, 30);
        title.setLayoutX(315);
        title.setLayoutY(55);
        title.setAlignment(Pos.CENTER);

        Label value = new Label(payment.getPaymentValue().toString() + " PLN");
        if (payment.getPaymentValue().compareTo(BigDecimal.ZERO) < 0) {
            value.setTextFill(Color.RED);
        }
        value.setPrefSize(200, 30);
        value.setLayoutX(820);
        value.setLayoutY(40);
        value.setAlignment(Pos.CENTER);

        pane.setStyle("-fx-background-color: #FFFFFF;");
        pane.getChildren().add(separator);
        pane.getChildren().add(date);
        pane.getChildren().add(recipientOrSender);
        pane.getChildren().add(title);
        pane.getChildren().add(value);
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
