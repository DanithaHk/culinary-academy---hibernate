package lk.ijse.ormcoursework.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.ormcoursework.bo.BOFactory;
import lk.ijse.ormcoursework.bo.custom.ProgramsBO;
import lk.ijse.ormcoursework.dto.ProgramsDto;
import lk.ijse.ormcoursework.tm.ProgramsTm;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CoursesController {

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<ProgramsTm> tblcourse;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;
    ProgramsBO programsBO = (ProgramsBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROGRAMS);


    public void initialize(){
        setCellValueFactory();
        loadAllPrograms();

        tblcourse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setFieldsWithSelectedRowData(newValue);
            }
        });
    }

    private void setFieldsWithSelectedRowData(ProgramsTm newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtDuration.setText(newValue.getDuration());
        txtFee.setText(String.valueOf(newValue.getFee()));
    }

    private void loadAllPrograms() {
        ObservableList<ProgramsTm> obList = FXCollections.observableArrayList();

        try {
            List<ProgramsDto> programsList = programsBO.getAllPrograms();

            for (ProgramsDto programsDto : programsList) {

                ProgramsTm programsTm = new ProgramsTm(
                        programsDto.getId(),
                        programsDto.getName(),
                        programsDto.getDuration(),
                        programsDto.getFee()
                );

                obList.add(programsTm);
            }

            tblcourse.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading programss: " + e.getMessage(), ButtonType.OK).show();
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException{
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        String id = txtId.getText();
        if (result.orElse(no) == yes) {
            if (!programsBO.delete(id)) {
                new Alert(Alert.AlertType.ERROR, "Error!!").show();
            }
        }
//        generateNextUserId();
        clearFields();
        loadAllPrograms();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean isSaved = programsBO.save(new ProgramsDto(
                txtId.getText(),
                txtName.getText(),
                txtDuration.getText(),
                Double.valueOf(txtFee.getText())
        ));
        if (isSaved) {
            loadAllPrograms();
            clearFields();
            new Alert(Alert.AlertType.CONFIRMATION, "User Saved").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "User UnSaved").show();
        }
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtDuration.clear();
        txtFee.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String pid=txtId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        Double fee = Double.valueOf(txtFee.getText());

        if(programsBO.update(new ProgramsDto(pid,name,duration,fee))){
            new Alert(Alert.AlertType.CONFIRMATION, "Update Successfully!!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Error!!").show();
        }
        clearFields();
        loadAllPrograms();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

}
