package comm;

import dominio.Message;
import gui_elements.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {

    //TODO: Merge Listener and Sender classes.

    public void sendMessage(Message message) throws IOException {

        Socket socket = null;

        socket = new Socket("localhost", 5511);
        ObjectOutputStream oos = null;

        oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeObject(message);

        socket.close();
    }
}
