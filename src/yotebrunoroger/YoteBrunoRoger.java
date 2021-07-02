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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static yotebrunoroger.FXMLDocumentController.inputChatTestEstatico;
import static yotebrunoroger.FXMLDocumentController.outputChatTextEstatico;
import static yotebrunoroger.FXMLDocumentController.pecasAzuisEstatico;
import static yotebrunoroger.FXMLDocumentController.pecascomidasEstatico;

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
    private String jogouNaCasa = "Jogou na casa ";

    /**
     *
     */
    public static Button teste;

    /**
     *
     */
    public static int yo = 0;
    private int count = 0;
 
    private int casaX, casaY = 0;
    private String valorCasas = " ";
    private final Text text = new Text(Integer.toString(count));
    private int indiceDaPeca = 0;
    private int[][] arraySuporte = new int[5][6];
    private int pecasvcomidas = 0;

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
        Platform.runLater(() -> {
            FXMLDocumentController.labelControlaJogadorEstatica.setText("É o jogador 1 a jogar");
        });
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {

                arraySuporte[i][j] = 0;

            }
        }
        printTabuleiro();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        System.out.println("Hello");

    }

    private void connectClient() throws IOException {

        Socket socket = new Socket(serverIP, serverPort);

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        String nomeJogadorserver = in.readUTF();
        if (nomeJogadorserver.contains("nomeJogador")) {
            FXMLDocumentController.textNomeJogador1Estatico.setText(nomeJogadorserver);
        } else if (nomeJogadorserver.contains("nome")) {
            FXMLDocumentController.textNomeJogador2Estatico.setText(nomeJogadorserver);
        }
        // Thread que serve para o cliente envia mensagens para o servidor
        Thread enviarMensagem = new Thread(() -> {
            while (true) {

                FXMLDocumentController.confirmaNomeJogadorEstatico.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            String nomeJogador = FXMLDocumentController.meteNomeJogadorestatico.getText();
                            out.writeUTF("clicou nome" + nomeJogador);
                            Platform.runLater(() -> {
                                FXMLDocumentController.textNomeJogador1Estatico.setText(FXMLDocumentController.meteNomeJogadorestatico.getText());
                            });

                        } catch (IOException ex) {
                            Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                });

                
                FXMLDocumentController.confirmaNomeJogadorEstatico.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            String nomeJogador = FXMLDocumentController.meteNomeJogadorestatico.getText();
                            out.writeUTF("clicou nome" + nomeJogador);
                            Platform.runLater(() -> {
                            FXMLDocumentController.textNomeJogador1Estatico.setText(FXMLDocumentController.meteNomeJogadorestatico.getText());
                            });
                            
                        } catch (IOException ex) {
                            Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                         }
                    
                });
                
                
                
                
                
                
                FXMLDocumentController.inputChatTestEstatico.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent k) {
                        if (k.getCode().equals(KeyCode.ENTER)) {
                            try {

                                out.writeUTF("#Chat");
                                System.out.println(FXMLDocumentController.inputChatTestEstatico.getText());
                                Platform.runLater(() -> {
                                    FXMLDocumentController.outputChatTextEstatico.appendText(FXMLDocumentController.inputChatTestEstatico.getText() + "\n");

                                    FXMLDocumentController.inputChatTestEstatico.setText("");
                                });
                                inputChat.setText("");
                            } catch (IOException ex) {
                                //Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NullPointerException e) {

                            }
                        }
                    }
                });

                //102 - 114 codigo para dar logout no socket e sair da aplicação
                FXMLDocumentController.buttonSairEstatico.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            out.writeUTF("#logout");
                            Platform.exit();
                        } catch (IOException ex) {
                            Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                });
                try{
                //Código para colocar uma peça do jogo e movimentar nas 4 direções
                for (Peca p : FXMLDocumentController.pecasAzuisEstatico) {
                    
                    if (p.getEstadentro() == true) {
                        FXMLDocumentController.botaoParacimaEstatico.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] == 1) {

                                    if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 1) {
                                        System.out.println("não pode mover");
                                    } else {
                                        if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 0) {
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 1;
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                            Platform.runLater(() -> {

                                                FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                                FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getRowIndex(p.getForma()), GridPane.getColumnIndex(p.getForma()) - 1);

                                            });
                                            casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                            casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) - 1;
                                            //System.out.println(casaX + casaY);
                                            indiceDaPeca = findIndex(FXMLDocumentController.pecasAzuisEstatico, p);
                                            System.out.println(indiceDaPeca);

                                            
                                        } else if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 2 && arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 2][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 0) {
                                           //P campo onde se encontrava a peça azul antes de capturar fica a 0
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                           //O Campo onde se encontrava a vermelha a ser capturada fica a 0
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 0;
                                             //O campo a cima da peça vermelha a ser capturada fica a a 1
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 2][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 1;

                                            Platform.runLater(() -> {
                                                FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                                FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getRowIndex(p.getForma()), GridPane.getColumnIndex(p.getForma()) - 2);
                                                for (Peca p1 : FXMLDocumentController.pecasVermelhasEstatico) {
                                                    if (GridPane.getRowIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1 && GridPane.getColumnIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ) {
                                                        FXMLDocumentController.gridEstatico.getChildren().remove(p1.getForma());
                                                        removepeca(FXMLDocumentController.pecasVermelhasEstatico, p1);

                                                        FXMLDocumentController.pecascomidasEstatico.setText(Integer.toString(pecasvcomidas));
                                                        System.out.println("comeu");
                                                    }
                                                }

                                            });
                                            casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                            casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) - 2;
                                            //System.out.println(casaX + casaY);
                                            indiceDaPeca = findIndex(FXMLDocumentController.pecasAzuisEstatico, p);
                                        }
                                    }
                                    try {
                                             out.writeUTF("azul" + nomeJogadorserver);
                                        out.writeUTF("clicou clicouParaCima");
                                        out.writeInt(indiceDaPeca);
                                        out.writeInt(casaX);
                                        out.writeInt(casaY);
                                    } catch (IOException ex) {
                                        Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                            }

                        });
                        
                        FXMLDocumentController.botaoParaBaixoEstatico.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                              if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] == 1) {

                                    if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 1) {
                                        System.out.println("não pode mover");
                                    } else {
                                        if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 0) {
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 1;
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                            Platform.runLater(() -> {

                                                FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                                FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getRowIndex(p.getForma()), GridPane.getColumnIndex(p.getForma()) + 1);

                                            });
                                            casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                            casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1;
                                            //System.out.println(casaX + casaY);
                                            indiceDaPeca = findIndex(FXMLDocumentController.pecasAzuisEstatico, p);
                                            System.out.println(indiceDaPeca);

                                            
                                        } else if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 2 && arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 2][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 0) {
                                           //P campo onde se encontrava a peça azul antes de capturar fica a 0
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                           //O Campo onde se encontrava a vermelha a ser capturada fica a 0
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 0;
                                             //O campo a cima da peça vermelha a ser capturada fica a a 1
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 2][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 1;

                                            Platform.runLater(() -> {
                                                FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                                FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getRowIndex(p.getForma()), GridPane.getColumnIndex(p.getForma()) + 2);
                                                for (Peca p1 : FXMLDocumentController.pecasVermelhasEstatico) {
                                                    if (GridPane.getRowIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 1 && GridPane.getColumnIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ) {
                                                        FXMLDocumentController.gridEstatico.getChildren().remove(p1.getForma());
                                                        removepeca(FXMLDocumentController.pecasVermelhasEstatico, p1);

                                                        FXMLDocumentController.pecascomidasEstatico.setText(Integer.toString(pecasvcomidas));
                                                        System.out.println("comeu");
                                                    }
                                                }

                                            });
                                            casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                            casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 2;
                                            //System.out.println(casaX + casaY);
                                            indiceDaPeca = findIndex(FXMLDocumentController.pecasAzuisEstatico, p);
                                        }
                                    }
                                    try {
                                             out.writeUTF("azul" + nomeJogadorserver);
                                        out.writeUTF("clicou clicouParaCima");
                                        out.writeInt(indiceDaPeca);
                                        out.writeInt(casaX);
                                        out.writeInt(casaY);
                                    } catch (IOException ex) {
                                        Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                            
                        });
                        
                        FXMLDocumentController.botaoParaDireitaEstatico.setOnMousePressed(new EventHandler<MouseEvent>(){
                            @Override
                            public void handle(MouseEvent event) {
                                if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] == 1) {

                                    if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1 ] == 1) {
                                        System.out.println("não pode mover");
                                    } else {
                                        if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1] == 0) {
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1] = 1;
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                            Platform.runLater(() -> {

                                                FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                                FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getRowIndex(p.getForma()), GridPane.getColumnIndex(p.getForma()) + 1);

                                            });
                                            casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()  + 1);
                                            casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText());
                                            //System.out.println(casaX + casaY);
                                            indiceDaPeca = findIndex(FXMLDocumentController.pecasAzuisEstatico, p);
                                            System.out.println(indiceDaPeca);

                                            
                                        } else if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())+ 1 ] == 2 && arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())+ 2 ] == 0) {
                                           //P campo onde se encontrava a peça azul antes de capturar fica a 0
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                           //O Campo onde se encontrava a vermelha a ser capturada fica a 0
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1] = 0;
                                             //O campo a cima da peça vermelha a ser capturada fica a a 1
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 2] = 1;

                                            Platform.runLater(() -> {
                                                FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                                FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getRowIndex(p.getForma() ), GridPane.getColumnIndex(p.getForma())  + 2);
                                                for (Peca p1 : FXMLDocumentController.pecasVermelhasEstatico) {
                                                    if (GridPane.getRowIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()) && GridPane.getColumnIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1) {
                                                        FXMLDocumentController.gridEstatico.getChildren().remove(p1.getForma());
                                                        removepeca(FXMLDocumentController.pecasVermelhasEstatico, p1);

                                                        FXMLDocumentController.pecascomidasEstatico.setText(Integer.toString(pecasvcomidas));
                                                        System.out.println("comeu");
                                                    }
                                                }

                                            });
                                            casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()) + 2;
                                            casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ;
                                            //System.out.println(casaX + casaY);
                                            indiceDaPeca = findIndex(FXMLDocumentController.pecasAzuisEstatico, p);
                                        }
                                    }
                                    try {
                                             out.writeUTF("azul" + nomeJogadorserver);
                                        out.writeUTF("clicou clicouParaCima");
                                        out.writeInt(indiceDaPeca);
                                        out.writeInt(casaX);
                                        out.writeInt(casaY);
                                    } catch (IOException ex) {
                                        Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                              }
                            
                        });
                    } else {
                        p.getForma().setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                System.out.println("vem do cliente");
                                try {
                                    out.writeUTF("azul" + nomeJogadorserver + "array " + printTabuleiro());

                                    if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())] == 0) {
                                        Platform.runLater(() -> {
                                            FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                            FXMLDocumentController.gridEstatico.add(p.getForma(), Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()), Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()));
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 1;
                                            FXMLDocumentController.labelControlaJogadorEstatica.setText("É o jogador 2 a jogar");
                                            FXMLDocumentController.outputChatTextEstatico.appendText(jogouNaCasa + " " + casaX + " " + casaY + "\n");

                                            printTabuleiro();

                                        });
                                        casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                        casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText());
                                        //System.out.println(casaX + casaY);
                                        indiceDaPeca = findIndex(FXMLDocumentController.pecasAzuisEstatico, p);
                                        System.out.println(indiceDaPeca);
                                        out.writeInt(indiceDaPeca);
                                        out.writeInt(casaX);
                                        out.writeInt(casaY);
                                        
                                        p.setEstadentro(true);
                                    } else {

                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }

                        });
                    }

                }}catch(NullPointerException e){
                    System.out.println("erro");
                }
                //codigo para a peça vermelha
                try{
                   for (Peca p : FXMLDocumentController.pecasVermelhasEstatico) {
                    if (p.getEstadentro() == true) {
                        FXMLDocumentController.botaoParacimaEstatico.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] == 2) {

                                    if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 2) {
                                        System.out.println("não pode mover");
                                    } else {
                                        if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 0) {
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 2;
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                            Platform.runLater(() -> {

                                                FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                                FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getRowIndex(p.getForma()), GridPane.getColumnIndex(p.getForma()) - 1);

                                            });
                                            casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                            casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) - 1;
                                            //System.out.println(casaX + casaY);
                                            indiceDaPeca = findIndex(FXMLDocumentController.pecasVermelhasEstatico, p);
                                            System.out.println(indiceDaPeca);

                                            
                                        } else if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 1 && arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 2][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 0) {
                                           //P campo onde se encontrava a peça vermelha antes de capturar fica a 0
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                           //O Campo onde se encontrava a azul a ser capturada fica a 0
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 0;
                                             //O campo a cima da peça azul a ser capturada fica a 1
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 2][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 1;

                                            Platform.runLater(() -> {
                                                FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                                FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getRowIndex(p.getForma()), GridPane.getColumnIndex(p.getForma()) - 2);
                                                for (Peca p1 : FXMLDocumentController.pecasAzuisEstatico) {
                                                    if (GridPane.getRowIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1 && GridPane.getColumnIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ) {
                                                        FXMLDocumentController.gridEstatico.getChildren().remove(p1.getForma());
                                                        removepeca(FXMLDocumentController.pecasAzuisEstatico, p1);

                                                        FXMLDocumentController.pecascomidasEstatico.setText(Integer.toString(pecasvcomidas));
                                                        System.out.println("comeu");
                                                    }
                                                }

                                            });
                                            casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                            casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) - 2;
                                            //System.out.println(casaX + casaY);
                                            indiceDaPeca = findIndex(FXMLDocumentController.pecasVermelhasEstatico, p);
                                        }
                                    }
                                    try {
                                             out.writeUTF("vermelha" + nomeJogadorserver);
                                        out.writeUTF("clicou clicouParaCima");
                                        out.writeInt(indiceDaPeca);
                                        out.writeInt(casaX);
                                        out.writeInt(casaY);
                                    } catch (IOException ex) {
                                        Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                            }

                        });
                        
                        FXMLDocumentController.botaoParaBaixoEstatico.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                   if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] == 2) {

                                    if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()) + 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 2) {
                                        System.out.println("não pode mover");
                                    } else {
                                        if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 0) {
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 2;
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                            Platform.runLater(() -> {

                                                FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                                FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getRowIndex(p.getForma()), GridPane.getColumnIndex(p.getForma()) + 1);

                                            });
                                            casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                            casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1;
                                            //System.out.println(casaX + casaY);
                                            indiceDaPeca = findIndex(FXMLDocumentController.pecasVermelhasEstatico, p);
                                            System.out.println(indiceDaPeca);

                                            
                                        } else if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())- 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 1 && arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()) + 2][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] == 0) {
                                           //P campo onde se encontrava a peça vermelha antes de capturar fica a 0
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                           //O Campo onde se encontrava a azul a ser capturada fica a 0
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()) + 1][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 0;
                                             //O campo a cima da peça azul a ser capturada fica a 1
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()) + 2][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ] = 1;

                                            Platform.runLater(() -> {
                                                FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                                FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getRowIndex(p.getForma()), GridPane.getColumnIndex(p.getForma()) + 2);
                                                for (Peca p1 : FXMLDocumentController.pecasAzuisEstatico) {
                                                    if (GridPane.getRowIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())+ 1 && GridPane.getColumnIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) ) {
                                                        FXMLDocumentController.gridEstatico.getChildren().remove(p1.getForma());
                                                        removepeca(FXMLDocumentController.pecasAzuisEstatico, p1);

                                                        FXMLDocumentController.pecascomidasEstatico.setText(Integer.toString(pecasvcomidas));
                                                        System.out.println("comeu");
                                                    }
                                                }

                                            });
                                            casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                            casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 2;
                                            //System.out.println(casaX + casaY);
                                            indiceDaPeca = findIndex(FXMLDocumentController.pecasVermelhasEstatico, p);
                                        }
                                    }
                                    try {
                                             out.writeUTF("vermelha" + nomeJogadorserver);
                                        out.writeUTF("clicou clicouParaCima");
                                        out.writeInt(indiceDaPeca);
                                        out.writeInt(casaX);
                                        out.writeInt(casaY);
                                    } catch (IOException ex) {
                                        Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                    }
                            
                        });
                    } else {
                        p.getForma().setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                System.out.println("vem do cliente");
                                try {
                                    out.writeUTF("vermelha" + nomeJogadorserver + "array " + printTabuleiro());

                                    if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())] == 0) {
                                        Platform.runLater(() -> {
                                            FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                            FXMLDocumentController.gridEstatico.add(p.getForma(), Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()), Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()));
                                            arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 2;
                                            FXMLDocumentController.labelControlaJogadorEstatica.setText("É o jogador 1 a jogar");
                                            FXMLDocumentController.outputChatTextEstatico.appendText(jogouNaCasa + " " + casaX + " " + casaY + "\n");

                                            printTabuleiro();

                                        });
                                        casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                        casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText());
                                        //System.out.println(casaX + casaY);
                                        indiceDaPeca = findIndex(FXMLDocumentController.pecasAzuisEstatico, p);
                                        System.out.println(indiceDaPeca);
                                        out.writeInt(indiceDaPeca);
                                        out.writeInt(casaX);
                                        out.writeInt(casaY);
                                        
                                        p.setEstadentro(true);
                                    } else {

                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }

                        });
                    }

                }
                } catch(NullPointerException e){
                           
                           }

            }
        });

        Thread lerMensagem;
        lerMensagem = new Thread(() -> {
            System.out.println("asd");
            while (true) {
                String msg, nomeJogador;
                int casasY, recebeIndicePeca,casasX;
                int casaNoClienteX;
                int casaNoClienteY, recebeIndicePecaCliente;
                try {
                    msg = in.readUTF();
                    casasX = in.readInt();
                    casasY = in.readInt();
                    recebeIndicePeca = in.readInt();
                    nomeJogador = in.readUTF();

                    if (msg.contains("azul")) {
                        System.out.println("Outro clicou");
                        
                        Platform.runLater(() -> {
                            try {
                                FXMLDocumentController.gridEstatico.getChildren().remove(FXMLDocumentController.pecasAzuisEstatico[recebeIndicePeca].getForma());
                                FXMLDocumentController.gridEstatico.add(FXMLDocumentController.pecasAzuisEstatico[recebeIndicePeca].getForma(), casasY, casasX);
                                FXMLDocumentController.outputChatTextEstatico.appendText("#SERVER " + nomeJogador + " " + jogouNaCasa + " " + casasX + " " + casasY + "\n");
                                arraySuporte[casasX][casasY] = 1;
                                printTabuleiro();
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.getMessage();
                            }
                            FXMLDocumentController.labelControlaJogadorEstatica.setText("É o jogador 2 a jogar");
                        });
                    } else if (msg.contains("vermelha")) {
                        System.out.println(msg);
                        casaNoClienteX = casasX;
                        casaNoClienteY = casasY;
                        recebeIndicePecaCliente = recebeIndicePeca;
                        Platform.runLater(() -> {
                            try {
                                FXMLDocumentController.gridEstatico.getChildren().remove(FXMLDocumentController.pecasVermelhasEstatico[recebeIndicePeca].getForma());
                                FXMLDocumentController.gridEstatico.add(FXMLDocumentController.pecasVermelhasEstatico[recebeIndicePeca].getForma(),  casasX, casasY);
                                FXMLDocumentController.outputChatTextEstatico.appendText("#SERVER " + nomeJogador + " " + jogouNaCasa + " " + casasX + " " + casasY + "\n");
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.getMessage();
                            }
                            FXMLDocumentController.labelControlaJogadorEstatica.setText("É o jogador 2 a jogar");
                        });
                    } else if (msg.contains("clicouParaCima")) {
                        casaNoClienteX = casasX;
                        casaNoClienteY = casasY;
                        recebeIndicePecaCliente = recebeIndicePeca;
                        Platform.runLater(() -> {
                            try {
                                FXMLDocumentController.gridEstatico.getChildren().remove(FXMLDocumentController.pecasAzuisEstatico[recebeIndicePeca].getForma());
                                FXMLDocumentController.gridEstatico.add(FXMLDocumentController.pecasAzuisEstatico[recebeIndicePeca].getForma(), casasY, (int) casasX);
                                FXMLDocumentController.outputChatTextEstatico.appendText("#SERVER " + nomeJogador + " " + jogouNaCasa + " " + casasX + " " + casasY + "\n");
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.getMessage();
                            }
                            FXMLDocumentController.labelControlaJogadorEstatica.setText("É o jogador 2 a jogar");
                        });
                    } else if (msg.contains("#chat")) {
                        System.out.println(msg);
                        Platform.runLater(() -> {
                            FXMLDocumentController.outputChatTextEstatico.appendText(msg + "\n");
                        });
                    }

                } catch (Exception ex) {
                    Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        lerMensagem.start();
        enviarMensagem.start();

    }

    /**
     *
     * @param arr
     * @param t
     * @return
     */
    public static int findIndex(Peca arr[], Peca t) {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            } else {
                i = i + 1;
            }
        }
        return -1;
    }

    public static void apagaPeca(Peca arr[], Peca p) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == p) {
                // shifting elements
                for (int j = i; j < arr.length - 1; j++) {
                    arr[j] = arr[j + 1];

                }
                break;
            }
        }
        System.out.println(arr.length);

    }

    public void removepeca(Peca[] array, Peca p) {
        for (int i = 0; i < array.length; i++) {

            if (array[i] == p) {
                array[i] = null;
                System.out.println(array[i]);
                pecasvcomidas++;
                break;

            }
        }

    }

    public String printTabuleiro() {
        int linhas = -1;
        int colunas = -1;
        System.out.println("  0     1       2       3       4");
        System.out.println("----------------------------------");
        for (int x = 0; x < 5; x++) {
            linhas++;
            System.out.print(linhas + "|");
            for (int y = 0; y < (this.arraySuporte[x]).length; y++) {
                System.out.print(this.arraySuporte[x][y]);
                if (y != (this.arraySuporte[x]).length - 1) {
                    System.out.print("\t");
                }
            }
            System.out.println("|");
        }
        System.out.println("----------------------------------");
        System.out.println("");
        System.out.println("---------------------------------");
 return "array " + arraySuporte;
    }
   
}