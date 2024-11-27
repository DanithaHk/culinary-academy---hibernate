package lk.ijse.ormcoursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EnrolmentController {

    @FXML
    private ComboBox<?> cmbCourseName;

    @FXML
    private ComboBox<?> cmbStudentId;

    @FXML
    private TableColumn<?, ?> colComent;

    @FXML
    private TableColumn<?, ?> colCourseName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEnrolmentId;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colTotalFee;

    @FXML
    private TableColumn<?, ?> colUpfrontPayment;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<?> tblEnrolment;

    @FXML
    private TextField txtComment;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEnrolmentId;

    @FXML
    private TextField txtFee;



    @FXML
    private TextField txtStudentName;

    @FXML
    private TextField txtUpfrontPayment;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

}
