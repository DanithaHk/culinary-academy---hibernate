package lk.ijse.ormcoursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ormcoursework.Utill.PasswordVerifier;
import lk.ijse.ormcoursework.bo.BOFactory;
import lk.ijse.ormcoursework.bo.custom.UserBO;
import lk.ijse.ormcoursework.dao.Custom.UserDAO;
import lk.ijse.ormcoursework.dao.DAOFactory;
import lk.ijse.ormcoursework.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginController {


    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);


    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

//        String dbpw= userBO.findUserByname(username).getPassword();
        try {
//            if (PasswordVerifier.verifyPassword(password,dbpw)){
            if (userDAO.checkPassword(username,password)){
                if (userBO.findUserByname(username).getRole().equals("Admin")) {
                    AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/adminDashbord.fxml"));

                    Scene scene = new Scene(rootNode);

                    Stage stage = (Stage) this.rootNode.getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.setTitle("Dashboard Form");

                } else {
                    AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/codinaterDashbord.fxml"));

                    Scene scene = new Scene(rootNode);

                    Stage stage = (Stage) this.rootNode.getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.setTitle("Dashboard Form");
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Please Check Username and password !!").show();
            }


            txtPassword.clear();
        } catch(IOException e){
                throw new RuntimeException(e);
            }
        }


    @FXML
    void linkFrogetPasswordOnAction(ActionEvent event) {
        String username  = txtUserName.getText().trim();
        if(!txtUserName.getText().isEmpty()){
            User userByname = userBO.findUserByname(username);
            if(userByname != null){


                if (true) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Password Reset");
                    alert.setHeaderText(null);
                    alert.setContentText("Password reset link has been sent to your email.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The provided email is not registered.");
                    alert.showAndWait();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Username does not exist! Please check your username.").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Username feild is empty!give username to reset password").show();

        }
    }


}
