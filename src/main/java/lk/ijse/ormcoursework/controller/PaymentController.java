package lk.ijse.ormcoursework.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.ormcoursework.bo.BOFactory;
import lk.ijse.ormcoursework.bo.custom.EnrollmentBO;
import lk.ijse.ormcoursework.bo.custom.PaymentBO;
import lk.ijse.ormcoursework.dto.PaymentDto;
import lk.ijse.ormcoursework.tm.PaymentTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PaymentController {

    @FXML
    private ComboBox<String> cmbEnrollmentId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEnrollmentId;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<PaymentTm> tblcourse;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtPaymentId;

    ObservableList<PaymentTm> observableList;
    String ID;
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);
    EnrollmentBO enrollmentBo = (EnrollmentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ENROLLMENT);

    public void initialize() {
        try {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            txtDate.setText(today.format(formatter));
            getAll();
            loadEnrollmentIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        setCellValueFactory();
        generateNextPaymentId();
    }

    private void generateNextPaymentId() {
        String nextId = null;
        try {
            nextId = paymentBO.generateNewPaymentID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtPaymentId.setText(nextId);
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEnrollmentId.setCellValueFactory(new PropertyValueFactory<>("eid"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadEnrollmentIds() throws SQLException, ClassNotFoundException {
        List<String> enrollmentIds = enrollmentBo.getAllEnrollmentIds();
        cmbEnrollmentId.getItems().clear();
        cmbEnrollmentId.getItems().addAll(enrollmentIds);
    }

    private void getAll() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<PaymentDto> allPayment = paymentBO.getAllPayment();

        for (PaymentDto paymentDTO : allPayment){
            observableList.add(new PaymentTm(paymentDTO.getId(),paymentDTO.getEid(),paymentDTO.getAmount(),paymentDTO.getDate()));
        }
        tblcourse.setItems(observableList);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        String id = txtPaymentId.getText();
        String eid = cmbEnrollmentId.getValue();
        Double amount = Double.valueOf(txtAmount.getText());
        LocalDate date = LocalDate.parse(txtDate.getText());

        if (paymentBO.PaymentIdExists(id)){
            new Alert(Alert.AlertType.ERROR, "Payment ID " + id + " already exists!").show();
            return;
        }

        if(amount > (enrollmentBo.findEnrollmentById(eid).getRemainingfee())){
            new Alert(Alert.AlertType.ERROR, "Payment exceeds the remaining fee. Please enter a valid amount!").show();
            return;
        }

        if (paymentBO.savePayment(new PaymentDto(id,eid,amount,date))) {
            updateremainfee();
            clearTextFileds();
            loadEnrollmentIds();
            generateNextPaymentId();
            getAll();
            new Alert(Alert.AlertType.CONFIRMATION, "Saved!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error!!").show();
        }
    }

    private void clearTextFileds() {
        txtPaymentId.clear();
        cmbEnrollmentId.getItems().clear();
        txtAmount.clear();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        txtDate.setText(today.format(formatter));
        generateNextPaymentId();
    }

    private void updateremainfee() {
        try {
            String eid = cmbEnrollmentId.getValue();
            double paymentAmount = Double.parseDouble(txtAmount.getText());

            double currentRemainFee = enrollmentBo.getRemainingFeeByEnrollmentId(eid);

            double updatedRemainFee = currentRemainFee - paymentAmount;

            boolean isUpdated = enrollmentBo.updateRemainingFee(eid, updatedRemainFee);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Remaining fee updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update remaining fee!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error updating remaining fee: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

}
