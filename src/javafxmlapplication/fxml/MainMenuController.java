/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafxmlapplication.JavaFXMLApplication;
/**
 * FXML Controller class
 *
 * @author maxim
 */
public class MainMenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public JavaFXMLApplication mainApp;
    @FXML
    private Button nodo_boton_salir;
    @FXML
    private Text texto_jugador;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void boton_iniciar_sesion(ActionEvent event) throws Exception {
        mainApp.iniciar_sesion();
    }

    @FXML
    private void boton_registrarse(ActionEvent event) throws Exception {
        mainApp.registrarse();
    }

    @FXML
    private void boton_salir(ActionEvent event) throws Exception {
        if (mainApp.player1 != null){mainApp.menu_jugar();}
        else{Platform.exit();}
    }
    public void inicializar_(){
    if(mainApp.player1!=null){nodo_boton_salir.setText("Cancelar");texto_jugador.setText("Jugador 2");}
    else{texto_jugador.setText("");}
    }
}
