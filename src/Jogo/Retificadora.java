package Jogo;

public class Retificadora extends Arma{

    public Retificadora(String nome, int categoria) {
        super("Retificadora", 5);
    }

    @Override
    public void configurarAtributos() {
        this.atkbonus = 2.0;
        this.defBonus = 1.5;
        this.velocidade = 1.5;
        this.estilo = EstiloCombate.OFENSIVO;
    }
}
