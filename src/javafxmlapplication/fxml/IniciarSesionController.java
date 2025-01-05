/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.fxml;

import connect4.Player;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafxmlapplication.JavaFXMLApplication;
/**
 * FXML Controller class
 *
 * @author maxim
 */
public class IniciarSesionController implements Initializable {
    public JavaFXMLApplication mainApp;
    @FXML
    private TextField tf_username;
    private PasswordField tf_password;
    @FXML
    private ToggleButton show_password;
    @FXML
    private TextField tf_password_shown;
    @FXML
    private PasswordField tf_password_hidden;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tf_password_shown.textProperty().bindBidirectional(tf_password_hidden.textProperty());
    }    
    
    @FXML
    private void boton_volver_atras(ActionEvent event) throws Exception {
        mainApp.menu_principal();
    }

    @FXML
    private void boton_iniciar_sesion(ActionEvent event) throws Exception {
        Player player = mainApp.player_manager.loginPlayer(tf_username.getText(), tf_password_shown.getText());
        
        if (player == null){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("Ha habido un error iniciando sesión.");
            alert.setContentText("No existe un usuario con estos datos.\n Asegúrate de haber introducido los datos correctamente.");
            alert.showAndWait();
            return;
        }
        
        if (mainApp.player1 == null) mainApp.player1 = player;
        else{
            if (mainApp.player1.getNickName().equals(player.getNickName())){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error.");
                alert.setHeaderText("Ha habido un error iniciando sesión.");
                alert.setContentText("Este jugador ya ha iniciado su sesión.");
                alert.showAndWait();
                return;
            } mainApp.player2 = player;
        } mainApp.menu_jugar();
    }
    @FXML
    private void boton_recuperar_contraseña(ActionEvent event) throws Exception {
        mainApp.recuperar_contraseña();
    }

    @FXML
    private void contraseña_enter(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER && 
                !tf_username.getText().equals("") &&
                !tf_password_shown.getText().equals("")) {
            boton_iniciar_sesion(null);
        }
    }

    @FXML
    private void boton_mostrar_contraseña(ActionEvent event) {
        if(show_password.isSelected()) {
            tf_password_hidden.setVisible(false);
            tf_password_shown.setVisible(true);
            tf_password_shown.requestFocus();
            tf_password_shown.selectEnd();
        } else {
            tf_password_hidden.setVisible(true);
            tf_password_shown.setVisible(false);
            tf_password_hidden.requestFocus();
            tf_password_hidden.selectEnd();
        }
    }
    
}
