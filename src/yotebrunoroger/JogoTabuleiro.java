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
 * Classe que contém os métodos para enviar objetos para o servidor
 * @author senho
 */
public class JogoTabuleiro implements Serializable {

    private transient GridPane gridJogo;
    private Peca[] pecasAzuis;
    private Peca[] pecasVermelhas;
    transient Sample obj;
    ObjectOutputStream out;

    /**
     *
     * @param gridJogo
     * @param pecasAzuis
     * @param out
     */
    public JogoTabuleiro(GridPane gridJogo, Peca[] pecasAzuis, ObjectOutputStream out) {
        this.gridJogo = gridJogo;
        this.pecasAzuis = pecasAzuis;

        this.out = out;
    }

    /**
     *
     * @param posArray posição no array de peças geral da peca que se quer inserir
     * @param posX a posicao em x da casa onde se quer inserir
     * @param posY a posicao em y da casa onde se quer inserir
     * @throws IOException
     */
    public void inserePeca(int posArray, int posX, int posY, String nomeJogador) throws IOException {
        obj = new Sample();

        obj.posX = posX;
        obj.posY = posY;
        obj.posArray = posArray;
        obj.nomePlayer = nomeJogador;
        out.writeObject(obj);

    }

    /**
     *
     * @param posArrayAzul posição no array de peças geral da peca azul 
     * @param posArrayVermelha  posição no array de peças geral da peca vermelha 
     * @param posX  posicao em x para onde se quer mandar a peca
     * @param posY  posicao em y para onde se quer mandar a peca
     * @param come variavel que verifica se é um movimento de captura ou simples
     * @throws IOException
     */
    public void movePeca(int posArrayAzul, int posArrayVermelha, int posX, int posY,int xAnterior, int yAnterior, boolean come, String nomeJogador) throws IOException {
        obj = new Sample();

        obj.posX = posX;
        obj.posY = posY;
        obj.posArray = posArrayAzul;
        obj.posArrayVermelha = posArrayVermelha;
        obj.come = come;
        obj.posXAnterior = xAnterior;
        obj.posYAnterior = yAnterior;
        obj.nomePlayer = nomeJogador;
        out.writeObject(obj);

    }
    
    /**
     *
     * @param nome  nome do jogador
     * @throws IOException
     */
    public void mandaNome(String nome) throws IOException{
        obj = new Sample();
        obj.nomePlayer = nome;
        out.writeObject(obj);
    }

}
