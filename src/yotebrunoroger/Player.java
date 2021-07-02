/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

/**
 *
 * @author senho
 */
public class Player {
    private String nome;
    private Peca[] pecasdoJogador;
    private int jogou;
        
    /**
     *
     * @param nome
     * @param pecasdoJogador
     * @param jogou
     */
    public Player(String nome, Peca[] pecasdoJogador, int jogou){
        this.nome = nome;
        this.pecasdoJogador = pecasdoJogador;
          
        this.jogou = jogou;
    }

    /**
     *
     * @return
     */
    public int getJogou() {
        return jogou;
    }

    /**
     *
     * @param jogou
     */
    public void setJogou(int jogou) {
        this.jogou = jogou;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @param pecasdoJogador
     */
    public void setPecasdoJogador(Peca[] pecasdoJogador) {
        this.pecasdoJogador = pecasdoJogador;
    }
    
    /**
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @return
     */
    public Peca[] getpecasdoJogador(){
     return pecasdoJogador;
 }

    
    
    
}
