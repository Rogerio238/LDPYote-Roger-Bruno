/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;


import java.awt.Paint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane bolaAzul3;
    @FXML
    private Text textNomeJogador1;
    @FXML
    private Text textNomeJogador2;
    @FXML
    private Ellipse bolaAzul1;
    @FXML
    private Ellipse bolaAzul2;
    @FXML
    private Ellipse bolaAzul6;
    @FXML
    private Ellipse bolaAzul5;
    @FXML
    private Ellipse bolaAzul4;
    @FXML
    private Ellipse bolaAzul10;
    @FXML
    private Ellipse bolaAzul11;
    @FXML
    private Ellipse bolaAzul12;
    @FXML
    private Ellipse bolaAzul9;
    @FXML
    private Ellipse bolaAzul8;
    @FXML
    private Ellipse bolaAzul7;
    @FXML
    private Ellipse bolaVermelha7;
    @FXML
    private Ellipse bolaVermelha8;
    @FXML
    private Ellipse bolaVermelha9;
    @FXML
    private Ellipse bolaVermelha12;
    @FXML
    private Ellipse bolaVermelha11;
    @FXML
    private Ellipse bolaVermelha10;
    @FXML
    private Ellipse bolaVermelha4;
    @FXML
    private Ellipse bolaVermelha5;
    @FXML
    private Ellipse bolaVermelha6;
    @FXML
    private Ellipse bolaVermelha3;
    @FXML
    private Ellipse bolaVermelha2;
    @FXML
    private Ellipse bolaVermelha1;
    private Color corVerde;
    
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    

    @FXML
    private void clicouPecaAzul1(MouseEvent event) {
        bolaAzul1.setStroke(Color.GREEN);
    }
    
}
