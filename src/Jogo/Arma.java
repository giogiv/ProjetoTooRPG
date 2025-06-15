package Jogo;

abstract public class Arma {

    protected String nome;
    protected int categoria;
    protected double atkbonus;
    protected double defBonus;
    protected double velocidade;
    protected EstiloCombate estilo;
    
    public Arma(String nome, int categoria){
        this.nome = nome;
        this.categoria = categoria;
    }
    
    public abstract void configurarAtributos();
    
    // calcula o dano baseado no estilo de combate
    public double calcularDano(double atkBase){
        double dano;
        
        switch (estilo) {
            case OFENSIVO -> dano = (atkBase * atkBase) * atkbonus;
            case DEFENSIVO -> dano = (atkBase / defBonus) * atkbonus;
            case BALANCEADO -> dano = atkBase * atkbonus;
            default -> throw new AssertionError();
        }
        return dano;
    }

    public String getNome() {
        return nome;
    }
    
    public int getCategoria() {
        return categoria;
    }

    public double getAtkbonus() {
        return atkbonus;
    }

    public double getDefBonus() {
        return defBonus;
    }
    
    public double getVelocidade() {
        return velocidade;
    }

    public EstiloCombate getEstilo() {
        return estilo;
    }

}
