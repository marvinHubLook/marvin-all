package cn.socket.oio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/7/7 10:19
 **/
public class PlainOioServer {
    public static  void server(int port) throws Exception {
        //bind server to port
        final ServerSocket socket = new ServerSocket(port);
        try {
            while(true){
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while(reader.read()!=-1){
                    System.out.printf(reader.readLine());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            socket.close();
        }
    }

    public static void main(String[] args) throws Exception {
        server(8080);
    }


}
