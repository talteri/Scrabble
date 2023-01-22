package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    int port;
    ClientHandler ch;
    boolean stop;
    int numOfClients;
    MyServer(int port ,ClientHandler ch){
        this.port = port;
        this.ch = ch;
    }

    public void start(){
        stop = false;
        numOfClients = 0;
        new Thread(this::StartServer).start();
    }

    private void StartServer(){
        try{
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1000);
            while (!stop && numOfClients == 0){
                try{
                    Socket client = server.accept();
                    numOfClients++;
                    ch.handleClient(client.getInputStream(),client.getOutputStream());
                    ch.close();
                    numOfClients--;
                    client.close();

                }catch (SocketTimeoutException ignored){}
            }
            server.close();
        }catch (IOException ignored){}
    }
    public void close() {
        if (numOfClients == 0)
            stop = true;
    }
}