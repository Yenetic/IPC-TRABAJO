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
import javafx.scene.control.TextField;
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
    @FXML
    private TextField tf_password;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    @FXML
    private void boton_volver_atras(ActionEvent event) throws Exception {
        mainApp.menu_principal();
    }

    @FXML
    private void boton_iniciar_sesion(ActionEvent event) throws Exception {
        Player player = mainApp.player_manager.loginPlayer(tf_username.getText(), tf_password.getText());
        if (player == null){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error iniciando sesion");
            alert.setContentText("No existe un usuario con estos datos.");
            alert.showAndWait();
            return;
        }
        if (mainApp.player1 == null){
            mainApp.player1 = player;
        }
        else{
            if (mainApp.player1.getNickName().equals(player.getNickName())){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error iniciando sesion");
                alert.setContentText("Este usuario es el jugador primario.");
                alert.showAndWait();
                return;
            
            }
            mainApp.player2 = player;
        }
        mainApp.menu_jugar();
    }
    @FXML
    private void boton_recuperar_contrasenya(ActionEvent event) throws Exception {
        mainApp.recuperar_contrasenya();
    }
    
    
}
