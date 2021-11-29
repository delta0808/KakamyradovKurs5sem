package runing;

import java.io.*;
import java.net.Socket;

public class NullSocket {
    private ObjectOutputStream coos;
    private Socket clientSocket;
    public boolean isHaveError = false;

    public NullSocket(){
        try {
            clientSocket = new Socket("127.0.0.1",9999);
            coos = new ObjectOutputStream(clientSocket.getOutputStream());
            coos.writeObject("BREAK");
        } catch(Exception e){
            isHaveError = true;
        } finally {
            try {
                coos.close();
                clientSocket.close();
            } catch (Exception e){
                isHaveError = true;
            }
        }
    }

}
