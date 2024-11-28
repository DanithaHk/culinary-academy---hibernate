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
import lk.ijse.ormcoursework.bo.custom.ProgramsBO;
import lk.ijse.ormcoursework.bo.custom.StudentBO;
import lk.ijse.ormcoursework.dto.EnrollmentDto;
import lk.ijse.ormcoursework.entity.Programs;
import lk.ijse.ormcoursework.entity.Student;
import lk.ijse.ormcoursework.tm.EnrollmentTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EnrolmentController {

    @FXML
    private ComboBox<String> cmbCourseId;

    @FXML
    private ComboBox<String> cmbStudentId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEnrollmentId;

    @FXML
    private TableColumn<?, ?> colProgram;

    @FXML
    private TableColumn<?, ?> colProgramId;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colTotalFee;

    @FXML
    private TableColumn<?, ?> colUpfrontFee;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EnrollmentTm> tblEnrollment;

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
    private TextField txtcourseName;
    ObservableList<EnrollmentTm> observableList;
    String ID;
    EnrollmentBO enrollmentBo = (EnrollmentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ENROLLMENT);
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);
    ProgramsBO programsBO = (ProgramsBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROGRAMS);

    public void initialize(){
        try {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            txtDate.setText(today.format(formatter));
            getAllEnrollments();
            loadStudentIds();
            loadProgramsIds();
            setCellValueFactory();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setCellValueFactory();
        generateNextUserId();

    }


    private void generateNextUserId() {
        String nextId = null;
        try {
            nextId = enrollmentBo.generateNewEnrollmentID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        txtEnrolmentId.setText(nextId);
    }

    private void setCellValueFactory() {
        colEnrollmentId.setCellValueFactory(new PropertyValueFactory<>("eid"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("sid"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("Studentname"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("Coursename"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotalFee.setCellValueFactory(new PropertyValueFactory<>("remainingfee"));
        colUpfrontFee.setCellValueFactory(new PropertyValueFactory<>("upfrontpayment"));
    }

    private void loadProgramsIds() throws SQLException, ClassNotFoundException {
        List<String> programIds = programsBO.getAllProgramIds();
        cmbCourseId.getItems().clear();
        cmbCourseId.getItems().addAll(programIds);
    }

    private void loadStudentIds() throws SQLException, ClassNotFoundException {
        List<String> studentIds = studentBO.getAllStudentIds();
        cmbStudentId.getItems().clear();
        cmbStudentId.getItems().addAll(studentIds);
    }

    private void getAllEnrollments() throws SQLException, ClassNotFoundException {
//        tblEnrollment.getItems().clear();
//        observableList = FXCollections.observableArrayList();
//        List<EnrollmentDto> allenrollment = enrollmentBo.getAllEnrollment();
//
//        for (EnrollmentDto enrollmentDTO : allenrollment){
//            observableList.add(new EnrollmentTm(enrollmentDTO.getEid(),enrollmentDTO.getSid(),enrollmentDTO.getStudentname(),enrollmentDTO.getCid(),enrollmentDTO.getCoursename(),enrollmentDTO.getDate(),enrollmentDTO.getUpfrontpayment(),enrollmentDTO.getRemainingfee()));
//            tblEnrollment.setItems(observableList);
//        }
        ObservableList<EnrollmentTm> obList = FXCollections.observableArrayList();

        try {
            List<EnrollmentDto> enrollmentList = enrollmentBo.getAllEnrollment();

            for (EnrollmentDto enrollmentDto : enrollmentList) {

                EnrollmentTm enrollmentTm = new EnrollmentTm(
                        enrollmentDto.getEid(),
                        enrollmentDto.getSid(),
                        enrollmentDto.getStudentname(),
                        enrollmentDto.getCid(),
                        enrollmentDto.getCoursename(),
                        enrollmentDto.getDate(),
                        enrollmentDto.getUpfrontpayment(),
                        enrollmentDto.getRemainingfee()
                );

                obList.add(enrollmentTm);
            }

            tblEnrollment.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading programss: " + e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        String id = txtEnrolmentId.getText();
        String sid = cmbStudentId.getValue();
        String studentname = txtStudentName.getText();
        String cid = cmbCourseId.getValue();
        String coursename = txtcourseName.getText();
        LocalDate date = LocalDate.parse(txtDate.getText());
        Double totalfee = Double.valueOf(txtFee.getText());
        Double upfrontpayment = Double.valueOf(txtUpfrontPayment.getText());
        Double remainingfee = totalfee - upfrontpayment;

        if (enrollmentBo.EnrollmentIdExists(id)){
            new Alert(Alert.AlertType.ERROR, "Enrollment ID " + id + " already exists!").show();
            return;
        }

        if (enrollmentBo.isStudentEnrolledInCourse(sid, cid)) {
            new Alert(Alert.AlertType.ERROR, "Student " + sid + " is already enrolled in course " + cid + "!").show();
            return;
        }

        if (enrollmentBo.saveEnrollment(new EnrollmentDto(id, sid,studentname,cid,coursename,date,upfrontpayment,remainingfee))) {
            clearTextFileds();
            getAll();
            loadStudentIds();
            loadProgramsIds();
            new Alert(Alert.AlertType.CONFIRMATION, "Saved!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error!!").show();
        }
    }

    private void getAll() throws SQLException, ClassNotFoundException {
        tblEnrollment.getItems().clear();
        observableList = FXCollections.observableArrayList();
        List<EnrollmentDto> allenrollment = enrollmentBo.getAllEnrollment();

        for (EnrollmentDto enrollmentDTO : allenrollment){
            observableList.add(new EnrollmentTm(enrollmentDTO.getEid(),enrollmentDTO.getSid(),enrollmentDTO.getStudentname(),enrollmentDTO.getCid(),enrollmentDTO.getCoursename(),enrollmentDTO.getDate(),enrollmentDTO.getUpfrontpayment(),enrollmentDTO.getRemainingfee()));
            tblEnrollment.setItems(observableList);
        }
    }

    private void clearTextFileds() throws SQLException, ClassNotFoundException {
        txtEnrolmentId.clear();
        cmbStudentId.getItems().clear();
        txtStudentName.clear();
        cmbCourseId.getItems().clear();
        txtcourseName.clear();
        txtDate.clear();
        txtUpfrontPayment.clear();
        txtFee.clear();
        loadStudentIds();
        loadProgramsIds();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        txtDate.setText(today.format(formatter));
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    public void handleCourse(ActionEvent event) throws Exception {
        String selectedProgramId = cmbCourseId.getValue();

        if (selectedProgramId != null && !selectedProgramId.isEmpty()) {
            Programs selectedProgram = programsBO.getProgramById(selectedProgramId);

            if (selectedProgram != null) {
                String courseName = selectedProgram.getName();
                Double fee = selectedProgram.getFee();
                System.out.println("Course ID: " + selectedProgram.getId());
                System.out.println("Course Name: " + courseName);
                System.out.println("Course fee: " + fee);

                txtcourseName.setText(courseName);
                txtFee.setText(String.valueOf(fee));

            } else {
                System.out.println("Course not found for ID: " + selectedProgramId);
            }
        }
    }

    public void handleStudent(ActionEvent event) throws Exception {
        String selectedStudentId = cmbStudentId.getValue();

        if (selectedStudentId != null && !selectedStudentId.isEmpty()) {
            Student selectedStudent = studentBO.getStudentById(selectedStudentId);

            if (selectedStudent != null) {
                String studentName = selectedStudent.getName();
                System.out.println("Course ID: " + selectedStudent.getId());
                System.out.println("Course Name: " + studentName);

                txtStudentName.setText(studentName);


            } else {
                System.out.println("Student not found for ID: " + selectedStudent);
            }
        }
    }

}
