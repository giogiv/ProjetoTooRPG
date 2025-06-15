package Jogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Inimigo {

    protected String nome;
    protected CustoInimigo custo;
    protected List<Elemento> elemento;
    protected List<Habilidade> habilidade;
    protected double hp;
    protected double atk;
    protected double def;

    public Inimigo(String nome, CustoInimigo custo, List<Elemento> elemento,
            List<Habilidade> habilidades, double hp, double atk, double def) {
        //um inimigo pode ter até 3 elementos
        if (elemento == null || elemento.size() > 3) {
            System.out.println("O inimigo so pode ter ate 3 elementos!");
        } else if (habilidades.size() != 3) {
            System.out.println("O inimigo tem que ter 3 habilidades!");
        }

        this.nome = nome;
        this.custo = custo;
        this.elemento = elemento;
        this.habilidade = habilidades;
        configurarAtributosPorCusto();
    }

    //método para gerar habilidades automaticas
    public static Inimigo criarInimigoComHabilidadesAutomaticas(String nome, CustoInimigo custo, List<Elemento> elementos) {
        Inimigo inimigo = new Inimigo();
        inimigo.nome = nome;
        inimigo.custo = custo;
        inimigo.elemento = elementos;

        // gera habilidades automaticamente e de acordo com o custo
        inimigo.habilidade = inimigo.gerarHabilidadesPorElementos(elementos);
        inimigo.configurarAtributosPorCusto();

        return inimigo;
    }

    private Inimigo() {
    }

    private List<Habilidade> gerarHabilidadesPorElementos(List<Elemento> elementos) {
        List<Habilidade> habilidades = new ArrayList<>();

        for (Elemento el : elementos) {
            habilidades.add(gerarHabilidadePorElemento(el));
        }

        if (elementos.size() == 1) {
            habilidades.add(new Habilidade(98, "Bloqueio Sombrio", 0, "Defende parte do dano", 0, "Defesa"));
            habilidades.add(new Habilidade(99, "Regeneração Fraca", -20, "Recupera um pouco de vida", 0, "Cura"));
        } else if (elementos.size() == 2) {
            habilidades.add(new Habilidade(98, "Bloqueio Sombrio", 0, "Defende parte do dano", 0, "Defesa"));
        }

        return habilidades;
    }

    private Habilidade gerarHabilidadePorElemento(Elemento elemento) {
        return switch (elemento) {
            case Aero ->
                new Habilidade(1, "Redemoinho Cortante", 30, "Causa dano continuo com vento", 0, "Dano por turno");
            case Spectro ->
                new Habilidade(2, "Penumbra Crescente", 28, "Aumenta seu ataque", 0, "Buff");
            case Electro ->
                new Habilidade(3, "Trovoada Caotica", 35, "Explosao eletrica forte", 0, "Explosao");
            case Fusion ->
                new Habilidade(4, "Queima Vital", 26, "Rouba vida com fogo", 0, "Vampirismo");
            case Glacio ->
                new Habilidade(5, "Prisao de Gelo", 30, "Paralisa o inimigo", 0, "Congelamento");
            case Havoc ->
                new Habilidade(6, "Ruina", 36, "Dano massivo final", 0, "Destruicao");
        };
    }

    private void configurarAtributosPorCusto() {
        switch (custo) {
            case CUSTO1 -> {
                this.hp = 1000;
                this.atk = 1.0;
                this.def = 0.8;
            }
            case CUSTO3 -> {
                this.hp = 1500;
                this.atk = 1.2;
                this.def = 1.0;
            }
            case CUSTO4 -> {
                this.hp = 3000;
                this.atk = 2.4;
                this.def = 2.0;
            }
        }
    }

    public Habilidade usarHabilidade() {
        Random random = new Random();
        int index = random.nextInt(habilidade.size());
        return habilidade.get(index);
    }

    public double calcularDano(Habilidade habilidadeUsada) {
        return habilidadeUsada.getDano() * atk;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CustoInimigo getCusto() {
        return custo;
    }

    public void setCusto(CustoInimigo custo) {
        this.custo = custo;
    }

    public List<Elemento> getElemento() {
        return elemento;
    }

    public void setElemento(List<Elemento> elemento) {
        this.elemento = elemento;
    }

    public List<Habilidade> getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(List<Habilidade> habilidade) {
        this.habilidade = habilidade;
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
}
