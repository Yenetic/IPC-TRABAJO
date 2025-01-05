/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.fxml;

import connect4.Connect4;
import connect4.Player;
import java.awt.Color;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafxmlapplication.JavaFXMLApplication;
/**
 * FXML Controller class
 *
 * @author jiaxiang
 */
public class JuegoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public JavaFXMLApplication mainApp;
    private Text text_timer;
    
    Task<String> update_timer;
    public Player player1;
    public Player player2;
    @FXML
    private GridPane campo;
    private int turno; //1 es jugador 1, 2 es jugador 2
    private int[][] campo_info = new int[7][8];
    private Button[][] campo_boton = new Button[7][8];
    @FXML
    private Text texto_turno;

    public void initialize_() {
 
        mainApp.jugando=true;
                
        if (player2 == null) turno = 1;
        else turno = ThreadLocalRandom.current().nextInt(2)+1;
        
        if(turno == 1) {
            texto_turno.setFill(Paint.valueOf("BLUE"));
            texto_turno.setText("Turno de "+player1.getNickName());
        } else {
            texto_turno.setFill(Paint.valueOf("RED"));
            texto_turno.setText("Turno de "+player2.getNickName());
        }
        
        for (int i=0; i<7; i++){
            for (int j=0; j<8; j++){
                Button button = new Button();
                int final_i = i;
                int final_j = j;
                button.setOnAction(e -> on_player_action(final_i, final_j));
                button.setPrefWidth(45);  
                button.setPrefHeight(45);
                campo.add(button, i, j);
                campo_info[i][j] = 0;
                campo_boton[i][j] = button;
            }
        }
    }    
    
    private void on_player_action(int i, int j){
        if((campo_info[i][0]!=0)) return; 
        for (int k=0; k<8; k++) if (campo_info[i][k]==0) j=k;
        
        campo_info[i][j]=turno;
        campo_boton[i][j].setStyle("-fx-background-color: " + (turno == 1 ? "blue" : "red") + ";");
        campo_boton[i][j].setDisable(true);
        if (check_win_condition(turno)){ win(); return; }
        else if(turno==1) {
            turno=2;
            texto_turno.setFill(Paint.valueOf("RED"));
            texto_turno.setText("Turno de "+(player2!=null?player2.getNickName():"Robot"));
        } else {
            turno=1;
            texto_turno.setFill(Paint.valueOf("BLUE"));
            texto_turno.setText("Turno de "+player1.getNickName());
        } if (player2==null && turno==2) robot_action();
    }
    
    private void robot_action(){
        while (true){
            int i = ThreadLocalRandom.current().nextInt(0, 7);
            if (campo_info[i][0]==0){
                on_player_action(i, 0);
                return;
            }
        }
    }
    
    private boolean check_4(int pos_x, int pos_y, int player, int delta_x, int delta_y){
        if (delta_x==0 && delta_y==0) return false;
        for (int i = 0; i<4; i++){
            int new_x = pos_x+delta_x*i;
            int new_y = pos_y+delta_y*i;
            if (new_x<0 || new_x >6 || new_y < 0 || new_y >7 ||
                    campo_info[new_x][new_y] != player) return false;
        } return true;
    }
    
    private boolean check_win_condition(int player){    
        for (int i=0; i<7; i++){
            for (int j=0; j<8; j++){
                for (int k=-1; k<2; k++){
                    for (int z=-1; z<2; z++){
                        if (check_4(i, j, player, k, z)) return true;
                    }
                }
            }
        } return false;
    }
    
    private void win()  {
        Player winner;
        Player loser;
        
        if (turno==1){ winner=player1; loser=player2; } 
        else { winner=player2; loser=player1; }
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("¡Partida completada!");
        alert.setHeaderText("Ganador: "+(winner==null ? "Robot" : winner.getNickName()));
        alert.setContentText(null);
        alert.showAndWait();
        
        mainApp.jugando=false;
        if (winner!=null) {
            if (loser!=null) {
                winner.addPoints(5);
                Connect4.getInstance().registerRound(LocalDateTime.now(), winner, loser);
            } else winner.addPoints(1);
        } try { mainApp.menu_jugar(); } catch(Exception e) {}
    }

    @FXML
    private void acabar_partida(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación.");
        alert.setHeaderText("¿Seguro que quieres terminar la partida?");
        alert.setContentText("Se perderá todo el progreso.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                mainApp.jugando=false;
                mainApp.menu_jugar();
            } catch(Exception e){}
        } 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
