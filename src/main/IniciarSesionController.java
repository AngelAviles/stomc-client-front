package main;

import dominio.Message;
import gui_elements.Toast;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class IniciarSesionController implements IController {

    @FXML
    public Label dateLabel;
    @FXML
    public Label timeLabel;
    @FXML
    public TextField txtUsuario;
    @FXML
    public PasswordField txtPassword;
    @FXML
    public Button btnIniciarSesion;

    public void initialize(){
        System.out.println("IniciarSesionController did initialize");

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            timeLabel.setText(currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'del' yyyy")));
    }

    @FXML
    public void didClickIniciarSesionButton(){
//        System.out.println("Nuevo Iniciar Sesion Button Clicked");
//        Message msg = new Message(Message.MessageType.NEW_TURN_CAJA, "test_user");
//        try {
//            sender.sendMessage(msg);
//            makeToast("Turno " + Turn.Type.CAJA.toString() + " creado exitosamente.");
//        } catch (IOException e) {
//            System.out.println("Could not connect to server.");
//            makeToast("EL TURNO " + Turn.Type.CAJA.toString() + " NO HA SIDO CREADO.");
//            //e.printStackTrace();
//        }
    }

    public void makeToast(String mensaje) {
        Toast.makeText(null, mensaje, 3500,100,300);
    }

    @Override
    public void handleMessage(Message message) {

    }
}
