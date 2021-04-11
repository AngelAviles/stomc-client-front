package main;

import comm.Sender;
import dominio.Message;
import dominio.Turn;
import gui_elements.Toast;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientFrontController {

    Sender sender = new Sender();

    @FXML
    public Label dateLabel;
    @FXML
    public Label timeLabel;

    public void initialize(){
        System.out.println("HomeController did initialize");

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
    public void didClickNewTurnCajaButton(){
        System.out.println("Nuevo Turno Caja Button Clicked");
        Message msg = new Message(Message.MessageType.NEW_TURN_CAJA, "test_user");
        try {
            sender.sendMessage(msg);
            makeToast("Turno " + Turn.Type.CAJA.toString() + " creado exitosamente.");
        } catch (IOException e) {
            System.out.println("Could not connect to server.");
            makeToast("EL TURNO " + Turn.Type.CAJA.toString() + " NO HA SIDO CREADO.");
            //e.printStackTrace();
        }
    }

    @FXML
    public void didClickNewTurnModuloButton(){
        System.out.println("Nuevo Turno Modulo Button Clicked");
        Message msg = new Message(Message.MessageType.NEW_TURN_MODULO, "test_user");
        try {
            sender.sendMessage(msg);
            makeToast("Turno " + Turn.Type.MODULO.toString() + " creado exitosamente.");
        } catch (IOException e) {
            System.out.println("Could not connect to server.");
            makeToast("EL TURNO " + Turn.Type.MODULO.toString() + " NO HA SIDO CREADO.");
            //e.printStackTrace();
        }
    }

    @FXML
    public void didClickNewTurnCajaModuloButton(){
        System.out.println("Nuevo Turno Caja/Modulo Button Clicked");
        Message msg = new Message(Message.MessageType.NEW_TURN_GENERIC, "test_user");
        try {
            sender.sendMessage(msg);
            makeToast("Turno " + Turn.Type.GENERIC.toString() + " creado exitosamente.");
        } catch (IOException e) {
            System.out.println("Could not connect to server.");
            makeToast("EL TURNO " + Turn.Type.GENERIC.toString() + " NO HA SIDO CREADO.");
            //e.printStackTrace();
        }
    }

    public void makeToast(String mensaje) {
        Toast.makeText(null, mensaje, 3500,100,300);
    }
}
