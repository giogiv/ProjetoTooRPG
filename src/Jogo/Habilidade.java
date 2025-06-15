package Jogo;

public class Habilidade {
    protected int index;
    protected String nome;
    protected double dano;
    protected String descricao;
    protected double custoEnergia;
    protected String efeito;

    public Habilidade(int index, String nome, double dano, String descricao, double custoEnergia, String efeito) {
        this.index = index;
        this.nome = nome;
        this.dano = dano;
        this.descricao = descricao;
        this.custoEnergia = custoEnergia;
        this.efeito = efeito;
    }

    public String getNome() {
        return nome;
    }

    public double getDano() {
        return dano;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public double getCustoEnergia() {
        return custoEnergia;
    }

    public String getEfeito() {
        return efeito;
    }
    
}
