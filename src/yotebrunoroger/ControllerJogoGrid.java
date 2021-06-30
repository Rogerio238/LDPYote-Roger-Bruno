/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author senho
 */
public class ControllerJogoGrid implements Initializable {

    /**
     *
     */
    public Label nomeid;

    /**
     *
     */
    public Label estadoJogo;

    /**
     *
     */
    public GridPane gridJogo;
       private boolean tabela[][];
        private final int tamQuadrado = 50;
         private final int altura = 10;
            private final int largura = 10;
              private Rectangle rectsJogar[][];
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    /**
     *
     * @param texto
     */
    public void mudaTextoLabel(String texto){
            System.out.println("TEXTO DA LABEL: " + estadoJogo.getText());
            estadoJogo.setText(texto);
    }
     
    /**
     *
     * @param tabela
     */
    public void setGridAdversario(boolean[][] tabela){
        gridJogo.setHgap(this.tamQuadrado / 10);
        gridJogo.setVgap(this.tamQuadrado / 10);

        this.tabela = tabela;
        this.rectsJogar = new Rectangle[altura][largura];

        for (int x = 0;x < altura;++x){
            for (int y = 0;y < largura;++y) {

                Rectangle rect = new Rectangle(this.tamQuadrado,this.tamQuadrado, Color.WHITE);

                gridJogo.add(rect,x,y);

                this.rectsJogar[x][y] = rect;
            }
        }

        this.pintaGridJogo();
    }
     
        private void pintaGridJogo(){
        //pinta todos os navios
        for (int x = 0;x < this.altura;++x)
            for (int y = 0;y < this.largura;++y)
                if (this.tabela[x][y])
                    this.rectsJogar[x][y].setFill(Color.BLACK);

    }

    /**
     *
     * @return
     */
    public GridPane getGridJogo(){
        return this.gridJogo;
    }
}
