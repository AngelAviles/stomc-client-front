package main;

import dominio.Message;
import gui_elements.Toast;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AdministrarSistemaController implements IController {

    @FXML
    public Label dateLabel;
    @FXML
    public Label timeLabel;
    @FXML
    public BorderPane panContenido;

    @FXML
    public Button btnEmpleados;
    @FXML
    public Button btnPuntosDeAtencion;
    @FXML
    public Button btnSucursales;
    @FXML
    public Button btnTurnos;
    @FXML
    public Button btnTramites;

    public void initialize(){
        System.out.println("AdministrarSistemaController did initialize");

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            timeLabel.setText(currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'del' yyyy")));

        loadContent("menuEmpleados.fxml");
    }

    @FXML
    public void didClickEmpleadosButton() {
        loadContent("menuEmpleados.fxml");
    }

    @FXML
    public void didClickPuntosDeAtencionButton() {
        loadContent("menuPuntosDeAtencion.fxml");
    }

    @FXML
    public void didClickSucursalesButton() { loadContent("menuSucursales.fxml"); }

    @FXML
    public void didClickTurnosButton() {
        loadContent("menuTurnos.fxml");
    }

    @FXML
    public void didClickTramitesButton() {
        loadContent("menuInformacionTramites.fxml");
    }

    public void loadContent(String recurso) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(recurso));
            Parent root = loader.load();
            panContenido.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeToast(String mensaje) {
        Toast.makeText(null, mensaje, 3500,100,300);
    }

    @Override
    public void handleMessage(Message message) {

    }
}
