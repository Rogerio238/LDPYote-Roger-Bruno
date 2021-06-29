/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author roger
 */
public class YoteBrunoRoger extends Application {
private static String serverIP = "127.0.0.1";
    private static final int serverPort = 6666;
    static DataInputStream in;
    static DataOutputStream out;
    String boas = "boas";
    private static TextField inputChat;
    public static Button teste;
    public static int yo = 0;
    private int count = 0;
    private final Text text = new Text(Integer.toString(count));

    private void incrementCount() {
        count++;
        text.setText(Integer.toString(count));
        text.setLayoutX(100);
        text.setLayoutY(100);
        
    }
    @Override
    public void start(Stage stage) throws Exception {

        Parent root;
        root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
 //connectClient();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        //System.out.println("Hello");
FXMLDocumentController.confirma.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.out.println("ola mundo");
                  }
    
});
    }
    private void connectClient() throws IOException {

        Socket socket = new Socket(serverIP, serverPort);

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
  // Thread que serve para o cliente envia mensagens para o servidor
        Thread enviarMensagem = new Thread(() -> {
            while (true) {
                     FXMLDocumentController.confirma.setOnMousePressed(new EventHandler<MouseEvent>(){
                         @Override
                         public void handle(MouseEvent event) {
                             //System.out.println("lel");
                             try {
                                 out.writeUTF("qweqwe");
                             } catch (IOException ex) {
                                 Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                             }
                         }
                         
                     });
        }});
        
         Thread lerMensagem;
        lerMensagem = new Thread(() -> {
            while (true) {
                    String msg;
                try {
                    msg = in.readUTF();
                    if(msg.contains("boas")){
                       FXMLDocumentController.testaLabel.setText("teste");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        enviarMensagem.start();
        lerMensagem.start();
 }

}
