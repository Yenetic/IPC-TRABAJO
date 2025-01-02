/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import connect4.Connect4;
import connect4.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafxmlapplication.fxml.MainMenuController;
import javafxmlapplication.fxml.IniciarSesionController;
import javafxmlapplication.fxml.JuegoController;
import javafxmlapplication.fxml.MenuJugarController;
import javafxmlapplication.fxml.RecuperarContrasenyaController;
import javafxmlapplication.fxml.RegistrarseController;

public class JavaFXMLApplication extends Application {
    Stage stage;
    Scene scene;
    
    public Player player1;
    public Player player2;
    
    public Connect4 player_manager;
    public boolean jugando = false;
    BorderPane root;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        player_manager = Connect4.getInstance();
        
        this.stage=stage;
        MenuBar menuBar = new MenuBar();
        Menu theme_menu = new Menu("Tema");
        MenuItem light_theme = new MenuItem("Blanco");
        light_theme.setOnAction((t) -> {
            Application.setUserAgentStylesheet(STYLESHEET_MODENA);
        });
        MenuItem dark_theme = new MenuItem("Oscuro");
        dark_theme.setOnAction((t) -> {
            String css = this.getClass().getResource("/estilos/oscuro.css") .toExternalForm();
            Application.setUserAgentStylesheet(css);
        });
        theme_menu.getItems().addAll(light_theme, dark_theme);
        menuBar.getMenus().addAll(theme_menu);
        root = new BorderPane();
        root.setTop(menuBar);
        
        
        menu_principal();
        
        
        
        stage.setTitle("Cuatro en Raya");
        stage.show();
        stage.setOnCloseRequest(event -> {
            event.consume();
            if (!jugando) stage.close();
            else{
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("Seguro que quiere salir?");
            alert.setContentText("Se perdera todo el progreso.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    stage.close();
                }
            });}
        });
    }
    
    
    public void menu_principal() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/fxml/MainMenu.fxml"));
        Parent root = loader.load();
        
        //crear escna si no existe
        if (scene==null){
            this.scene = new Scene(this.root);
            this.stage.setScene(scene);
            this.root.setCenter(root);
        }
        else{
            this.root.setCenter(root);
        }
        
        
        MainMenuController controller = loader.getController();
        controller.mainApp = this;
        controller.inicializar_();
    }
    
    
    public void iniciar_sesion() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/fxml/IniciarSesion.fxml"));
        Parent root = loader.load();
        this.root.setCenter(root);
        
        IniciarSesionController controller = loader.getController();
        controller.mainApp = this;
    }
    

    public void registrarse() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/fxml/Registrarse.fxml"));
        Parent root = loader.load();
        this.root.setCenter(root);
        
        RegistrarseController controller = loader.getController();
        controller.mainApp = this;
    }
    
    public void actualizar_perfil(Player player) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/fxml/Registrarse.fxml"));
        Parent root = loader.load();
        this.root.setCenter(root);
        
        RegistrarseController controller = loader.getController();
        controller.mainApp = this;
        controller.modo_actualizar(player);
    }
    
    public void menu_jugar() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/fxml/MenuJugar.fxml"));
        Parent root = loader.load();
        this.root.setCenter(root);
        
        MenuJugarController controller = loader.getController();
        controller.mainApp = this;
        
        controller.rellenar_tablas();
    }
    
    public void empezar_juego(Player player1, Player player2) throws Exception{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/fxml/Juego.fxml"));
        Parent root = loader.load();
        this.root.setCenter(root);
        
        JuegoController controller = loader.getController();
        controller.mainApp = this;
        controller.player1 = player1;
        controller.player2 = player2;
        controller.initialize_();
        
    }
    
    public void recuperar_contrasenya() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/fxml/RecuperarContrasenya.fxml"));
        Parent root = loader.load();
        this.root.setCenter(root);
        
        RecuperarContrasenyaController controller = loader.getController();
        controller.mainApp = this;
    }
    
    public static void main(String[] args) {
        launch(args);
        
    }


    
}
