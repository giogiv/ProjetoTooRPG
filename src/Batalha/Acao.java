package Batalha;

public class Acao {
    public static void main(String[] args) {
        System.out.println("------- Bem-vindo ao meu jogo de RPG de luta! ----------");
        System.out.println();

        Batalha batalha = Batalha.criarBatalha();
        batalha.iniciarBatalha();
        
        System.out.println();
        System.out.println("Obrigada por jogar!");
    }
}
