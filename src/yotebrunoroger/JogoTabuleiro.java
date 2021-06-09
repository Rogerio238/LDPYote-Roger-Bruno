/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author senho
 */
public class JogoTabuleiro  extends UnicastRemoteObject {
    private Stage stagePrincipal;
    public GridPane gridYote;
    private ControllerJogoGrid gridController;
      public int id;
    private boolean solicitante;
    public JogoTabuleiro(Stage stagePrincipal, int id, boolean solicitante) throws RemoteException{
        this.stagePrincipal = stagePrincipal;
        this.id = id;
        this.solicitante = solicitante;
    }
    
    
    public void carregaJogo(){
        System.out.println("JogoYote iniciado!");
        
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try{
                      FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocumentController.fxml"));
                       gridController = new ControllerJogoGrid();

                    loader.setController(gridController);
                      Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                      
                        stagePrincipal.setScene(new Scene(root));
                    stagePrincipal.show();
                } catch(RemoteException e){
                    e.printStackTrace();
                }
                
            }
        });
        
        
        
    }
}
