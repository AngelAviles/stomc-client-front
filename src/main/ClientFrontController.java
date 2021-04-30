package main;

import comm.Conexion;
import dominio.Employee;
import dominio.Message;
import dominio.Turn;
import gui_elements.Toast;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import print.PrintController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientFrontController implements IController{

    private IniciarSesionController iniciarSesionController;

    private Turn turn;

    private Employee empleado;

    @FXML
    public Button btnCerrarSesion;
    @FXML
    public Label txtEmpleado;

    @FXML
    public Label dateLabel;
    @FXML
    public Label timeLabel;

    @FXML
    public Button newTurnCajaButton;
    @FXML
    public Button newTurnModuloButton;
    @FXML
    public Button newTurnCajaModuloButton;
    @FXML
    public Button preguntasFrecuentesButton;

    @FXML
    public Label txtTurnoCreado;
    @FXML
    public Button imprimirTurnoButton;

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public void setEmpleado(Employee empleado) {
        this.empleado = empleado;

        if (empleado != null) {
            txtEmpleado.setText(empleado.getName());
        }
    }

    public Employee getEmpleado() {
        return empleado;
    }

    public void initialize(){
        System.out.println("HomeController did initialize");

        Conexion.getInstance().setController(this);

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
            Conexion.getInstance().sendMessage(msg);
        } catch (IOException e) {
            System.out.println("Could not connect to server.");
            makeToast("EL TURNO " + Turn.Type.CAJA.toString() + " NO HA SIDO CREADO.");
        }
    }

    @FXML
    public void didClickNewTurnModuloButton(){
        System.out.println("Nuevo Turno Modulo Button Clicked");
        Message msg = new Message(Message.MessageType.NEW_TURN_MODULO, "test_user");
        try {
            Conexion.getInstance().sendMessage(msg);
        } catch (IOException e) {
            System.out.println("Could not connect to server.");
            makeToast("EL TURNO " + Turn.Type.MODULO.toString() + " NO HA SIDO CREADO.");
        }
    }

    @FXML
    public void didClickNewTurnCajaModuloButton(){
        System.out.println("Nuevo Turno Caja/Modulo Button Clicked");
        Message msg = new Message(Message.MessageType.NEW_TURN_GENERIC, "test_user");
        try {
            Conexion.getInstance().sendMessage(msg);
        } catch (IOException e) {
            System.out.println("Could not connect to server.");
            makeToast("EL TURNO " + Turn.Type.GENERIC.toString() + " NO HA SIDO CREADO.");
        }
    }

    @FXML
    public void didClickImprimirButton(ActionEvent actionEvent) {
        if (getTurn() != null) {
            PrintController.imprimirTurno(getTurn());
        } else {
            makeToast("NO HAY TURNO PARA IMPRIMIR");
        }
    }

    public void makeToast(String mensaje) {
        Toast.makeText(null, mensaje, 3500,100,300);
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.getType()) {
            case NEW_TURN_CAJA:
            case NEW_TURN_MODULO:
            case NEW_TURN_GENERIC:
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setTurn( (Turn)message.getObject() );
                        updateCreatedTurnLabelWithText();
                        makeToast("Turno " + ( (Turn)message.getObject() ).getType().toString() + " creado exitosamente.");
                    }
                });
        }
    }

    public void updateCreatedTurnLabelWithText() {
        String s = "";
        switch (getTurn().getType()) {

            case CAJA:
                s = "C";
                break;
            case MODULO:
                s = "M";
                break;
            case GENERIC:
                s = "G";
                break;
        }

        txtTurnoCreado.setText(s + getTurn().getTurnNumber());
    }

    @FXML
    public void didClickCerrarSesionButton(ActionEvent actionEvent) {
        try {
            setEmpleado(null);

            try {
                Conexion.getInstance().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicioSesion.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1060, 700);
            Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();

            iniciarSesionController = loader.getController();

            appStage.setTitle("STOMC Client");
            appStage.setOnCloseRequest(windowEvent -> System.exit(0));
            appStage.show();
        } catch(IOException e) {
            makeToast("Error al cerrar Sesión.");
        }
    }
}
