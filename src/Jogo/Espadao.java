package Jogo;

public class Espadao extends Arma{

    public Espadao(String nome, int categoria) {
        super("Espadao", 2);
    }

    @Override
    public void configurarAtributos() {
        this.atkbonus = 2.5;
        this.defBonus = 2.5;
        this.velocidade = 0.7;
        this.estilo = EstiloCombate.DEFENSIVO;
    }
}
