package Jogo;

public class Pistola extends Arma{

    public Pistola(String nome, int categoria) {
        super("Pistola", 3);
    }

    @Override
    public void configurarAtributos() {
        this.atkbonus = 1.5;
        this.defBonus = 1.0;
        this.velocidade = 2.0;
        this.estilo = EstiloCombate.OFENSIVO;
    }
}
