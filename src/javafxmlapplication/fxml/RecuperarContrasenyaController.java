/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.fxml;

import connect4.Connect4;
import connect4.Player;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafxmlapplication.JavaFXMLApplication;

/**
 * FXML Controller class
 *
 * @author maxim
 */
public class RecuperarContrasenyaController implements Initializable {

    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_email;
    
    @FXML
    private TextField tf_codigo;
    
    
    @FXML
    private Label texto_codigo;

    public JavaFXMLApplication mainApp;
    @FXML
    private Button boton_validar;
    
    private int codigo;
    
    private Player player;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void boton_volver_atras(ActionEvent event) throws Exception {
        mainApp.iniciar_sesion();
    }

    @FXML
    private void boton_mandar(ActionEvent event) {
        try{
            player = Connect4.getInstance().getPlayer(tf_username.getText());
            if (player == null){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error.");
                alert.setHeaderText(null);
                alert.setContentText("No existe una cuenta con este nombre.");
                alert.showAndWait();
                return;
            }
            
            if (!tf_email.getText().equals(player.getEmail())){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error.");
                alert.setHeaderText(null);
                alert.setContentText("Correo electrónico incorrecto.\n Asegúrate de haberlo introducido correctamente.");
                alert.showAndWait();
                return;
            }
            
            if (tf_email.getText().equals(player.getEmail())){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Email.");
                    alert.setHeaderText("Email con código.");
                    codigo = ThreadLocalRandom.current().nextInt(0, 1000);
                    alert.setContentText(Integer.toString(codigo));
                    alert.showAndWait();
                    
                    texto_codigo.setVisible(true);
                    tf_codigo.setVisible(true);
                    boton_validar.setVisible(true);
            }
        } catch (Exception e){}
    }

    @FXML
    private void validar(ActionEvent event) throws Exception {
        if (Integer.toString(codigo).equals(tf_codigo.getText())){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Contraseña");
            alert.setHeaderText("Código correcto, su contraseña:");
            alert.setContentText(player.getPassword());
            alert.showAndWait();
            mainApp.iniciar_sesion();
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Código incorrecto.");
            alert.showAndWait();
        
        }
    }

    @FXML
    private void email_enter(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER && 
                !tf_username.getText().equals("") &&
                !tf_email.getText().equals("")) {
            boton_mandar(null);
        }
    }

    @FXML
    private void codigo_enter(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER && 
                !tf_codigo.getText().equals("")) {
            validar(null);
        }
    }
    
}
