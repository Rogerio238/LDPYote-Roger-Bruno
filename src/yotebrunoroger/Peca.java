/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

import java.io.Serializable;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import static yotebrunoroger.pecaCor.Azul;
import static yotebrunoroger.pecaCor.Vermelha;

/**
 *
 * @author senho
 */

enum pecaCor {
  Azul ,
  Vermelha
}

/**
 *
 * @author senho
 */
public class Peca implements Serializable{
    private pecaCor corPeca;
    private boolean estadentro=false;
    private transient Ellipse forma;
    private Color corEscolhida; 
    private int x,y=-1;
    private int idArray;
    private String id;
    private int idAtual = 0;
    private String stringId;

    /**
     *
     * @param cor
     * @param id
     * @param idArray
     */
    public Peca(Color cor,String id, int idArray){
       
       
        forma = new Ellipse((double)30,(double)30);
        forma.setFill(cor);
       this.idArray = idArray;
        this.id = id;
        forma.setVisible(true);
    }
    
    /**
     *
     * @return
     */
    public Ellipse getForma(){
        return forma;
    }
    
    /**
     *
     * @return
     */
    public pecaCor corPeca(){
        return corPeca;
    }
    
    /**
     *
     * @return
     */
    public String getId(){
        return id;
    }
    
    /**
     *
     * @return
     */
    public int getIdArray(){
        return idArray;
    }

    /**
     *
     * @param x
     * @param y
     * @param tabuleiro
     */
    public void movePeca(int x, int y, GridPane tabuleiro){
     tabuleiro.add(forma, x, y);
    }

    /**
     *
     * @return
     */
    public boolean getEstadentro() {
        return estadentro;
    }

    /**
     *
     * @param estadentro
     */
    public void setEstadentro(boolean estadentro) {
        this.estadentro = estadentro;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
  
}
