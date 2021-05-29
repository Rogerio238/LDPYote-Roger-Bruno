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
    public Player(String nome, Peca[] pecasdoJogador){
        this.nome = nome;
        this.pecasdoJogador = pecasdoJogador;
    }

    public String getNome() {
        return nome;
    }

 public Peca[] getpecasdoJogador(){
     return pecasdoJogador;
 }

    
    
    
}
