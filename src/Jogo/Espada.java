package Jogo;

public class Espada extends Arma{

    public Espada(String nome, int categoria) {
        super("Espada", 4);
    }

    @Override
    public void configurarAtributos() {
        this.atkbonus = 2.0;
        this.defBonus = 2.0;
        this.velocidade = 1.5;
        this.estilo = EstiloCombate.BALANCEADO;
    }
}
