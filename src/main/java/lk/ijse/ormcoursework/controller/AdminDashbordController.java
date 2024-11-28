package lk.ijse.ormcoursework.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.ormcoursework.bo.BOFactory;
import lk.ijse.ormcoursework.bo.custom.EnrollmentBO;
import lk.ijse.ormcoursework.bo.custom.ProgramsBO;
import lk.ijse.ormcoursework.bo.custom.StudentBO;
import lk.ijse.ormcoursework.dto.ProgramsDto;
import lk.ijse.ormcoursework.tm.ProgramsTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminDashbordController {

    @FXML
    private AnchorPane Load;

    @FXML
    private TableColumn<?, ?> colDay;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colRoute;

    @FXML
    private TableColumn<?, ?> colVanId;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblCusCount;

    @FXML
    private Label lblEmpCount;

    @FXML
    private Label lblTime;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<ProgramsTm> tblVan;

    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);
    EnrollmentBO enrollmentBO = (EnrollmentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ENROLLMENT);

    ProgramsBO programsBO = (ProgramsBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROGRAMS);
    public void initialize(){
        initClock();
        setCellValueFactory();
        try {
            int studentCount = studentBO.getStudentCount();
            lblCusCount.setText(String.valueOf(studentCount));

            int enrolmentCount = enrollmentBO.getEnrollmentCount();
            lblAmount.setText(String.valueOf(enrolmentCount));

            loadAllPrograms();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colVanId.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colRoute.setCellValueFactory(new PropertyValueFactory<>("fee"));

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

            tblVan.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading programss: " + e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnCoursesOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/course.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/adminDashbord.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnExpensesOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/enrollment.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login.fxml"));

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/payment.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnRejistretionOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/rejistertion.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }

    @FXML
    void btnUserOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/user.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Load.getChildren().clear();
        Load.getChildren().add(load);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Load);
        transition.setFromX(load.getScene().getWidth());
        transition.setToX(0);
        transition.play();
    }
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd        HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

}
