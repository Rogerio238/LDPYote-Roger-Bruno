/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

import java.awt.Paint;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
public class FXMLDocumentController implements Initializable, Runnable {

    private Label label;
    @FXML
    private Text textNomeJogador1;

    /**
     *
     */
    public static Text textNomeJogador1Estatico;
    /**
     *
     */
    @FXML
    public Text textNomeJogador2;

    /**
     *
     */
    public static Text textNomeJogador2Estatico;
    private Ellipse bolaAzul1;
    private int testajogador = 1;
    private Ellipse bolaAzul2;
    private Ellipse bolaAzul10;
    private Ellipse bolaAzul9;
    private Color corVerde;
    final static int ServerPort = 1234;
    private TextField meteNomeJogador;

    /**
     *
     */
    public static TextField meteNomeJogadorestatico;
    private Button confirmaNome;

    /**
     *
     */
    @FXML
    public GridPane gridTabuleiro;
    private boolean entrou = false;
    Player p1;
    Player p2;
    Peca peca1, peca2, peca3, peca4, peca5, peca6, peca7, peca8, peca9, peca10, peca11, peca12;
    Peca peca1V, peca2V, peca3V, peca4V, peca5V, peca6V, peca7V, peca8V, peca9V, peca10V, peca11V, peca12V;
    @FXML
    private GridPane pecasInicioAzul;

    /**
     *
     */
    public static GridPane pecasInicioAzulEstatico;

    private boolean clicou = false;
    @FXML
    private TextField coordenadaNmr;
    @FXML
    private TextField coordenadaLetra;
    
    /**
     *
     */
    public static TextField coordenadaEsquerdaEstatico;

    /**
     *
     */
    public static TextField coordenadaDireitaEstatico;
    private int[][] arraytabuleiro;

    /**
     *
     */
    public Peca[] pecas;

    /**
     *
     */
    

    /**
     *
     */
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane pecasInicioVermelhas;

    /**
     *
     */
    public static GridPane pecasInicioVermelhasEstatico;
    ObservableList<Node> childrens;

    /**
     *
     */
    @FXML
    public AnchorPane escondeAnchor;
    private boolean jogador1Jogou = false;
    private boolean jogador2Jogou = false;
    private ServerSocket ss;
    private Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    boolean posNome = false;
     ObjectInputStream oos ;

    /**
     *
     */
    public static Button confirma;
    static FXMLDocumentController instancia;

    /**
     *
     */
    public static Label testaLabel;

    /**
     *
     */
    public static GridPane gridEstatico;
    /**
     *
     * @return
     */
  
    private InetAddress inet;
 String nomeJogador = " saddas";
    private int rondas = 0;
    @FXML
    private Text textJogadorAtual;
    private Label labelConectado;
    @FXML
    private Button buttonSair;
    
    /**
     *
     */
    public static Button buttonSairEstatico;
    int porta = 4000;

    /**
     *
     */
    public static Button confirmaNomeJogadorEstatico;

    /**
     *
     */
    public Button confirmaNomeParaMandar;

    /**
     *
     */
    public static Peca[] pecasEstatico;
    private Label labelControlaJogador;

    /**
     *
     */
    public static Label labelControlaJogadorEstatica;
    @FXML
    private Button botaoParaCima;

    /**
     *
     */
    public static Button botaoParacimaEstatico;
    @FXML
    private TextArea outputChatText;

    /**
     *
     */
    public static TextArea outputChatTextEstatico;
    @FXML
    private TextField inputChatText;
    
    /**
     *
     */
    public static TextField inputChatTestEstatico;
    @FXML
    private Text pecascomidas;

    /**
     *
     */
    public static Text pecascomidasEstatico;
    @FXML
    private Button botaoParaBaixo;
    
    /**
     *
     */
    public static Button botaoParaBaixoEstatico;
    @FXML
    private Button botaoParaDireita;

    /**
     *
     */
    public static Button botaoParaDireitaEstatico;
    private Button botaoParaEsquerda;
    public static Button botaoParaEsquerdaEstatico;
    @FXML
    private Text pecascomidas1;
    public static Text pecascomidas1Estatico;
    private void handleButtonAction(ActionEvent event) {

    }
    private int pecasVermelhasColetadas = 0;

   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
          atualizaJogo();   
          pecascomidas1Estatico = pecascomidas1;
          botaoParaDireitaEstatico = botaoParaDireita;
          botaoParaEsquerdaEstatico = botaoParaEsquerda;
          botaoParaBaixoEstatico = botaoParaBaixo;
          pecascomidasEstatico=pecascomidas;
        confirmaNomeJogadorEstatico = confirmaNome;
        testaLabel = labelConectado;
       pecasEstatico = pecas;
      
       gridEstatico = gridTabuleiro;
       labelControlaJogadorEstatica = labelControlaJogador;
       coordenadaEsquerdaEstatico = coordenadaNmr;
       coordenadaDireitaEstatico = coordenadaLetra;
       meteNomeJogadorestatico = meteNomeJogador;
       pecasInicioAzulEstatico = pecasInicioAzul;
       pecasInicioVermelhasEstatico = pecasInicioVermelhas;
       textNomeJogador1Estatico = textNomeJogador1;
       textNomeJogador2Estatico = textNomeJogador2;
       botaoParacimaEstatico = botaoParaCima;
       buttonSairEstatico = buttonSair;
       inputChatTestEstatico = inputChatText;
       outputChatTextEstatico = outputChatText;
    }     
       
    /**
     *
     */
    @Override
public void run() {
   
  
}

    /**
     *
     */
    public  void atualizaJogo(){
        
         
     
      
        childrens = gridTabuleiro.getChildren();
        pecas = new Peca[24];
      
        peca1 = new Peca(Color.BLUE,"azul",0);
        peca2 = new Peca(Color.BLUE,"azul",1);
        peca3 = new Peca(Color.BLUE,"azul",2);
        peca4 = new Peca(Color.BLUE,"azul",3);
        peca5 = new Peca(Color.BLUE,"azul",4);
        peca6 = new Peca(Color.BLUE,"azul",5);
        peca7 = new Peca(Color.BLUE,"azul",6);
        peca8 = new Peca(Color.BLUE,"azul",7);
        peca9 = new Peca(Color.BLUE,"azul",8);
        peca10 = new Peca(Color.BLUE,"azul",9);
        peca11 = new Peca(Color.BLUE,"azul",10);
        peca12 = new Peca(Color.BLUE,"azul",11);
        peca1V = new Peca(Color.RED,"vermelha",12);
        peca2V = new Peca(Color.RED,"vermelha",13);
        peca3V = new Peca(Color.RED,"vermelha",14);
        peca4V = new Peca(Color.RED,"vermelha",15);
        peca5V = new Peca(Color.RED,"vermelha",16);
        peca6V = new Peca(Color.RED,"vermelha",16);
        peca7V = new Peca(Color.RED,"vermelha",17);
        peca8V = new Peca(Color.RED,"vermelha",18);
        peca9V = new Peca(Color.RED,"vermelha",19);
        peca10V = new Peca(Color.RED,"vermelha",20);
        peca11V = new Peca(Color.RED,"vermelha",21);
        peca12V = new Peca(Color.RED,"vermelha",22);
       
        pecas[0] = peca1;
        pecas[1] = peca2;
        pecas[2] = peca3;
        pecas[3] = peca4;
        pecas[4] = peca5;
        pecas[5] = peca6;
        pecas[6] = peca7;
        pecas[7] = peca8;
        pecas[8] = peca9;
        pecas[9] = peca10;
        pecas[10] = peca11;
        pecas[11] = peca11;
        pecas[12] = peca1V;
         pecas[13] = peca2V;
        pecas[14] = peca3V;
        pecas[15] = peca4V;
         pecas[16] = peca5V;
        pecas[17] = peca6V;
        pecas[18] = peca7V;
         pecas[19] = peca8V;
        pecas[20] = peca9V;
        pecas[21] = peca10V;
         pecas[22] = peca11V;
        pecas[23] = peca12V;
        
        pecasInicioAzul.add(peca1.getForma(), 0, 0);
        pecasInicioAzul.add(peca2.getForma(), 0, 1);
        pecasInicioAzul.add(peca3.getForma(), 1, 1);
        pecasInicioAzul.add(peca4.getForma(), 1, 0);
        pecasInicioAzul.add(peca5.getForma(), 2, 1);
        pecasInicioAzul.add(peca6.getForma(), 2, 0);
        pecasInicioAzul.add(peca7.getForma(), 3, 1);
        pecasInicioAzul.add(peca8.getForma(), 3, 0);
        pecasInicioAzul.add(peca9.getForma(), 4, 1);
        pecasInicioAzul.add(peca10.getForma(), 4, 0);
        pecasInicioAzul.add(peca11.getForma(), 5, 1);
        pecasInicioAzul.add(peca12.getForma(), 5, 0);
       
      
        pecasInicioVermelhas.add(peca1V.getForma(), 0, 0);
        pecasInicioVermelhas.add(peca2V.getForma(), 0, 1);
        pecasInicioVermelhas.add(peca3V.getForma(), 1, 1);
        pecasInicioVermelhas.add(peca4V.getForma(), 1, 0);
        pecasInicioVermelhas.add(peca5V.getForma(), 2, 1);
        pecasInicioVermelhas.add(peca6V.getForma(), 2, 0);
        pecasInicioVermelhas.add(peca7V.getForma(), 3, 1);
        pecasInicioVermelhas.add(peca8V.getForma(), 3, 0);
        pecasInicioVermelhas.add(peca9V.getForma(), 4, 1);
        pecasInicioVermelhas.add(peca10V.getForma(), 4, 0);
        pecasInicioVermelhas.add(peca11V.getForma(), 5, 1);
        pecasInicioVermelhas.add(peca12V.getForma(), 5, 0);

        textNomeJogador1.setText(peca1.getId());
        gridTabuleiro.setGridLinesVisible(true);

        
       
    }
   

    private void pecasClicaveis() {


    }

    private void pecasClicaveisVermelhas() {


    }

   
    private void clicouPecaAzul1(MouseEvent event) {
        try {
            bolaAzul1.setStroke(Color.GREEN);
            clicou = true;
            gridTabuleiro.add(bolaAzul1, Integer.parseInt(coordenadaNmr.getText()), Integer.parseInt(coordenadaLetra.getText()));
         
        } catch (Exception e) {

        }
    }

    @FXML

    private void clicouTabuleiro(MouseEvent event) {

    }
  
    @FXML
    private void botaoClicouSair(MouseEvent event) {
        
    }


    
}
