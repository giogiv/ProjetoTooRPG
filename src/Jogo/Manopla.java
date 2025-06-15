package Jogo;

public class Manopla extends Arma{

    public Manopla(String nome, int categoria) {
        super("Manopla", 1);
    }

    @Override
    public void configurarAtributos() {
        this.atkbonus = 0.9;
        this.defBonus = 2.0;
        this.velocidade = 1.5;
        this.estilo = EstiloCombate.DEFENSIVO;
    }
}
