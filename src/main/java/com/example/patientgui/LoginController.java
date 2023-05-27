package com.example.patientgui;
import com.example.patientgui.dao.impl.UserDaoImpl;
import com.example.patientgui.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    UserDaoImpl userDao = new UserDaoImpl() ;

    @FXML
    private Label Text ;
    @FXML
    private TextField username ;
    @FXML
    private PasswordField password ;
    @FXML
    private Button loginButton ;
    @FXML
    protected void onLogInButtonClick() throws Exception
    {
        User user = userDao.getUserByUserName(username.getText()) ;
        if (user == null)
            Text.setText("user not found");
        else if (user.getPassword().equals(password.getText())) {
            loginButton.setOnAction(event->{
                Parent root ;
                try {
                    root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root) ;
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow() ;
                window.setFullScreen(true);
                window.setScene(scene);
                window.show();
            });
        }
        else
            Text.setText("password incorrect");

    }

    @FXML
    protected void onResetButtonClick()
    {
        password.setText("");
        username.setText("");
        Text.setText("");

    }
    @FXML
    protected void onresetButtonClick()
    {
        username.setText("");
        password.setText("");

    }

}
