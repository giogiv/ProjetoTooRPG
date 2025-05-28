package modelo;

import java.util.List;

public class Personagem {
    
    protected String nome;
    protected Raridade raridade;
    protected Elemento elemento;
    protected Funcao funcao;
    protected Arma arma;
    protected double hp;
    protected double atk;
    protected double def;
    protected double energia;
    protected List<Habilidade> habilidades;
    
    Personagem (String nome, Raridade raridade, Elemento elemento, Funcao funcao){
        this.nome = nome;
        this.raridade = raridade;
        this.elemento = elemento;
        this.funcao = funcao;
    }
    
    
    
}
