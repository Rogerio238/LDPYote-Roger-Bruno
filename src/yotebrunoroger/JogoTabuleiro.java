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

    /**
     *
     */
    public GridPane gridYote;
    private ControllerJogoGrid gridController;

    /**
     *
     */
    public int id;
    private boolean solicitante;
     private boolean vez;

    /**
     *
     * @param stagePrincipal
     * @param id
     * @param solicitante
     * @throws RemoteException
     */
    public JogoTabuleiro(Stage stagePrincipal, int id, boolean solicitante) throws RemoteException{
        this.stagePrincipal = stagePrincipal;
        this.id = id;
        this.vez = solicitante;
    }
    
    /**
     *
     */
    public void carregaJogo(){
        System.out.println("JogoYote iniciado!");
        
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               
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
                    
                    
                    
                    Platform.runLater(() -> {
                        System.out.println("CARREGOU NOVO INICIO DE JOGO!!!");

                        //gridController.setGridAdversario(tabelaNaviosAdversario);
                        //gridController.setGridPropria(tabelaNaviosPropria);


                        String textoLabel;
                        if (vez)
                            textoLabel = "É a tua vez!";
                        else
                            textoLabel = "É a Vez do Adversario!";
                        
                        //gridController.mudaTextoLabel(textoLabel);
                         
                        if(id==1){
                        gridController.nomeid.setText("Jogador 1");
                        }else if(id==2){
                        gridController.nomeid.setText("Jogador 2");
                        }
                       


                       iniciarJogo();
                    });
                } 
                
            });
        
        }
    
    /**
     *
     */
    public void iniciarJogo(){
    Scene cenaJogo = this.stagePrincipal.getScene();
        GridPane gridJogo = this.gridController.getGridJogo();
               
    }
        
        
    }

