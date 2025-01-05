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
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private TextField tf_password_shown;
    @FXML
    private PasswordField tf_password_hidden;
    @FXML
    private ToggleButton show_password;
    public void initialize(URL url, ResourceBundle rb) {
        tf_password_shown.textProperty().bindBidirectional(tf_password_hidden.textProperty());
    }    
    
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
        tf_password_shown.setText(player.getPassword());
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
        if (tf_username.getText().equals("") || tf_email.getText().equals("") || tf_password_shown.getText().equals("") || dp_dob.getValue()==null){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Faltan datos.");
            alert.setContentText("Tiene que introducir por lo menos un nombre de usuario,\n un email, una fecha de nacimiento\n y una contraseña.");
            alert.showAndWait();
            return;
        }
        if (!actualizar && (mainApp.player_manager.existsNickName(tf_username.getText()))){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error en registrarse.");
            alert.setContentText("Ya existe un usuario con este nombre.");
            alert.showAndWait();
            return;
        }
        if (! tf_password_shown.getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*()\\-=+])[A-Za-z\\d!@#$%&*()\\-=+]{8,20}$")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("Contraseña no válida.");
            alert.setContentText("Su contraseña debe contener entre 8 y 20 caracteres,\n incorporar al menos una letra en mayúsculas y minúsculas,\n algún dígito y algún carácter especial !@#$%&*()-+= ).");
            alert.showAndWait();
            return;
        }
        if (! tf_email.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("Email no válido.");
            alert.setContentText("Email introducido no tiene formato válido.");
            alert.showAndWait();
            return;
        }
        if ( LocalDate.now().minusYears(12).isBefore(dp_dob.getValue())){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("Eres demasiado joven.");
            alert.setContentText("Debes tener al menos 12 años para jugar.");
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
                actualizando.setPassword(tf_password_shown.getText());
                actualizando.setAvatar(avatar);
            }
            else{
                mainApp.player_manager.registerPlayer(tf_username.getText(), tf_email.getText(), tf_password_shown.getText(), dp_dob.getValue(), 0, avatar);}
            }
        else{
            mainApp.player_manager.registerPlayer(tf_username.getText(), tf_email.getText(), tf_password_shown.getText(), dp_dob.getValue(), 0);
        }
        
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Éxito.");
        alert.setHeaderText("Registro completado.");
        if (actualizar){alert.setHeaderText("Actualización completada.");}
        alert.setContentText("Bienvenido, "+ tf_username.getText() + ".");
        alert.showAndWait();
     
        if (actualizar){mainApp.menu_jugar();}
        else{mainApp.menu_principal();}
    }
    
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return (lastDot == -1) ? "" : name.substring(lastDot);
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

    @FXML
    private void contraseña_enter(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER && 
                !tf_username.getText().equals("") &&
                !tf_email.getText().equals("") &&
                dp_dob.getValue() != null &&
                !tf_password_shown.getText().equals("")) {
            boton_registrarse(null);
        }
    }
}
