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
import static yotebrunoroger.FXMLDocumentController.pecasAzuisEstatico;

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
    private int casaX = 0;
    private int casaY = 0;
    private String valorCasas = " ";
    private boolean posNome = false;
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
        connectClient();
        Platform.runLater(() -> {FXMLDocumentController.labelControlaJogadorEstatica.setText("É o jogador 1 a jogar"); });
        FXMLDocumentController.gridEstatico.setVisible(false);
        FXMLDocumentController.pecasInicioAzulEstatico.setVisible(false);
        FXMLDocumentController.pecasInicioVermelhasEstatico.setVisible(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        //System.out.println("Hello");

    

    }
    private void connectClient() throws IOException {

        Socket socket = new Socket(serverIP, serverPort);

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        Thread enviarNome = new Thread(() -> {
              while (true) {
            
              }
        });
  // Thread que serve para o cliente envia mensagens para o servidor
        Thread enviarMensagem = new Thread(() -> {
            while (true) {
                FXMLDocumentController.confirmaNomeJogadorEstatico.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                     try {
                out.writeUTF("Nome" + FXMLDocumentController.meteNomeJogadorestatico.getText());
                 Platform.runLater(() -> {FXMLDocumentController.textNomeJogador1Estatico.setText(FXMLDocumentController.meteNomeJogadorestatico.getText());
                 FXMLDocumentController.gridEstatico.setVisible(true);
        FXMLDocumentController.pecasInicioAzulEstatico.setVisible(true);
        FXMLDocumentController.pecasInicioVermelhasEstatico.setVisible(true);
                 });
            } catch (IOException ex) {
                Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
                
            });
                for(Peca p : FXMLDocumentController.pecasAzuisEstatico){
                   p.getForma().setOnMousePressed(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("vem do cliente");
                        try {
                                 out.writeUTF("clicou");
                                  Platform.runLater(() -> {FXMLDocumentController.gridEstatico.add(p.getForma(),Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()),Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()));
                                  FXMLDocumentController.labelControlaJogadorEstatica.setText("É o jogador 2 a jogar"); });
                                  casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                  casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText());
                                  System.out.println(casaX + casaY);
                                  
                                  out.writeInt(casaX);
                                  out.writeInt(casaY);
                             } catch (IOException ex) {
                                 Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                             }
                    }
                       
                   });
                           }
                  
        }});
         
         Thread lerMensagem;
        lerMensagem = new Thread(() -> {
           
            while (true) {
                    String msg;
                    int casasX, casasY;
                    String recebeNome;
                try {
                    msg = in.readUTF();
                    casasX = in.readInt();
                    casasY = in.readInt();
                    recebeNome = in.readUTF();
                    if(msg.contains("clicou")){
                        System.out.println("Outro clicou");
                         
             Platform.runLater(() -> {FXMLDocumentController.gridEstatico.add(FXMLDocumentController.pecasAzuisEstatico[0].getForma(),casasX,casasY);
                                  FXMLDocumentController.labelControlaJogadorEstatica.setText("É o jogador 2 a jogar"); });
                    }
                    else if(msg.contains("Nome")){
                        Platform.runLater(() -> {FXMLDocumentController.textNomeJogador2Estatico.setText(recebeNome);});
                        System.out.println(msg);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
       
        });
          //enviarNome.start();
         lerMensagem.start();
       enviarMensagem.start();
     
  
       
 }

}
