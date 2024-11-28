package lk.ijse.ormcoursework.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.ormcoursework.bo.BOFactory;
import lk.ijse.ormcoursework.bo.custom.StudentBO;
import lk.ijse.ormcoursework.dto.StudentDto;
import lk.ijse.ormcoursework.tm.StudentTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class RejistretionController {

    @FXML
    private DatePicker btnDOB;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<StudentTm> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);


    public void initialize(){
        setCellValueFactory();
        loadAllStudents();
        cmbGender.getItems().addAll("Male", "Female");
        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setFieldsWithSelectedRowData(newValue);
            }
        });
    }

    private void setFieldsWithSelectedRowData(StudentTm selectedStudent) {
        txtId.setText(selectedStudent.getId());
        txtName.setText(selectedStudent.getName());
        txtAddress.setText(selectedStudent.getAddress());
        txtContact.setText(selectedStudent.getContact());
        btnDOB.setValue(LocalDate.parse(selectedStudent.getBirthDay()));
        cmbGender.setValue(selectedStudent.getGender());
    }

    private List<StudentDto> loadAllStudents() {
        ObservableList<StudentTm> obList = FXCollections.observableArrayList();

        try {
            List<StudentDto> studentList = studentBO.getAllStudents();

            for (StudentDto studentDto : studentList) {
                String formattedBirthDay = studentDto.getBirthDay().toString();
                StudentTm studentTm = new StudentTm(
                        studentDto.getId(),
                        studentDto.getName(),
                        studentDto.getAddress(),
                        studentDto.getContact(),
                        formattedBirthDay,
                        studentDto.getGender()
                );

                obList.add(studentTm);
            }

            tblStudent.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading users: " + e.getMessage(), ButtonType.OK).show();
        }
        return null;
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        String id = txtId.getText();
        if (result.orElse(no) == yes) {
            if (!studentBO.delete(id)) {
                new Alert(Alert.AlertType.ERROR, "Error!!").show();
            }
        }
//        generateNextUserId();
        clearFields();
        loadAllStudents();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean isValidate = validateRejister();
        if (isValidate) {
            boolean isSaved = studentBO.save(new StudentDto(
                    txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    btnDOB.getValue(),
                    (String) cmbGender.getValue()
            ));
            if (isSaved) {
                loadAllStudents();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "User Saved").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "User UnSaved").show();
            }
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        btnDOB.setValue(null);
        cmbGender.getSelectionModel().clearSelection();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException{
        String sid=txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        LocalDate dob = LocalDate.parse(String.valueOf(btnDOB.getValue()));
        String gender = cmbGender.getValue();

        if(studentBO.update(new StudentDto(sid,name,address,contact,dob,gender))){
            new Alert(Alert.AlertType.CONFIRMATION, "Update Successfully!!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Error!!").show();
        }
        clearFields();
        loadAllStudents();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }
    private boolean validateRejister() {
        int num=0;
        String id = txtId.getText();
        boolean isIDValidate= Pattern.matches("[0-9]{2,7}",id);
        if (!isIDValidate){
            num=1;
            vibrateTextField(txtId);
        }

        String name=txtName.getText();
        boolean isNameValidate= Pattern.matches("[A-z]{3,}",name);
        if (!isNameValidate){
            num=1;
            vibrateTextField(txtName);
        }

        String address=txtAddress.getText();
        boolean isAddressValidate= Pattern.matches("[A-z]{3,}",address);
        if (!isAddressValidate){
            num=1;
            vibrateTextField(txtAddress);
        }


        String contact=txtContact.getText();
        boolean isContactValidate= Pattern.matches("[0-9]{10}",contact);
        if (!isContactValidate){
            num=1;
            vibrateTextField(txtContact);
        }

//        String dob= String.valueOf(btnDOB.getValue());
//        boolean isDobValidate= Pattern.matches("[0-9]{10}",dob);
//        if (!isDobValidate){
//            num=1;
//            vibrateTextField(btnDOB.getEditor());
//        }

        String gender= String.valueOf(cmbGender.getValue());
        boolean isGenderValidate= Pattern.matches("[A-z /]{2,}",gender);
        if (!isGenderValidate){
            num=1;
            vibrateTextField(btnDOB.getEditor());
        }

        if(num==1){
            num=0;
            return false;
        }else {
            num=0;
            return true;

        }
    }

    private void vibrateTextField(TextField textField) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), new KeyValue(textField.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(50), new KeyValue(textField.translateXProperty(), -6)),
                new KeyFrame(Duration.millis(100), new KeyValue(textField.translateXProperty(), 6)),
                new KeyFrame(Duration.millis(150), new KeyValue(textField.translateXProperty(), -6)),
                new KeyFrame(Duration.millis(200), new KeyValue(textField.translateXProperty(), 6)),
                new KeyFrame(Duration.millis(250), new KeyValue(textField.translateXProperty(), -6)),
                new KeyFrame(Duration.millis(300), new KeyValue(textField.translateXProperty(), 6)),
                new KeyFrame(Duration.millis(350), new KeyValue(textField.translateXProperty(), -6)),
                new KeyFrame(Duration.millis(400), new KeyValue(textField.translateXProperty(), 0))

        );

        textField.setStyle("-fx-border-color: red;");
        timeline.play();

        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.seconds(3), new KeyValue(textField.styleProperty(), "-fx-border-color: #bde0fe;"))
        );

        timeline1.play();
    }

}
