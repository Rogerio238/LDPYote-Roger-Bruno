/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.scene.layout.GridPane;

/**
 *
 * @author senho
 */
public class JogoTabuleiro implements Serializable {

    private transient GridPane gridJogo;
    private Peca[] pecasAzuis;
    private Peca[] pecasVermelhas;
    transient Sample obj;
    ObjectOutputStream out;

    public JogoTabuleiro(GridPane gridJogo, Peca[] pecasAzuis, ObjectOutputStream out) {
        this.gridJogo = gridJogo;
        this.pecasAzuis = pecasAzuis;

        this.out = out;
    }

    public void inserePeca(int posArray, int posX, int posY) throws IOException {
        obj = new Sample();

        obj.posX = posX;
        obj.posY = posY;
        obj.posArray = posArray;
        out.writeObject(obj);

    }

    public void movePeca(int posArrayAzul, int posArrayVermelha, int posX, int posY, boolean come) throws IOException {
        obj = new Sample();

        obj.posX = posX;
        obj.posY = posY;
        obj.posArray = posArrayAzul;
        obj.posArrayVermelha = posArrayVermelha;
        obj.come = come;
        out.writeObject(obj);

    }

}
