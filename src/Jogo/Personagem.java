package Jogo;

import java.util.ArrayList;
import java.util.List;

public class Personagem {

    private String nome;
    private Raridade raridade;
    private Elemento elemento;
    private Funcao funcao;
    private Arma arma;
    private double hp;
    private double atk;
    private double def;
    private double energia;
    private List<Habilidade> habilidade;

    public Personagem(String nome, Raridade raridade, Elemento elemento,
            Funcao funcao, double hp, double atk, double def,
            double energia, Arma arma, int habilidade1Index, int habilidade2Index) {

        this.nome = nome;
        this.raridade = raridade;
        this.elemento = elemento;
        this.funcao = funcao;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.energia = energia;
        this.arma = arma;

        // monta as 4 habilidades automaticamente
        this.habilidade = montarHabilidades(habilidade1Index, habilidade2Index);
    }
    
    // ataque básico pro personagem
    public Habilidade ataqueBasico() {
        return new Habilidade(0, "Ataque Basico", 15, "Um golpe simples com sua arma.", 0, "Fisico");
    }

    private List<Habilidade> montarHabilidades(int i1, int i2) {
        List<Habilidade> lista = new ArrayList<>();

        lista.add(ataqueBasico());

        // recupera lista de habilidades do elemento
        List<Habilidade> habilidadesElemento = getHabilidadesElemento(elemento);

        // adiciona as 2 escolhidas
        if (i1 < 0 || i1 > 3 || i2 < 0 || i2 > 3 || i1 == i2) {
            System.out.println("Voce deve escolher dois indices validos e diferentes entre 0 e 3.");
        }
        lista.add(habilidadesElemento.get(i1));
        lista.add(habilidadesElemento.get(i2));

        // adiciona ultimate baseado no elemento
        lista.add(getUltimate(elemento));

        return lista;
    }

    private List<Habilidade> getHabilidadesElemento(Elemento e) {
        switch (e) {
            case Aero -> {
                return List.of(aero1(), aero2(), aero3(), aero4());
            }
            case Electro -> {
                return List.of(electro1(), electro2(), electro3(), electro4());
            }
            case Havoc -> {
                return List.of(havoc1(), havoc2(), havoc3(), havoc4());
            }
            case Fusion -> {
                return List.of(fusion1(), fusion2(), fusion3(), fusion4());
            }
            case Glacio -> {
                return List.of(glacio1(), glacio2(), glacio3(), glacio4());
            }
            case Spectro -> {
                return List.of(electro1(), electro2(), electro3(), electro4());
            }
            default ->
            {
                System.out.println("Elemento nao suportado.");
                return List.of();
            }
        }
    }

    private Habilidade getUltimate(Elemento e) {
        switch (e) {
            case Aero:
                return aeroUltimate();
            case Electro:
                return electroUltimate();
            // Adicione os outros elementos aqui
            default:
                throw new IllegalArgumentException("Elemento sem ultimate.");
        }
    }

    public void usarHabilidade(int index) {
        if (index < 0 || index >= habilidade.size()) {
            System.out.println(nome + " tentou usar uma habilidade invalida!");
            return;
        }

        Habilidade habilidade = this.habilidade.get(index);
        if (energia >= habilidade.getCustoEnergia()) {
            energia -= habilidade.getCustoEnergia();
            System.out.println(nome + " usou " + habilidade.getNome()
                    + " causando " + habilidade.getDano()
                    + " de dano no inimigo!");
            System.out.println("Efeito " + habilidade.getNome() + " " + habilidade.getDescricao());
        } else {
            System.out.println("[" + getEnergia() + "]: " + nome + " nao tem energia suficiente para usar " + habilidade.getNome() + ".");
        }
    }

    public void trocarArma(Arma novaArma) {
        System.out.println(nome + " trocou sua arma de "
                + (arma != null ? arma.getClass().getSimpleName() : "nenhuma")
                + " para " + novaArma.getClass().getSimpleName() + "!");

        this.arma = novaArma;
    }

    public void StatusAtual() {
        System.out.println("<" + raridade + "> " + nome + " [" + elemento + "]");
        System.out.println("Funcao: " + funcao);
        System.out.println("HP: " + hp + " | ATK: " + atk + " | DEF: " + def + " | Energia: " + energia);
        System.out.println("Arma: " + (arma != null ? arma.getClass().getSimpleName() : "Nenhuma"));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Raridade getRaridade() {
        return raridade;
    }

    public void setRaridade(Raridade raridade) {
        this.raridade = raridade;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getAtk() {
        return atk;
    }

    public void setAtk(double atk) {
        this.atk = atk;
    }

    public double getDef() {
        return def;
    }

    public void setDef(double def) {
        this.def = def;
    }

    public double getEnergia() {
        return energia;
    }

    public void setEnergia(double energia) {
        this.energia = energia;
    }

    public List<Habilidade> getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(List<Habilidade> habilidade) {
        this.habilidade = habilidade;
    }
    
    // Aero
    public Habilidade aero1() {
        return new Habilidade(1, "Lamina de Vento", 20, "Corta com uma rajada de vento", 10, "Dano");
    }

    public Habilidade aero2() {
        return new Habilidade(2, "Empurrao Aereo", 0, "Empurra o inimigo para longe, ganhando espaco", 12, "Controle");
    }

    public Habilidade aero3() {
        return new Habilidade(3, "Dança das Correntes", 18, "Evita o proximo ataque recebido", 15, "Defensiva");
    }

    public Habilidade aero4() {
        return new Habilidade(4, "Folego Refrescante", 0, "Recupera um pouco de energia", 10, "Cura");
    }

    public Habilidade aeroUltimate() {
        return new Habilidade(5, "Tornado Celeste", 45, "Invoca um tornado que atinge todos os inimigos", 30, "Dano em Area");
    }

    // Spectro
    public Habilidade spectro1() {
        return new Habilidade(6, "Lamento Sombrio", 22, "Reduz o ataque do inimigo", 12, "Debuff");
    }

    public Habilidade spectro2() {
        return new Habilidade(7, "Manto das Sombras", 0, "Fica intangivel e ignora o proximo ataque", 15, "Defensiva");
    }

    public Habilidade spectro3() {
        return new Habilidade(8, "Sussurros Vivos", 20, "Causa dano mental ao inimigo", 10, "Dano");
    }

    public Habilidade spectro4() {
        return new Habilidade(9, "Maldicao Lenta", 0, "Diminui a velocidade do inimigo", 8, "Controle");
    }

    public Habilidade spectroUltimate() {
        return new Habilidade(10, "Eclipse Espectral", 40, "Causa grande dano e silencia inimigos", 30, "Silencio em Area");
    }

    // Electro
    public Habilidade electro1() {
        return new Habilidade(11, "Raio Estatico", 25, "Causa dano eletrico direto", 12, "Dano");
    }

    public Habilidade electro2() {
        return new Habilidade(12, "Campo Eletromagnetico", 0, "Aumenta sua defesa temporariamente", 10, "Buff");
    }

    public Habilidade electro3() {
        return new Habilidade(13, "Pulso de Choque", 18, "Empurra e paralisa inimigo", 14, "Controle");
    }

    public Habilidade electro4() {
        return new Habilidade(14, "Condutor Natural", 0, "Aumenta sua energia em 20", 0, "Recuperacao");
    }

    public Habilidade electroUltimate() {
        return new Habilidade(15, "Tempestade de Plasma", 50, "Lanca uma onda de eletricidade em todos os inimigos", 35, "Dano em Area");
    }

    // Fusion
    public Habilidade fusion1() {
        return new Habilidade(16, "Chamas Rasantes", 24, "Ataca com rajadas de fogo", 12, "Dano");
    }

    public Habilidade fusion2() {
        return new Habilidade(17, "Explosao Interna", 28, "Alto dano, mas machuca o usuario tambem", 15, "Risco/Recompensa");
    }

    public Habilidade fusion3() {
        return new Habilidade(18, "Furia Ardente", 0, "Aumenta o ataque por 3 turnos", 10, "Buff");
    }

    public Habilidade fusion4() {
        return new Habilidade(19, "Fagulha Restauradora", 0, "Cura moderadamente a si mesmo", 15, "Cura");
    }

    public Habilidade fusionUltimate() {
        return new Habilidade(20, "Inferno Escaldante", 60, "Explode tudo ao redor com fogo incontrolavel", 40, "Explosao em Area");
    }

    // Glacio
    public Habilidade glacio1() {
        return new Habilidade(21, "Estilhaco de Gelo", 20, "Lança gelo cortante", 10, "Dano");
    }

    public Habilidade glacio2() {
        return new Habilidade(22, "Barreira Congelante", 0, "Cria um escudo de gelo que reduz dano recebido", 12, "Defensiva");
    }

    public Habilidade glacio3() {
        return new Habilidade(23, "Sopro Invernal", 0, "Diminui a velocidade dos inimigos", 10, "Controle");
    }

    public Habilidade glacio4() {
        return new Habilidade(24, "Gelo Curativo", 0, "Restaura vida com gelo medicinal", 15, "Cura");
    }

    public Habilidade glacioUltimate() {
        return new Habilidade(25, "Tempestade Glacial", 45, "Paralisa e causa dano em area", 35, "Paralisia em Area");
    }

    // Havoc
    public Habilidade havoc1() {
        return new Habilidade(26, "Destruicao Espiral", 30, "Ataque poderoso, dificil de evitar", 15, "Dano");
    }

    public Habilidade havoc2() {
        return new Habilidade(27, "Sangue da Furia", 0, "Aumenta ataque e reduz defesa", 10, "Buff de Risco");
    }

    public Habilidade havoc3() {
        return new Habilidade(28, "Dominio do Caos", 0, "Confunde o inimigo, tornando-o imprevisivel", 12, "Debuff");
    }

    public Habilidade havoc4() {
        return new Habilidade(29, "Rugido Sombrio", 0, "Assusta inimigos, reduzindo ataque", 10, "Controle");
    }

    public Habilidade havocUltimate() {
        return new Habilidade(30, "Ruína Final", 70, "Um ataque devastador, consome quase toda a energia", 50, "Massacre");
    }

}
