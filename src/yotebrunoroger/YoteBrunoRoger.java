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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
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
import static yotebrunoroger.FXMLDocumentController.pecasEstatico;
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
    static ObjectOutputStream objOut;
    static ObjectInputStream inObj;
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
    private JogoTabuleiro jogoInstancia;

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

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {

                arraySuporte[i][j] = 0;

            }
        }
        printTabuleiro();

        FXMLDocumentController.gridEstatico.setOnMousePressed(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (FXMLDocumentController.gridEstatico.getChildren() != null) {
                    System.out.println(FXMLDocumentController.gridEstatico.getRowIndex((Node) event.getTarget()));
                    System.out.println(FXMLDocumentController.gridEstatico.getColumnIndex((Node) event.getTarget()));

                }
            }

        });
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

        objOut = new ObjectOutputStream(socket.getOutputStream());

        inObj = new ObjectInputStream(socket.getInputStream());
        jogoInstancia = new JogoTabuleiro(FXMLDocumentController.gridEstatico, FXMLDocumentController.pecasEstatico, objOut);
        String nomeJogadorserver = in.readUTF();
        if (nomeJogadorserver.contains("nomeJogador")) {
            FXMLDocumentController.textNomeJogador1Estatico.setText(nomeJogadorserver);
        } else if (nomeJogadorserver.contains("nome")) {
            FXMLDocumentController.textNomeJogador2Estatico.setText(nomeJogadorserver);
        }
        // Thread que serve para o cliente envia mensagens para o servidor
        Thread enviarMensagem = new Thread(() -> {
            FXMLDocumentController.inputChatTestEstatico.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent k) {
                    if (k.getCode().equals(KeyCode.ENTER)) {
                        try {
                            System.out.println(FXMLDocumentController.inputChatTestEstatico.getText());
                            out.writeUTF("#chat" + " - " + FXMLDocumentController.inputChatTestEstatico.getText());
                            System.out.println(FXMLDocumentController.inputChatTestEstatico.getText());
                            Platform.runLater(() -> {
                                FXMLDocumentController.outputChatTextEstatico.appendText("Tu :  " + FXMLDocumentController.inputChatTestEstatico.getText() + "\n");

                                FXMLDocumentController.inputChatTestEstatico.setText("");
                            });

                        } catch (IOException ex) {
                            Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            for (Peca p : FXMLDocumentController.pecasEstatico) {
                FXMLDocumentController.botaoParaDireitaEstatico.setOnMousePressed(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println(p.getId());
                        if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] == 1) {

                            if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1] == 1) {
                                System.out.println("não pode mover");
                            } else {
                                if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1] == 0) {
                                    arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1] = 1;
                                    arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                    Platform.runLater(() -> {

                                        FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                        FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getColumnIndex(p.getForma()) + 1, GridPane.getRowIndex(p.getForma()));
                                        System.out.println(arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][0]);
                                        System.out.println(arraySuporte[0][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1]);
                                    });
                                    casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                    casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1;
                                    //System.out.println(casaX + casaY);
                                    indiceDaPeca = findIndex(FXMLDocumentController.pecasEstatico, p);
                                    System.out.println(indiceDaPeca);

                                } else if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1] == 2) {
                                    //P campo onde se encontrava a peça azul antes de capturar fica a 0
                                    arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                    //O Campo onde se encontrava a vermelha a ser capturada fica a 0
                                    arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1] = 0;
                                    //O campo a cima da peça vermelha a ser capturada fica a a 1
                                    arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 2] = 1;

                                    Platform.runLater(() -> {
                                        FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                        FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getColumnIndex(p.getForma()) + 2, GridPane.getRowIndex(p.getForma()));
                                        for (Peca p1 : FXMLDocumentController.pecasEstatico) {
                                            if (GridPane.getRowIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()) && GridPane.getColumnIndex(p1.getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1) {
                                                FXMLDocumentController.gridEstatico.getChildren().remove(p1.getForma());
                                                removepeca(FXMLDocumentController.pecasEstatico, p1);

                                                FXMLDocumentController.pecascomidasEstatico.setText(Integer.toString(pecasvcomidas));
                                                System.out.println("comeu");
                                            }
                                        }

                                    });
                                    casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                    casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 2;
                                    //System.out.println(casaX + casaY);
                                    indiceDaPeca = findIndex(FXMLDocumentController.pecasEstatico, p);
                                }
                            }

                        }
                    }

                });

                p.getForma().setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("olhaa aquiiiii " + p.getId());
                        System.out.println("vem do cliente");
                        if (p.getEstadentro() == false) {
                            if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())] == 0) {
                                Platform.runLater(() -> {
                                    FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                    FXMLDocumentController.gridEstatico.add(p.getForma(), Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()), Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()));
                                    if (p.getId().equals("azul")) {
                                        arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 1;
                                    } else {
                                        arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 2;
                                    }
                                    FXMLDocumentController.outputChatTextEstatico.appendText(jogouNaCasa + " " + casaX + " " + casaY + "\n");

                                    printTabuleiro();

                                });
                                casaX = Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText());
                                casaY = Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText());
                                //System.out.println(casaX + casaY);
                                indiceDaPeca = findIndex(FXMLDocumentController.pecasEstatico, p);

                                System.out.println(indiceDaPeca);
                                try {
                                    for (int i = 0; i < 24; i++) {
                                        if (p == FXMLDocumentController.pecasEstatico[i]) {

                                            out.writeUTF("#obj");
                                            jogoInstancia.inserePeca(i, Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()), Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()));
                                        }
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                p.setEstadentro(true);
                            } else {

                            }
                        } else {
                            if (arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] == 2) {
                                arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) - 1] = 0;
                                arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 0;
                                arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1] = 1;
                                int posVermelha = 0;
                                Platform.runLater(() -> {

                                    removePeca();
                                    FXMLDocumentController.gridEstatico.getChildren().remove(p.getForma());
                                    FXMLDocumentController.gridEstatico.add(p.getForma(), GridPane.getColumnIndex(p.getForma()) + 2, GridPane.getRowIndex(p.getForma()));

                                    /*if(p.getId().equals("azul")){
                                arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 1;
                                }else{
                                       arraySuporte[Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText())][Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())] = 2;
                                }*/
                                    //FXMLDocumentController.outputChatTextEstatico.appendText(jogouNaCasa + " " + casaX + " " + casaY + "\n");
                                    printTabuleiro();
                                    try {
                                        for (int i = 0; i < 24; i++) {
                                            if (p == FXMLDocumentController.pecasEstatico[i]) {

                                                out.writeUTF("#obj");
                                                jogoInstancia.movePeca(i,removePeca(), Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()), Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText()) + 1, true);
                                            }
                                        }
                                    } catch (IOException ex) {
                                        Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                });

                            }
                        }
                    }

                });
            }

        });

        Thread lerMensagem;
        lerMensagem = new Thread(() -> {
            while (true) {

                try {
                    String msg = in.readUTF();
                    Sample obj1;
                    if (msg.contains("#chat")) {
                        System.out.println(msg);

                        Platform.runLater(() -> {
                            FXMLDocumentController.outputChatTextEstatico.appendText(msg + "\n");
                        });

                    } else if (msg.startsWith("teste2")) {
                        System.out.println("teste 2 " + msg);
                        Platform.runLater(() -> {
                            FXMLDocumentController.outputChatTextEstatico.appendText(msg + "\n");
                        });
                    } else if (msg.equals("azul")) {
                        System.out.println(msg);
                    } else if (msg.equals("#obj")) {
                        obj1 = (Sample) inObj.readObject();
                        System.out.println("x" + obj1.posX + "y" + obj1.posY);

                        Platform.runLater(() -> {
                            FXMLDocumentController.outputChatTextEstatico.appendText("Server :  " + obj1.posX + "y" + obj1.posY + "\n");

                            FXMLDocumentController.inputChatTestEstatico.setText("");
                            FXMLDocumentController.gridEstatico.getChildren().remove(FXMLDocumentController.pecasEstatico[obj1.posArray].getForma());
                           FXMLDocumentController.gridEstatico.add(FXMLDocumentController.pecasEstatico[obj1.posArray].getForma(), obj1.posY , obj1.posX);
                           
                                        FXMLDocumentController.gridEstatico.getChildren().remove(FXMLDocumentController.pecasEstatico[obj1.posArrayVermelha].getForma());
                                
                                 
                            
                            if (FXMLDocumentController.pecasEstatico[obj1.posArray].getId().equals("azul")) {
                                arraySuporte[obj1.posX][obj1.posY] = 1;
                            } else {
                                arraySuporte[obj1.posX][obj1.posY] = 2;
                            }
                            printTabuleiro();

                        });
                    }
                    else if (msg.equals("#objMove")) {
                        obj1 = (Sample) inObj.readObject();
                        System.out.println("x" + obj1.posX + "y" + obj1.posY);

                        Platform.runLater(() -> {
                            FXMLDocumentController.outputChatTextEstatico.appendText("Server :  " + obj1.posX + "y" + obj1.posY + "\n");

                            FXMLDocumentController.inputChatTestEstatico.setText("");
                             FXMLDocumentController.gridEstatico.getChildren().remove(FXMLDocumentController.pecasEstatico[obj1.posArray].getForma());
                            FXMLDocumentController.gridEstatico.add(FXMLDocumentController.pecasEstatico[obj1.posArray].getForma(), obj1.posY + 1, obj1.posX);
                           
                            if (FXMLDocumentController.pecasEstatico[obj1.posArray].getId().equals("azul")) {
                                arraySuporte[obj1.posX][obj1.posY] = 1;
                            } else {
                                arraySuporte[obj1.posX][obj1.posY] = 2;
                            }
                            printTabuleiro();

                        });
                    }

                } catch (IOException e) {

                } catch (ClassNotFoundException ex) {
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

    /**
     *
     * @param arr
     * @param p
     */
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

    public int removePeca() {
        int posVermelha = 0;
        for (int i = 0; i < 24; i++) {
            if (GridPane.getRowIndex(FXMLDocumentController.pecasEstatico[i].getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaEsquerdaEstatico.getText()) && GridPane.getColumnIndex(FXMLDocumentController.pecasEstatico[i].getForma()) == Integer.parseInt(FXMLDocumentController.coordenadaDireitaEstatico.getText())) {
                FXMLDocumentController.gridEstatico.getChildren().remove(FXMLDocumentController.pecasEstatico[i].getForma());
                // posVermelha = i;

                posVermelha =  i;
            }
        }
       return posVermelha;
    }

    /**
     *
     * @param array
     * @param p
     */
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

    /**
     *
     * @return
     */
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

    public void enviaInformação(Socket s, int coordX, int coordY, String tag) {

        if (s.isConnected()) {
            try {
                out.writeUTF(tag);
                out.writeInt(indiceDaPeca);
                out.writeInt(casaX);
                out.writeInt(casaY);
            } catch (IOException ex) {
                Logger.getLogger(YoteBrunoRoger.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Não esta conectado");
        }

    }

}
