/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author senho
 */
public class TestaMultiServer {
Ellipse p = new Ellipse();
    static Vector<ClientHandler> ar = new Vector<>();
    static int i = 0;
 private static FXMLDocumentController jogo;
    public static void main(String[] args) throws IOException {
        System.out.println("Servidor aceita conexões.");
        ServerSocket ss = new ServerSocket(1234);
 
        Socket s;
        while (true) {
            s = ss.accept();
            System.out.println("Novo client recebido : " + s);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            String recebidoNome = dis.readUTF();
            ClientHandler mtch = new ClientHandler(s, recebidoNome, dis, dos);
            Thread t = new Thread(mtch);
           
            System.out.println("Adiciona cliente " + mtch.name + " à lista ativa.");
            ar.add(mtch);
            t.start();

            i++;
        }
    }
 public void setMainController(FXMLDocumentController jogo) {
        this.jogo = jogo;
    }
    private static class ClientHandler implements Runnable {

        private String name;
        final DataInputStream dis;
        final DataOutputStream dos;
        Socket s;

        boolean isloggedin;

        private ClientHandler(Socket s, String string,
                DataInputStream dis, DataOutputStream dos) {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
            this.name = string;
            this.isloggedin = true;
        }

        @Override
        public void run() {
            String recebido;
FXMLDocumentController.getInstancia().posicionaUmaPeca(1, 1);
            while (true) {
                try {
                    recebido = dis.readUTF();
                    System.out.println(recebido);
                    
                    for (ClientHandler client : TestaMultiServer.ar) {
                        if (client.name != recebido) {
                            dos.writeUTF(recebido);
                        }
                    }
                    
                    
                    System.out.println(recebido);
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
