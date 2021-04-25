package comm;

import dominio.Message;
import main.IController;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Conexion implements Runnable {

    private static Conexion instance;

    private static IController controller;

    protected static int serverPort = 0; // Server: 5511 -- Client: 5512
    protected static String serverHost = "";

    private static Socket socket;
    protected static ObjectOutputStream os;
    protected static ObjectInputStream is;

    private Conexion() {

        try (InputStream input = new FileInputStream("res/config.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            serverPort = Integer.parseInt(prop.getProperty("server_port"));
            serverHost = prop.getProperty("serverHost");

            socket = new Socket(serverHost, serverPort);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Thread hiloEscucha = new Thread(this);
        hiloEscucha.start();
    }

    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }

        if (socket.isClosed()) {
            try {
                socket = new Socket(serverHost, serverPort);
                os = new ObjectOutputStream(socket.getOutputStream());
                is = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        Conexion.controller = controller;
    }

    public void sendMessage(Message message) throws IOException {
        try {
            os.writeObject(message);
            os.reset();
        } catch (IOException e) {
            try {
                close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                return;
            }
            e.printStackTrace();
            return;
        }
    }

    public void close() throws IOException {
        is.close();
        os.close();
        socket.close();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message) is.readObject();

                getController().handleMessage(message);
            } catch (IOException | ClassNotFoundException e) {
                try {
                    close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    return;
                }
                e.printStackTrace();
                return;
            }
        }
    }
}
