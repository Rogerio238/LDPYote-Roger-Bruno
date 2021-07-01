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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author senho
 */
public class TestaMultiServer {

private static int port = 6666, nClientes = 1;
    private static List<ClientHandler> listaClientes = new ArrayList<>();
    private static Socket client;
    static int i = 0;
private static Socket s;
    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Servidor aceita conexões.");
        ServerSocket ss = new ServerSocket(port);

        
            Thread servidor = new Thread(() -> {
        if (i < 2) {
            while (true) {
                try {
                    s = ss.accept();
               
                System.out.println("Novo client recebido : " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                
                
                ClientHandler mtch = new ClientHandler(s, "client " + i, dis, dos, i);

                Thread t = new Thread(mtch);

      
                listaClientes.add(mtch);
                dos.writeUTF("nomeJogador" + mtch.name);
                String idNome = Integer.toString(i);
                t.setName(idNome);
                    System.out.println("Nome " + t.getName());
                    
                    
                     if(mtch.name == t.getName()){
                       
                             dos.writeUTF("nome"+mtch.name);
                         
                     }
                     
                t.start();
                   

                i++;
                          System.out.println("Adiciona cliente " + i + " à lista ativa." + s.getInetAddress());
                /*String leNome = dis.readUTF();
                if(leNome.contains("nomeJogador")){
                    mtch.name = leNome;
                    
                }*/
                
                     } catch (IOException ex) {
                    Logger.getLogger(TestaMultiServer.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                         
           
            }
        }
        }); 
            servidor.start();
    }
                   

    private static class ClientHandler implements Runnable {

        private String name;
        final DataInputStream dis;
        final DataOutputStream dos;
        Socket s;
        boolean isloggedin;
        final int id;

        private ClientHandler(Socket s, String string,
                DataInputStream dis, DataOutputStream dos, int id) {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
            this.name = string;
            this.id = id;
            this.isloggedin = true;
        }

        @Override
        public void run() {
            String recebido;String value;
            int recebeCasaX, recebeCasaY, recebeIndicePeca;

            while (true) {

                try {
                    recebido = dis.readUTF();
                         recebeCasaY = dis.readInt();
                    recebeCasaX = dis.readInt();
               
                    recebeIndicePeca = dis.readInt();
                    System.out.println(recebido);
                    //System.out.println(recebido);
                    if (recebido.endsWith("#logout")) {
                        this.isloggedin = false;
                        this.s.close();
                        break; // while
                    }

                    for (ClientHandler mc : TestaMultiServer.listaClientes) {

                        System.out.println("iei");
                        // mc.dos.writeUTF(recebido);
                      
                        if (recebido.contains("azul")) {
                            
                        System.out.println("muta gayyyyyyyyyyyyyyyyyyyyy"+recebido);
                        
                           mc.dos.writeUTF(recebido);
           
                            recebido = " "; break;
                        }if (recebido.contains("vermelha")) {
                                mc.dos.writeUTF("vermelha");
                           mc.dos.writeInt(recebeCasaY);
                            mc.dos.writeInt(recebeCasaX);
            
                            mc.dos.writeInt(recebeIndicePeca);
                            mc.dos.writeUTF(mc.name);
                            System.out.println("casa x" + recebeCasaX);
                            System.out.println("casa y" + recebeCasaY);
                            recebido = " "; break;
                        } else if (recebido.contains("clicouParaCima")) {
                       mc.dos.writeUTF("clicouParaCima");
                           mc.dos.writeInt(recebeCasaY);
                            mc.dos.writeInt(recebeCasaX);
                        
                            mc.dos.writeInt(recebeIndicePeca);
                            mc.dos.writeUTF(mc.name);
                            System.out.println("casa x" + recebeCasaX);
                            System.out.println("casa y" + recebeCasaY);
                            recebido = " "; break;
                        } 
                       
                        
                        if (recebido.contains("#chat")) {
                             System.out.println(recebido);
                            System.out.println(recebido);
                            mc.dos.writeUTF("#chat" + recebido);
                            recebido = " "; break;
                        }

                        break;

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
