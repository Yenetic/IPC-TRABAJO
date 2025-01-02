/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.fxml;

import connect4.Connect4;
import connect4.Player;
import connect4.Round;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.layout.BorderPane;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafxmlapplication.JavaFXMLApplication;
/**
 * FXML Controller class
 *
 * @author jiaxiang
 */
public class MenuJugarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public JavaFXMLApplication mainApp;
    @FXML
    private VBox menu_permanente;
    
    @FXML
    private TableView<Player> ranking_table;
    @FXML
    private TableColumn<Player, Integer> column_rank;
    @FXML
    private TableColumn<Player, Image> column_avatar;
    @FXML
    private TableColumn<Player, String> column_name;
    @FXML
    private TableColumn<Player, String> column_punctuation;
    
    
    @FXML
    private TableView<Round> match_table;
    @FXML
    private TableColumn<Round, String> column_dates;
    @FXML
    private TableColumn<Round, String> column_hours;
    @FXML
    private TableColumn<Round, String> column_winners;
    @FXML
    private TableColumn<Round, String> column_losers;
   
   
    
    @FXML
    private DatePicker date_from;
    @FXML
    private DatePicker date_to;
    private ChoiceBox<Player> player_choice_box;
    @FXML
    private VBox show_player_matches;
    
    ObservableList<Player> player_ranking;
    @FXML
    private RadioMenuItem filtro_todas;
    @FXML
    private ToggleGroup grupo1;
    @FXML
    private RadioMenuItem filtro_ganadas;
    @FXML
    private RadioMenuItem filtro_perdidas;
    @FXML
    private Button boton_cerrar_sesion;
    @FXML
    private Button boton_actualizar_perfil;
    @FXML
    private Button boton_jugar_contra_otro_jugador;
    @FXML
    private Button boton_jugar_contra_robot;
    @FXML
    private HBox player_list;
    @FXML
    private MenuButton filtro;
    @FXML
    private MenuButton player_selector;
    
    private Player selected_player;
    @FXML
    private Label player_name;
    public void initialize(URL url, ResourceBundle rb) {
        filtro_ganadas.setOnAction(eh->{filtro.setText("Solo ganadas");});
        filtro_perdidas.setOnAction(eh->{filtro.setText("Solo perdidas");});
        filtro_todas.setOnAction(eh->{filtro.setText("Todas");});
        
    }    
    
    private void show_object(Node object, String nombre) {
        // POR IA
        object.setVisible(true);
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(nombre);

        Button closeButton = new Button("Cerrar");
        closeButton.setOnAction(e -> popupStage.close());
        BorderPane popupLayout = new BorderPane();
        BorderPane.setMargin(object, new Insets(5));
        BorderPane.setMargin(closeButton, new Insets(5)); 
        BorderPane.setAlignment(closeButton, javafx.geometry.Pos.CENTER);
        popupLayout.setCenter(object); 
        popupLayout.setBottom(closeButton); 
        
        BorderPane.setAlignment(closeButton, javafx.geometry.Pos.CENTER);

        Scene popupScene = new Scene(popupLayout, 600, 400); 
        popupStage.setScene(popupScene);

        popupStage.showAndWait();
    }

    private boolean show_object_and_confirm(Node object, String nombre, String boton_si, String boton_no) {
        // POR IA
        object.setVisible(true);
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(nombre);

        Button cancelButton = new Button(boton_no);
        Button confirmButton = new Button(boton_si);

        final boolean[] userChoice = {false};

        cancelButton.setOnAction(e -> {
            userChoice[0] = false;
            popupStage.close();
        });

        confirmButton.setOnAction(e -> {
            userChoice[0] = true;
            popupStage.close();
        });

        HBox buttonLayout = new HBox(10, cancelButton, confirmButton); 
        buttonLayout.setAlignment(Pos.CENTER);

        BorderPane popupLayout = new BorderPane();
        BorderPane.setMargin(object, new Insets(5)); 
        BorderPane.setMargin(buttonLayout, new Insets(5));
        popupLayout.setCenter(object); 
        popupLayout.setBottom(buttonLayout);

        BorderPane.setAlignment(buttonLayout, Pos.CENTER);

        Scene popupScene = new Scene(popupLayout, 600, 400);
        popupStage.setScene(popupScene);

        popupStage.showAndWait();
        return userChoice[0];
    }

    public void rellenar_tablas(){
        
        
        /////////// RANKING ////////////////
        player_ranking = FXCollections.observableArrayList(mainApp.player_manager.getRanking());
        ranking_table.setItems(player_ranking);
        
        column_rank.setCellValueFactory(user -> 
            new ReadOnlyObjectWrapper<>(player_ranking.indexOf(user.getValue()) + 1) 
        );
        column_avatar.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAvatar()));

        column_avatar.setCellFactory(col -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    imageView.setFitWidth(30); 
                    imageView.setFitHeight(30); 
                    setGraphic(imageView);
                }
            }
        });
        
        column_name.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getNickName()));
        column_punctuation.setCellValueFactory(user -> new SimpleStringProperty(Integer.toString(user.getValue().getPoints())));

        ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
        for (Player player : player_ranking) {
            MenuItem menuItem = new MenuItem(player.getNickName());

            menuItem.setOnAction(event -> {
                selected_player = player;
                player_selector.setText(player.getNickName());
            });

            menu.add(menuItem);
        }
        player_selector.getItems().setAll(menu);        
        column_dates.setCellValueFactory(round -> new SimpleStringProperty(round.getValue().getTimestamp().toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        column_hours.setCellValueFactory(round -> {
                    int hours = round.getValue().getTimestamp().getHour();
                    int minutes = round.getValue().getTimestamp().getMinute();
                    String formattedTime = String.format("%02d:%02d", hours, minutes);
                    return new SimpleStringProperty(formattedTime);
                });
        column_winners.setCellValueFactory(round -> new SimpleStringProperty(round.getValue().getWinner().getNickName()));
        column_losers.setCellValueFactory(round -> new SimpleStringProperty(round.getValue().getLoser().getNickName()));
        
        
        
        if (mainApp.player2 != null){
            boton_cerrar_sesion.setText("Cerrar sesion (jugador 2)");
            boton_actualizar_perfil.setText("Actualizar perfil (jugador 2)");
            boton_jugar_contra_otro_jugador.setText("Jugar contra otro jugador");
            boton_jugar_contra_robot.setVisible(false);
           
        }
        String text = "Jugador 1: "+mainApp.player1.getNickName();
        if (mainApp.player2!=null){
            text+="\nJugador 2: "+mainApp.player2.getNickName();
        }
        player_name.setText(text);
   
    }
    @FXML
    private void jugar_contra_robot(ActionEvent event) throws Exception {
        mainApp.empezar_juego(mainApp.player1, null);
    }

    @FXML
    private void jugar_contra_otro_jugador(ActionEvent event) throws Exception{
        if (mainApp.player2!=null){
            mainApp.empezar_juego(mainApp.player1, mainApp.player2);
        }
        else{
            mainApp.menu_principal(); //repetir proceso para otro jugador
        }
    }

    @FXML
    private void ver_ranking(ActionEvent event) {
        show_object(ranking_table, "Ranking global de los jugadores");
    }

    @FXML
    private void ver_lista_de_partidas(ActionEvent event) {
        
        ObservableList<Round> rounds = FXCollections.observableArrayList();
        for (Player player : player_ranking){
            List<Round> player_rounds = mainApp.player_manager.getRoundsForPlayer(player.getNickName());
            for (Round round : player_rounds){
                boolean present = false;
                for (Round round2:rounds){ //rouds.contains no funciona correctamente
                    if (round2.getTimestamp().equals(round.getTimestamp())){present=true;}
                }
                if (!present){rounds.add(round);}
            }
        }
        rounds.sort((r1, r2) -> r2.getTimestamp().compareTo(r1.getTimestamp()));
        match_table.setItems(rounds);
        
       
       
        show_object(match_table, "Lista global de partidas");
    }

    private boolean fecha_correcta(LocalDate from, LocalDate to){
        if(from==null || to==null || from.isAfter(to)){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Rango de fechas no aceptable.");
                    alert.setContentText(null);
                    alert.showAndWait();
                    return false;}
        return true;
    }
    private boolean jugador_correcto(Player player){
        if (player == null){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Tiene que eligir a un usuario.");
                    alert.setContentText(null);
                    alert.showAndWait();
                    return false;
                }
        return true;
    }
    @FXML
    private void ver_partidas_de_jugador(ActionEvent event) {
        player_selector.setVisible(true);
        filtro.setVisible(true);
        while(true){
        boolean mostrar = show_object_and_confirm(show_player_matches, "Elige un jugador y un rango de fechas", "Mostrar", "Cerrar");
        if (!mostrar){return;}
        else {
                Player player = selected_player;
                if (!jugador_correcto(player)) continue;
                
                LocalDate from = date_from.valueProperty().getValue();
                LocalDate to = date_to.valueProperty().getValue();
                if (!fecha_correcta(from, to)) continue;
                

                ObservableList<Round> rounds = FXCollections.observableArrayList();
                
                for (Round round:mainApp.player_manager.getRoundsForPlayer(player.getNickName())){
                    boolean iswinner = round.getWinner().getNickName().equals(player.getNickName());
                    if ((!iswinner && filtro_ganadas.isSelected()) || (iswinner && filtro_perdidas.isSelected())){
                        continue;
                   }
                    else if (round.getTimestamp().toLocalDate().isBefore(from) || round.getTimestamp().toLocalDate().isAfter(to) ){
                        continue;
                    }
                    rounds.add(round);
                }
                rounds.sort((r1, r2) -> r2.getTimestamp().compareTo(r1.getTimestamp()));
                match_table.setItems(rounds);
                match_table.refresh();
                boolean cerrar = show_object_and_confirm(match_table, "Lista de partidas jugadas por "+player.getNickName()+" desde "+from.toString()+ " hasta "+to.toString(), "Cerrar", "Cambiar rango de fechas");
                if (!cerrar) continue;
                return;
            }
        }
    }

    @FXML
    private void ver_evolucion_de_partidas(ActionEvent event) {
        player_selector.setVisible(false);
        filtro.setVisible(false);
        
        while(true){
        boolean mostrar = show_object_and_confirm(show_player_matches, "Elige un jugador y un rango de fechas", "Mostrar", "Cerrar");
        if (!mostrar){return;}
        else {

                LocalDate from = date_from.valueProperty().getValue();
                LocalDate to = date_to.valueProperty().getValue();
                if (!fecha_correcta(from, to)) continue;
                
                ObservableList<Round> rounds = FXCollections.observableArrayList();
                for (Player player : player_ranking){
                    List<Round> player_rounds = mainApp.player_manager.getRoundsForPlayer(player.getNickName());
                    for (Round round : player_rounds){
                        if((round.getTimestamp().toLocalDate().isBefore(from)) || (round.getTimestamp().toLocalDate().isAfter(to))){continue;}
                        for (Round round2:rounds){ 
                            
                            if (round2.getTimestamp().equals(round.getTimestamp())){break;}
                        }
                        rounds.add(round);
                    }
                }
                rounds.sort((r1, r2) -> r2.getTimestamp().compareTo(r1.getTimestamp()));

                //POR IA
                // Prepare a LineChart with CategoryAxis for X-axis
                CategoryAxis xAxis = new CategoryAxis();
                NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel("Fecha");
                yAxis.setLabel("Partidas jugadas");
                LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
                lineChart.setTitle("Partidas por dia");

                // Process the data: group by date and count rounds
                Map<LocalDate, Long> roundsByDate = rounds.stream()
                        .collect(Collectors.groupingBy(
                                round -> round.getTimestamp().toLocalDate(),
                                Collectors.counting()
                        ));

                // Sort the data by date in ascending order
                Map<LocalDate, Long> sortedRoundsByDate = new TreeMap<>(roundsByDate);

                // Create a series for the chart
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Partidas diarias");

                // Add data points to the series
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                sortedRoundsByDate.forEach((date, count) -> {
                    String formattedDate = date.format(formatter);
                    series.getData().add(new XYChart.Data<>(formattedDate, count));
                });

                lineChart.getData().add(series);
                boolean cerrar = show_object_and_confirm(lineChart, "Evolucion del numero diario de partidas"+" desde "+from.toString()+ " hasta "+to.toString(), "Cerrar", "Cambiar rango de fechas");
                if (cerrar) return;
        }   }
            
    }
    
    
    @FXML
    private void ver_evolucion_de_jugador(ActionEvent event) {
        player_selector.setVisible(true);
        filtro.setVisible(false);
        while(true){
        boolean mostrar = show_object_and_confirm(show_player_matches, "Elige un jugador y un rango de fechas", "Mostrar", "Cerrar");
        if (!mostrar){return;}
        else {
                
                Player player = selected_player;
                if (!jugador_correcto(player)) continue;
                
                LocalDate from = date_from.valueProperty().getValue();
                LocalDate to = date_to.valueProperty().getValue();

                if (!fecha_correcta(from, to)) continue;
                
                ObservableList<Round> rounds = FXCollections.observableArrayList();
                
                
                    List<Round> player_rounds = mainApp.player_manager.getRoundsForPlayer(player.getNickName());
                    for (Round round : player_rounds){
                        if((round.getTimestamp().toLocalDate().isBefore(from)) || (round.getTimestamp().toLocalDate().isAfter(to))){continue;}
                        
                        for (Round round2:rounds){ 
                            
                            if (round2.getTimestamp().equals(round.getTimestamp())){break;}
                        }
                        rounds.add(round);
                    }
                
                rounds.sort((r2, r1) -> r2.getTimestamp().compareTo(r1.getTimestamp()));

                //POR IA
                VBox root = new VBox(10);

        // Bar Chart 1: Games Won and Lost
        CategoryAxis xAxis1 = new CategoryAxis();
        NumberAxis yAxis1 = new NumberAxis();
        StackedBarChart<String, Number> gamesChart = new StackedBarChart<>(xAxis1, yAxis1);
        gamesChart.setTitle("Partidas ganadas y perdidas");
        xAxis1.setLabel("Fecha");
        yAxis1.setLabel("Numero de partidas");

        XYChart.Series<String, Number> wonSeries = new XYChart.Series<>();
        wonSeries.setName("Ganadas");
        XYChart.Series<String, Number> lostSeries = new XYChart.Series<>();
        lostSeries.setName("Perdidas");

        Map<LocalDate, List<Round>> roundsByDate = rounds.stream()
        .collect(Collectors.groupingBy(
                round -> round.getTimestamp().toLocalDate(),
                LinkedHashMap::new,                         
                Collectors.toList()                      
        ));
        
        for (Map.Entry<LocalDate, List<Round>> entry : roundsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<Round> dailyRounds = entry.getValue();

            long wonCount = dailyRounds.stream().filter(r -> r.getWinner().getNickName().equals(player.getNickName())).count();
            long lostCount = dailyRounds.stream().filter(r -> r.getLoser().getNickName().equals(player.getNickName())).count();

            wonSeries.getData().add(new XYChart.Data<>(date.toString(), wonCount));
            lostSeries.getData().add(new XYChart.Data<>(date.toString(), lostCount));
        }
        gamesChart.getData().addAll(wonSeries, lostSeries);
        
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
        BarChart<String, Number> opponentsChart = new BarChart<>(xAxis2, yAxis2);
        opponentsChart.setTitle("Numero de oponentes por dia");
        xAxis2.setLabel("Fecha");
        yAxis2.setLabel("Oponentes unicos");

        XYChart.Series<String, Number> opponentsSeries = new XYChart.Series<>();
        opponentsSeries.setName("Oponentes unicos");

        for (Map.Entry<LocalDate, List<Round>> entry : roundsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<Round> dailyRounds = entry.getValue();

            long uniqueOpponents = dailyRounds.stream()
                    .flatMap(r -> Stream.of(r.getWinner(), r.getLoser()))
                    .filter(p -> !p.getNickName().equals(player.getNickName()))
                    .distinct()
                    .count();

            opponentsSeries.getData().add(new XYChart.Data<>(date.toString(), uniqueOpponents));
        }

        opponentsChart.getData().add(opponentsSeries);

        root.getChildren().addAll(gamesChart, opponentsChart);
                boolean cerrar = show_object_and_confirm(root, "Evolucion del numero diario de partidas del jugador "+player.getNickName()+" desde "+from.toString()+ " hasta "+to.toString() , "Cerrar", "Cambiar rango de fechas");
                if (cerrar) return;
        }   }
    }
    @FXML
    private void cerrar_sesion(ActionEvent event) throws Exception {
        if (mainApp.player2 != null){
            mainApp.player2 = null;
            mainApp.menu_jugar();
        }
        else{
            mainApp.player1 = null;
            mainApp.menu_principal();
        }
    }

    @FXML
    private void actualizar_perfil(ActionEvent event) throws Exception {
        if (mainApp.player2 != null){
            mainApp.actualizar_perfil(mainApp.player2);
        }
        else{
            mainApp.actualizar_perfil(mainApp.player1);
        }
    }


 
}
