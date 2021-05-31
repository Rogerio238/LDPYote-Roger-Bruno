/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;


import java.awt.Paint;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;


/**
 *
 * @author roger
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Text textNomeJogador1;
    @FXML
    private Text textNomeJogador2;
    private Ellipse bolaAzul1;
    private int testajogador=1;
    private Ellipse bolaAzul2;
    private Ellipse bolaAzul10;
    private Ellipse bolaAzul9;
    private Color corVerde;
    @FXML
    private TextField meteNomeJogador;
    @FXML
    private Button confirmaNome;
    @FXML
    private GridPane gridTabuleiro;
    private boolean entrou=false;
    Player p1;
    Player p2;
    Peca peca1,peca2,peca3,peca4,peca5,peca6,peca7,peca8,peca9,peca10,peca11,peca12;
    Peca peca1V,peca2V,peca3V,peca4V,peca5V,peca6V,peca7V,peca8V,peca9V,peca10V,peca11V,peca12V;
    @FXML
    private GridPane pecasInicioAzul;
    
    private boolean clicou = false;
    @FXML
    private TextField coordenadaNmr;
    @FXML
    private TextField coordenadaLetra;
    private  int[][] arraytabuleiro;
    private Peca[] pecasAzuis;
    private Peca[] pecasVermelhas;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane pecasInicioVermelhas;
    ObservableList<Node> childrens;
    @FXML
    private AnchorPane escondeAnchor;
    private boolean jogador1Jogou = false;
    private boolean jogador2Jogou = false;
    private ServerSocket ss;
    private Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    boolean posNome = false;
    private  InetAddress inet;
    @FXML
    private TextField meteNomeJogador2;
    @FXML
    private Button confirmaNomeJ2;
    
    private int rondas = 0;
    @FXML
    private Text textJogadorAtual;
    private void handleButtonAction(ActionEvent event) {
        
    }
    private int pecasVermelhasColetadas = 0;
    
    private void escondeElementos(){
        escondeAnchor.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
       
        escondeAnchor.setStyle("-fx-background-color: #F0F8FF");
        escondeElementos();
        childrens = gridTabuleiro.getChildren();
        pecasAzuis = new Peca[12];
        pecasVermelhas = new Peca[12];
        arraytabuleiro= new int [4][5];
        peca1 = new Peca(Color.BLUE);
        peca2 = new Peca(Color.BLUE);
        peca3 = new Peca(Color.BLUE);
        peca4 = new Peca(Color.BLUE);
        peca5 = new Peca(Color.BLUE);
        peca6 = new Peca(Color.BLUE);
        peca7 = new Peca(Color.BLUE);
        peca8 = new Peca(Color.BLUE);
        peca9 = new Peca(Color.BLUE);
        peca10 = new Peca(Color.BLUE);
        peca11= new Peca(Color.BLUE);
        peca12 = new Peca(Color.BLUE);
       p1= new Player("PLAYER 1", pecasAzuis, 0);
        p2= new Player("PLAYER 2", pecasVermelhas, 0);
        pecasAzuis[0] = peca1;
        pecasAzuis[1] = peca2;
        pecasAzuis[2] = peca3;
        pecasAzuis[3] = peca4;
        pecasAzuis[4] = peca5;
        pecasAzuis[5] = peca6;
        pecasAzuis[6] = peca7;
        pecasAzuis[7] = peca8;
        pecasAzuis[8] = peca9;
        pecasAzuis[9] = peca10;
        pecasAzuis[10] = peca11;
        pecasAzuis[11] = peca11;
        
        
        pecasInicioAzul.add(peca1.getForma(),0,0);
        pecasInicioAzul.add(peca2.getForma(),0,1);
        pecasInicioAzul.add(peca3.getForma(),1,1);
        pecasInicioAzul.add(peca4.getForma(),1,0);
        pecasInicioAzul.add(peca5.getForma(),2,1);
        pecasInicioAzul.add(peca6.getForma(),2,0);
        pecasInicioAzul.add(peca7.getForma(),3,1);
        pecasInicioAzul.add(peca8.getForma(),3,0);
        pecasInicioAzul.add(peca9.getForma(),4,1);
        pecasInicioAzul.add(peca10.getForma(),4,0);
        pecasInicioAzul.add(peca11.getForma(),5,1);
        pecasInicioAzul.add(peca12.getForma(),5,0);
        peca1V = new Peca(Color.RED);
        peca2V = new Peca(Color.RED);
        peca3V = new Peca(Color.RED);
        peca4V = new Peca(Color.RED);
        peca5V = new Peca(Color.RED);
        peca6V = new Peca(Color.RED);
        peca7V = new Peca(Color.RED);
        peca8V = new Peca(Color.RED);
        peca9V = new Peca(Color.RED);
        peca10V = new Peca(Color.RED);
        peca11V= new Peca(Color.RED);
        peca12V = new Peca(Color.RED);
        pecasVermelhas[0] = peca1V;
        pecasVermelhas[1] = peca2V;
        pecasVermelhas[2] = peca3V;
        pecasVermelhas[3] = peca4V;
        pecasVermelhas[4] = peca5V;
        pecasVermelhas[5] = peca6V;
        pecasVermelhas[6] = peca7V;
        pecasVermelhas[7] = peca8V;
        pecasVermelhas[8] = peca9V;
        pecasVermelhas[9] = peca10V;
        pecasVermelhas[10] = peca11V;
        pecasVermelhas[11] = peca11V;
        pecasInicioVermelhas.add(peca1V.getForma(),0,0);
        pecasInicioVermelhas.add(peca2V.getForma(),0,1);
        pecasInicioVermelhas.add(peca3V.getForma(),1,1);
        pecasInicioVermelhas.add(peca4V.getForma(),1,0);
        pecasInicioVermelhas.add(peca5V.getForma(),2,1);
        pecasInicioVermelhas.add(peca6V.getForma(),2,0);
        pecasInicioVermelhas.add(peca7V.getForma(),3,1);
        pecasInicioVermelhas.add(peca8V.getForma(),3,0);
        pecasInicioVermelhas.add(peca9V.getForma(),4,1);
        pecasInicioVermelhas.add(peca10V.getForma(),4,0);
        pecasInicioVermelhas.add(peca11V.getForma(),5,1);
        pecasInicioVermelhas.add(peca12V.getForma(),5,0);
       
        textNomeJogador1.setText(peca1.getId());
        gridTabuleiro.setGridLinesVisible(true);
        
            pecasClicaveis();
            pecasClicaveisVermelhas();
         
    }  
        @FXML
     private void confirmaNomeJogador(MouseEvent event) {
       p1.setNome(meteNomeJogador.getText().toString());
      
        textNomeJogador1.setText(meteNomeJogador.getText().toString());
        meteNomeJogador.setVisible(false);
        confirmaNome.setVisible(false);
        p1.setJogou(1);
       pecasClicaveis();
         
    }
    private void pecasClicaveis(){
      
       
        
          try{

        for(Peca p : pecasAzuis){     
           
        p.getForma().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
           
            public void handle(MouseEvent event) { 
           if(testajogador== 1){
                p.getForma().setStroke(Color.RED);
                if(p.getEstadentro()==false){
                
     //Código Para colocar uma peça azul dentro do tabuleiro
               if(arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]==2||arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]==2){
         
                 
         }
               else{
                   
                     arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]=1;
                 gridTabuleiro.add(p.getForma(), Integer.parseInt(coordenadaNmr.getText()),Integer.parseInt(coordenadaLetra.getText()));
                 p.setX(Integer.parseInt(coordenadaNmr.getText()));
                  p.setY(Integer.parseInt(coordenadaLetra.getText()));
                 p.setEstadentro(true);
                  
                 //OBTER A LINHA E COLUNA DA PEÇA NO PRÓPRIO GRIDPANE(GRIDTABULEIRO)
                   System.out.println("Coluna" + GridPane.getColumnIndex(p.getForma()) + "Linha " + GridPane.getRowIndex(p.getForma()));
        System.out.println("isto é : "+ testajogador);
          }
       
       } else{
                    if(Integer.parseInt(coordenadaNmr.getText()) - p.getX() > 1 || Integer.parseInt(coordenadaLetra.getText()) - p.getY() > 1){
                        System.out.println("Não pode colocar uma peça nessa casa!");
                    }else{
                    if(arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]==2||arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]==2){
                     arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText()) - 1]=1;
                     arraytabuleiro[p.getX()][p.getY()]=0;
                     for(Peca pV : pecasVermelhas){
                         if(pV.getX() == GridPane.getRowIndex(pV.getForma()) && pV.getY() == GridPane.getColumnIndex(pV.getForma())){
                             pecasInicioAzul.add(pV.getForma(), 0, 0);
                         }
                     }
                 gridTabuleiro.add(p.getForma(), Integer.parseInt(coordenadaNmr.getText()) ,Integer.parseInt(coordenadaLetra.getText())- 1);
                 p.setEstadentro(true);
                 
         }else{
                   arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]=1;
                     arraytabuleiro[p.getX()][p.getY()]=0;
                 gridTabuleiro.add(p.getForma(), Integer.parseInt(coordenadaNmr.getText()),Integer.parseInt(coordenadaLetra.getText()));
                 p.setEstadentro(true);
                    }
              
        }
                   
                }
           
            

           }
            
            testajogador=2;  }
     });
         p.getForma().setStroke(Color.BLUE);
    
  rondas++;
        }}
  catch(Exception e){
      
  
        }
       
  }
    
private void pecasClicaveisVermelhas(){
     
      
  try{
        for(Peca p : pecasVermelhas){     
           
        p.getForma().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
           
            public void handle(MouseEvent event) { 
                  if(testajogador == 2){
                p.getForma().setStroke(Color.RED);
                if(p.getEstadentro()==false){
                
     //Código Para colocar uma peça azul dentro do tabuleiro
               if(arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]==2||arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]==2){
         
                 
         }
               else{
                   
                     arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]=1;
                 gridTabuleiro.add(p.getForma(), Integer.parseInt(coordenadaNmr.getText()),Integer.parseInt(coordenadaLetra.getText()));
                 p.setX(Integer.parseInt(coordenadaNmr.getText()));
                  p.setY(Integer.parseInt(coordenadaLetra.getText()));
                 p.setEstadentro(true);
                  
                 //OBTER A LINHA E COLUNA DA PEÇA NO PRÓPRIO GRIDPANE(GRIDTABULEIRO)
                   System.out.println("Coluna" + GridPane.getColumnIndex(p.getForma()) + "Linha " + GridPane.getRowIndex(p.getForma()));
        }
                
       } else{
                    if(Integer.parseInt(coordenadaNmr.getText()) - p.getX() > 1 || Integer.parseInt(coordenadaLetra.getText()) - p.getY() > 1){
                        System.out.println("Não pode colocar uma peça nessa casa!");
                    }else{
                    if(arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]==2||arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]==2){
                     arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText()) - 1]=1;
                     arraytabuleiro[p.getX()][p.getY()]=0;
                     for(Peca pV : pecasVermelhas){
                         if(pV.getX() == GridPane.getRowIndex(pV.getForma()) && pV.getY() == GridPane.getColumnIndex(pV.getForma())){
                             pecasInicioAzul.add(pV.getForma(), 0, 0);
                         }
                     }
                 gridTabuleiro.add(p.getForma(), Integer.parseInt(coordenadaNmr.getText()) ,Integer.parseInt(coordenadaLetra.getText())- 1);
                 p.setEstadentro(true);
                 
         }else{
                   arraytabuleiro[Integer.parseInt(coordenadaNmr.getText())][Integer.parseInt(coordenadaLetra.getText())]=1;
                     arraytabuleiro[p.getX()][p.getY()]=0;
                 gridTabuleiro.add(p.getForma(), Integer.parseInt(coordenadaNmr.getText()),Integer.parseInt(coordenadaLetra.getText()));
                 p.setEstadentro(true);
                    }
              
        }
                   
                }
           
           
     }testajogador=1;}
     });
         p.getForma().setStroke(Color.BLUE);
    }}
  catch(Exception e){
      
  
        }
       
    }
    private void clicouPecaAzul1(MouseEvent event) {
        try{
        bolaAzul1.setStroke(Color.GREEN);
        clicou = true;
        gridTabuleiro.add(bolaAzul1, Integer.parseInt(coordenadaNmr.getText()),Integer.parseInt(coordenadaLetra.getText()));
        }catch(Exception e){
           
        }
    }

    @FXML


    private void clicouTabuleiro(MouseEvent event) {
           
    }

    @FXML
    private void confirmaNomeJogador2(MouseEvent event) {
       
         p2.setNome(meteNomeJogador2.getText().toString());
        textNomeJogador2.setText(meteNomeJogador2.getText().toString());
        meteNomeJogador2.setVisible(false);
        confirmaNomeJ2.setVisible(false);
        
         escondeAnchor.setVisible(true);
        p2.setJogou(2);

    }


 
    
}
