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
  private static int port = 6666, nClientes = 1;
   private static Vector<ClientHandler> listaClientes = new Vector<>();
   private static Socket client;
   static int i = 0;
   
     public static void main(String[] args) throws IOException {
System.out.println("Servidor aceita conexões.");
ServerSocket ss = new ServerSocket(port);

Socket s;
while(true){
s = ss.accept();
System.out.println("Novo client recebido : " + s);

DataInputStream dis = new DataInputStream(s.getInputStream());
DataOutputStream dos = new DataOutputStream(s.getOutputStream());

ClientHandler mtch = new ClientHandler(s, "client " + i, dis, dos,i);

Thread t = new Thread(mtch);

System.out.println("Adiciona cliente "+ i + " à lista ativa.");
listaClientes.add(mtch);
t.start();

i++;
}
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
String recebido;
int recebeCasaX, recebeCasaY;
String recebeNome;
while(true){
    
try {
    recebeNome = dis.readUTF();
           recebido = dis.readUTF();
           recebeCasaX = dis.readInt();
           recebeCasaY = dis.readInt();
           System.out.println(recebido);
           //System.out.println(recebido);
           if(recebido.endsWith("logout")){
           this.isloggedin = false;
               this.s.close();
                   break; // while
}

          
               for(ClientHandler mc: TestaMultiServer.listaClientes){
                   if(recebeNome.contains("Nome")){
                   mc.dos.writeUTF(recebeNome);
                   }
                   System.out.println("iei");
                          // mc.dos.writeUTF(recebido);
                          if(recebido.contains("clicou")){
                              mc.dos.writeUTF("clicou");
                              mc.dos.writeInt(recebeCasaX);
                              mc.dos.writeInt(recebeCasaY);
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
