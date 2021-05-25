/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

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
public class Peca {
    private pecaCor corPeca;
    
    private Ellipse forma;
    private Color corEscolhida; 
    private int id;
    private int idAtual = 0;
    private String stringId;
    public Peca(Color cor){
       
        id++;
        idAtual = id;
        forma = new Ellipse((double)30,(double)30);
        forma.setFill(cor);
       
        stringId = Integer.toString(id);
        forma.setVisible(true);
    }
    
    public Ellipse getForma(){
        return forma;
    }
    
    public pecaCor corPeca(){
        return corPeca;
    }
    
    public String getId(){
        return stringId;
    }
  
}
