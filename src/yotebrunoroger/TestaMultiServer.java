/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author senho
 */
public class TestaMultiServer {
Ellipse p = new Ellipse();
    static List<ClientHandler> ar = new ArrayList<>();
    static int i = 0;
     static String teste = "teste";
     static Timer timer = new Timer();
 private static FXMLDocumentController jogo;
    public static void main(String[] args) throws IOException {
        System.out.println("Servidor aceita conexões.");
        ServerSocket ss = new ServerSocket(1234);
 
        Socket s;
        while (true && ar.size() < 2) {
            s = ss.accept();
            System.out.println("Novo client recebido : " + s);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            String recebidoNome = dis.readUTF();
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
           
            ClientHandler mtch = new ClientHandler(s, recebidoNome,i, dis, dos, jogo);
            Thread t = new Thread(mtch);
           
            System.out.println("Adiciona cliente " + mtch.name + i  + " à lista ativa.");
           System.out.println("este é o client no servidor " + mtch);
            ar.add(mtch);
           
              
         mtch.jogo.atualizaJogo();
            t.start();
        
   
            System.out.println(t);
          
        }
    }
 public void setMainController(FXMLDocumentController jogo) {
        this.jogo = jogo;
    }
    private static class ClientHandler extends Thread implements Runnable {

        private String name;
        final DataInputStream dis;
        final DataOutputStream dos;
        Socket s;
          int id;
        boolean isloggedin;
 FXMLDocumentController jogo;
        private ClientHandler(Socket s, String string,int id,
                DataInputStream dis, DataOutputStream dos, FXMLDocumentController jogo) {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
            this.name = string;
            this.jogo = jogo;
this.id = id;            this.isloggedin = true;
        }
        @Override
        public void run() {
            String recebido;

            while (true) {
                try {
                
                    
                    
                    for (ClientHandler client : TestaMultiServer.ar) {
                     
                            if(!client.name.equals(dis.readUTF())){
                                recebido = dis.readUTF();
                                dos.writeUTF(recebido);
                            }
                    }
                    
                    
                 
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
    
    
    
    
}
