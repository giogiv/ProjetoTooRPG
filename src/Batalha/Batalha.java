package Batalha;

import Jogo.Arma;
import Jogo.CustoInimigo;
import Jogo.Elemento;
import Jogo.Espada;
import Jogo.Espadao;
import Jogo.Funcao;
import Jogo.Habilidade;
import Jogo.Inimigo;
import Jogo.Manopla;
import Jogo.Personagem;
import Jogo.Pistola;
import Jogo.Raridade;
import Jogo.Retificadora;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Batalha {

    private Personagem jogador;
    private Inimigo inimigo;
    private Scanner scanner;
    private int turno;
    private boolean batalhaAtiva;

    public Batalha(Personagem jogador, Inimigo inimigo) {
        this.jogador = jogador;
        this.inimigo = inimigo;
        this.scanner = new Scanner(System.in);
        this.turno = 1;
        this.batalhaAtiva = true;
    }

    public void iniciarBatalha() {
        System.out.println("=== INICIO DA BATALHA ===");
        System.out.println(jogador.getNome() + " VS " + inimigo.getNome());
        System.out.println("========================");

        // configura a arma do jogador se ainda não estiver configurada
        if (jogador.getArma() != null) {
            jogador.getArma().configurarAtributos();
        }

        while (batalhaAtiva) {
            exibirStatusBatalha();

            // turno do jogador
            if (turnoJogador()) {
                break; // Inimigo foi derrotado
            }

            // verifica se o jogador ainda está vivo
            if (jogador.getHp() <= 0) {
                System.out.println(jogador.getNome() + " foi derrotado!");
                batalhaAtiva = false;
                break;
            }

            // turno do inimigo
            if (turnoInimigo()) {
                break; // jogador perdeu
            }

            // verifica se o inimigo ainda está vivo
            if (inimigo.getHp() <= 0) {
                System.out.println(inimigo.getNome() + " foi derrotado!");
                batalhaAtiva = false;
                break;
            }

            turno++;
            System.out.println("\n" + "=".repeat(50));
        }

        finalizarBatalha();
    }

    private void exibirStatusBatalha() {
        System.out.println("\n--- TURNO " + turno + " ---");
        System.out.printf("%s: HP=%.0f | Energia=%.0f%n",
                jogador.getNome(), jogador.getHp(), jogador.getEnergia());
        System.out.printf("%s: HP=%.0f | Custo=%s%n",
                inimigo.getNome(), inimigo.getHp(), inimigo.getCusto());
        System.out.println();
    }

    private boolean turnoJogador() {
        System.out.println("=== TURNO DO JOGADOR ===");
        System.out.println("Escolha sua acao:");
        System.out.println("1. Usar habilidade");
        System.out.println("2. Trocar arma");
        System.out.println("3. Ver status detalhado");
        System.out.println("4. Fugir da batalha");

        int escolha = scanner.nextInt();

        switch (escolha) {
            case 1 -> {
                return usarHabilidadeJogador();
            }
            case 2 -> {
                trocarArmaJogador();
                return false;
            }
            case 3 -> {
                jogador.StatusAtual();
                return turnoJogador(); // permite escolher novamente
            }
            case 4 -> {
                System.out.println(jogador.getNome() + " desistiu e fugiu da batalha!");
                batalhaAtiva = false;
                return true;
            }
            default -> {
                System.out.println("opcao invalida! perdeu o turno.");
                return false;
            }
        }
    }

    private boolean usarHabilidadeJogador() {
        System.out.println("\nHabilidades disponiveis: ");
        System.out.printf("Energia atual: %.0f/100%n%n", jogador.getEnergia());
        List<Habilidade> habilidades = jogador.getHabilidade();

        for (int i = 0; i < habilidades.size(); i++) {
            Habilidade hab = habilidades.get(i);
            String status = jogador.getEnergia() >= hab.getCustoEnergia() ? "S" : "X";
            System.out.printf("%s %d. %s (Dano: %.0f | Energia: %.0f) - %s%n",
                    status, i + 1, hab.getNome(), hab.getDano(),
                    hab.getCustoEnergia(), hab.getDescricao());
        }

        System.out.println("--------------------------------");
        System.out.print("Escolha uma habilidade (1-" + habilidades.size() + "): ");
        int escolhaHab = scanner.nextInt() - 1;

        if (escolhaHab >= 0 && escolhaHab < habilidades.size()) {
            Habilidade habilidadeEscolhida = habilidades.get(escolhaHab);

            if (jogador.getEnergia() >= habilidadeEscolhida.getCustoEnergia()) {
                // Usa a habilidade
                jogador.setEnergia(jogador.getEnergia() - habilidadeEscolhida.getCustoEnergia());

                // Calcula dano com base na arma
                double danoFinal = calcularDanoJogador(habilidadeEscolhida);

                // Aplica dano no inimigo
                double novoHpInimigo = inimigo.getHp() - danoFinal;
                inimigo.setHp(Math.max(0, novoHpInimigo));

                System.out.printf("\n %s usou %s!%n", jogador.getNome(), habilidadeEscolhida.getNome());
                System.out.printf("Causou %.1f de dano em %s!%n", danoFinal, inimigo.getNome());
                System.out.printf("%s%n", habilidadeEscolhida.getDescricao());
                System.out.printf("Energia restante: %.0f%n", jogador.getEnergia());

                // Regenera energia
                jogador.setEnergia(Math.min(100, jogador.getEnergia() + 5));
                System.out.printf("Energia regenerada: %.0f%n", jogador.getEnergia());

                return inimigo.getHp() <= 0;
            } else {
                System.out.println("Energia insuficiente! (Atual: %.0f | Necessária: %.0f) Perdeu o turno.%n" + jogador.getEnergia()+ habilidadeEscolhida.getCustoEnergia());
                return false;
            }
        } else {
            System.out.println("Habilidade invalida! Perdeu o turno.");
            return false;
        }
    }

    private double calcularDanoJogador(Habilidade habilidade) {
        double danoBase = habilidade.getDano();

        if (jogador.getArma() != null) {
            // usa o metodo da arma para calcular dano
            double danoArma = jogador.getArma().calcularDano(jogador.getAtk());
            return danoBase + (danoArma * 0.5); // combina dano da habilidade com bônus da arma
        }

        return danoBase * (jogador.getAtk() / 100.0);
    }

    private void trocarArmaJogador() {
        System.out.println("\nArmas disponiveis:");
        System.out.println("1. Espada (Balanceada)");
        System.out.println("2. Espadao (Defensiva)");
        System.out.println("3. Manopla (Defensiva)");
        System.out.println("4. Pistola (Ofensiva)");
        System.out.println("5. Retificadora (Ofensiva)");

        int escolhaArma = scanner.nextInt();
        Arma novaArma = null;

        switch (escolhaArma) {
            case 1 ->
                novaArma = new Espada("Espada do Jogador", 4);
            case 2 ->
                novaArma = new Espadao("Espadão do Jogador", 2);
            case 3 ->
                novaArma = new Manopla("Manopla do Jogador", 1);
            case 4 ->
                novaArma = new Pistola("Pistola do Jogador", 3);
            case 5 ->
                novaArma = new Retificadora("Retificadora do Jogador", 5);
            default -> {
                System.out.println("Arma inválida!");
                return;
            }
        }

        novaArma.configurarAtributos();
        jogador.trocarArma(novaArma);

        System.out.printf("Nova arma equipada: %s%n", novaArma.getClass().getSimpleName());
        System.out.printf("Estilo: %s | Velocidade: %.1f | ATK Bonus: %.1f%n",
                novaArma.getEstilo(), novaArma.getVelocidade(), novaArma.getAtkbonus());
    }

    private boolean turnoInimigo() {
        System.out.println("\n=== TURNO DO INIMIGO ===");

        // inimigo usa uma habilidade aleatória
        Habilidade habilidadeInimigo = inimigo.usarHabilidade();
        double danoInimigo = inimigo.calcularDano(habilidadeInimigo);

        System.out.printf("%s usou %s!%n", inimigo.getNome(), habilidadeInimigo.getNome());

        if (habilidadeInimigo.getDano() > 0) {
            // Reduz dano pela defesa do jogador
            double danoFinal = Math.max(1, danoInimigo - (jogador.getDef() * 0.5));
            double novoHpJogador = jogador.getHp() - danoFinal;
            jogador.setHp(Math.max(0, novoHpJogador));

            System.out.printf("Causou %.1f de dano em %s!%n", danoFinal, jogador.getNome());
        } else if (habilidadeInimigo.getDano() < 0) {
            // Habilidade de cura
            double cura = Math.abs(habilidadeInimigo.getDano());
            inimigo.setHp(inimigo.getHp() + cura);
            System.out.printf("%s recuperou %.0f de HP!%n", inimigo.getNome(), cura);
        } else {
            System.out.printf("%s usou uma habilidade defensiva!%n", inimigo.getNome());
        }

        System.out.printf("%s%n", habilidadeInimigo.getDescricao());

        return jogador.getHp() <= 0;
    }

    private void finalizarBatalha() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("\n---------- BATALHA FINALIZADA! -----------");
        System.out.println("=".repeat(30));

        if (jogador.getHp() > 0 && inimigo.getHp() <= 0) {
            System.out.println("VITORIA!!! " + jogador.getNome() + " venceu a batalha!");
            System.out.println("Recompensas obtidas!");
        } else if (jogador.getHp() <= 0) {
            System.out.println("DERROTA! " + jogador.getNome() + " foi derrotado...");
        } else {
            System.out.println("Fuga bem-sucedida!");
        }

        System.out.println("\nEstatisticas finais:");
        System.out.printf("%s: HP=%.0f/%.0f%n", jogador.getNome(),
                Math.max(0, jogador.getHp()), jogador.getHp());
        System.out.printf("%s: HP=%.0f%n", inimigo.getNome(), Math.max(0, inimigo.getHp()));
        System.out.println("Turnos: " + turno);

        scanner.close();
    }

    // método para criar uma batalha simples
    public static Batalha criarBatalha() {
        // criar personagem
        Arma espada = new Espada("Espada Inicial", 0);
        espada.configurarAtributos();

        //vai usar o que eu defini em espada
        String nomeArma = espada.getNome();
        int categoriaArma = espada.getCategoria();

        Personagem heroi = new Personagem(
                "Rover",
                Raridade.A,
                Elemento.Electro,
                Funcao.MAIN_DPS,
                1000, 150, 100, 50,
                espada, 0, 2
        );

        System.out.println("Personagem esta usando um/uma " + nomeArma + " (Categoria: " + categoriaArma + ")");

        // criar inimigo
        List<Elemento> elementosInimigo = Arrays.asList(Elemento.Havoc, Elemento.Glacio);

        Inimigo boss = Inimigo.criarInimigoComHabilidadesAutomaticas(
                "Capitaneus",
                CustoInimigo.CUSTO3,
                elementosInimigo
        );

        System.out.println("Inimigo criado: " + boss.getNome());
        System.out.println("Elementos: " + boss.getElemento());
        System.out.println("Habilidades geradas:");
        for (Habilidade hab : boss.getHabilidade()) {
            System.out.println("- " + hab.getNome() + " (Dano: " + hab.getDano() + ")");
        }
        System.out.println("\n");

        return new Batalha(heroi, boss);
    }
}
