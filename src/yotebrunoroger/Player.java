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
        
    public Player(String nome, Peca[] pecasdoJogador, int jogou){
        this.nome = nome;
        this.pecasdoJogador = pecasdoJogador;
          
        this.jogou = jogou;
    }

    public int getJogou() {
        return jogou;
    }

    public void setJogou(int jogou) {
        this.jogou = jogou;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

  

    public void setPecasdoJogador(Peca[] pecasdoJogador) {
        this.pecasdoJogador = pecasdoJogador;
    }
    
    

    public String getNome() {
        return nome;
    }

 public Peca[] getpecasdoJogador(){
     return pecasdoJogador;
 }

    
    
    
}
