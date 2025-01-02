/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication.fxml;

import connect4.Connect4;
import connect4.Player;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import java.util.concurrent.ThreadLocalRandom;
/**
 *
 * @author maxim
 */
public class RegistrarseController  implements Initializable {
    public JavaFXMLApplication mainApp;
    @FXML
    private Button boton_volver_atras;
    @FXML
    private Button boton_registrarse;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_email;
    @FXML
    private DatePicker dp_dob;
    @FXML
    private Button boton_seleccionar_imagen;
    @FXML
    private ImageView imagen_preview;
    private boolean actualizar=false;
    private Player actualizando;
    @FXML
    private void boton_volver_atras(ActionEvent event) throws Exception {
        if (actualizar){mainApp.menu_jugar();}
        else{mainApp.menu_principal();}
    }
    File image_file;
    
    public void modo_actualizar(Player player){
        actualizar=true;
        tf_username.setText(player.getNickName());
        tf_username.setEditable(false);
        tf_username.setDisable(true);
        tf_password.setText(player.getPassword());
        tf_email.setText(player.getEmail());
        dp_dob.setValue(player.getBirthdate());
        imagen_preview.setImage(player.getAvatar());
        boton_registrarse.setText("Guardar datos");
        actualizando=player;
    }
    
    @FXML
    public void seleccionar_imagen() {
        //por IA
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) boton_seleccionar_imagen.getScene().getWindow();
        image_file = fileChooser.showOpenDialog(stage);

        if (image_file != null) {
            Image image = new Image(image_file.toURI().toString());
            imagen_preview.setImage(image); 
        }
        
    }
    @FXML
    private void boton_registrarse(ActionEvent event) throws Exception {
        if (tf_username.getText().equals("") || tf_email.getText().equals("") || tf_password.getText().equals("") || dp_dob.getValue()==null){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Faltan datos");
            alert.setContentText("Tiene que introducir por lo menos un nombre de usuario, un email, una fecha de nacimiento y una contrasenya.");
            alert.showAndWait();
            return;
        }
        if (!actualizar && (mainApp.player_manager.existsNickName(tf_username.getText()))){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error en registrarse");
            alert.setContentText("Ya existe un usuario con este nombre.");
            alert.showAndWait();
            return;
        }
        if (! tf_password.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*()\\-=+])[A-Za-z\\d!@#$%&*()\\-=+]{8,20}$")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Contrasenya no valida");
            alert.setContentText("Su contraseña tiene que contener entre 8 y 20 caracteres, incorporar al menos una letra en mayúsculas y minúsculas, algún dígito y algún carácter especial !@#$%&*()-+= ).");
            alert.showAndWait();
            return;
        }
        if (! tf_email.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Email no valido");
            alert.setContentText("Email introducido no tiene formato valido.");
            alert.showAndWait();
            return;
        }
        if ( LocalDate.now().minusYears(12).isBefore(dp_dob.getValue())){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Eres demasiado joven");
            alert.setContentText("Hay que tener al menos 12 anyos para jugar.");
            alert.showAndWait();
            return;
        }
      
        if (image_file != null || actualizar) {
            Image avatar;
            if (image_file != null){
                avatar = new Image(image_file.toURI().toString());}
            else{
                avatar=actualizando.getAvatar();}
            if (actualizar){
                actualizando.setEmail(tf_email.getText());
                actualizando.setBirthdate(dp_dob.getValue());
                actualizando.setPassword(tf_password.getText());
                actualizando.setAvatar(avatar);
            }
            else{
                mainApp.player_manager.registerPlayer(tf_username.getText(), tf_email.getText(), tf_password.getText(), dp_dob.getValue(), 0, avatar);}
            }
        else{
            mainApp.player_manager.registerPlayer(tf_username.getText(), tf_email.getText(), tf_password.getText(), dp_dob.getValue(), 0);
        }
        
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Exito");
        alert.setHeaderText("Registro completado.");
        if (actualizar){alert.setHeaderText("Actualizacion completada.");}
        alert.setContentText("Bienvenido, "+ tf_username.getText());
        alert.showAndWait();
     
        if (actualizar){mainApp.menu_jugar();}
        else{mainApp.menu_principal();}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return (lastDot == -1) ? "" : name.substring(lastDot);
    }
}
