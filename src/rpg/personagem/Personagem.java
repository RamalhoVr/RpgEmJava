package rpg.personagem;

import rpg.inventario.Inventario;
import rpg.poderes.Poder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Personagem{

    protected String nome;
    protected int pontosVida;
    protected int maxPontosVida;
    protected String origem; // "humano" ou "pokemon"
    protected String tipo;   // ex.: "humano", "água", "fogo"...
    protected int ataque;
    protected int defesa;
    protected int nivel;
    protected int experiencia;
    protected int expParaProximoNivel;
    protected Inventario inventario;
    protected List<Poder> poderes;
    
    // Construtor padrão exigido pelo enunciado
    protected Personagem() {
        this("SemNome", 50, 5, 5, 1);
    }

    public Personagem(String nome, int pontosVida, int ataque, int defesa, int nivel) {
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.maxPontosVida = pontosVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.experiencia = 0;
        this.expParaProximoNivel = calcularExpNecessaria(nivel);
        this.inventario = new Inventario();
        this.poderes = new ArrayList<>();
        this.tipo = "humano"; // padrão
        this.origem = "humano";
    }

    // Construtor de cópia exigido pelo enunciado
    protected Personagem(Personagem outro) {
        this.nome = outro.nome;
        this.pontosVida = outro.pontosVida;
        this.maxPontosVida = outro.maxPontosVida;
        this.origem = outro.origem;
        this.tipo = outro.tipo;
        this.ataque = outro.ataque;
        this.defesa = outro.defesa;
        this.nivel = outro.nivel;
        this.experiencia = outro.experiencia;
        this.expParaProximoNivel = outro.expParaProximoNivel;
        this.inventario = outro.inventario.clone();
        this.poderes = new ArrayList<>(outro.poderes);
    }

    public String getNome() { return nome; }
    public String getOrigem(){ return origem; }
    public String getTipo(){ return tipo; }
    public int getPontosVida(){ return pontosVida; }
    public int getMaxPontosVida(){ return maxPontosVida; }
    public int getAtaque(){ return ataque; }
    public int getDefesa(){ return defesa; }
    public int getNivel(){ return nivel; }
    public int getExperiencia() { return experiencia; }
    public int getExpParaProximoNivel() { return expParaProximoNivel; }
    public Inventario getInventario() { return inventario; }
    public List<Poder> getPoderes() { return Collections.unmodifiableList(poderes); }

    public void receberDano(int dano){
        pontosVida -= dano;
        if (pontosVida < 0) pontosVida = 0;
    }

    public void curarVida(int cura){
        pontosVida += cura;
        if(pontosVida > maxPontosVida) pontosVida = maxPontosVida;
    }

    public abstract int atacar();

    public boolean podeUsarPoder(Poder poder) {
        // Regra simples: nível mínimo e afinidade por tipo pelo prefixo do id
        if (this.nivel < poder.getNivelMinimo()) return false;
        String id = poder.getId().toLowerCase();
        switch (this.tipo) {
            case "água": return id.startsWith("agua");
            case "fogo": return id.startsWith("fogo");
            case "planta": return id.startsWith("planta");
            case "pedra": return id.startsWith("pedra");
            case "elétrico": return id.startsWith("eletrico");
            case "psíquico": return id.startsWith("psiquico");
            case "humano": default: return false; // humanos não usam poderes elementais
        }
    }

    public void adicionarPoder(Poder poder) {
        if (podeUsarPoder(poder)) {
            this.poderes.add(poder);
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s (Nível %d) HP: %d/%d ATQ: %d DEF: %d Tipo: %s",
                nome, nivel, pontosVida, maxPontosVida, ataque, defesa, tipo);
    }

    public void recuperarVida() {
        this.pontosVida += 5;
        if (this.pontosVida > this.maxPontosVida) {
            this.pontosVida = this.maxPontosVida; 
        }
    }
    
    public boolean estaVivo() {
        return this.pontosVida > 0;
    }
    
    /**
     * Calcula a experiência necessária para o próximo nível
     */
    private int calcularExpNecessaria(int nivel) {
        return nivel * 50; // 50 XP para nível 2, 100 para nível 3, etc.
    }
    
    /**
     * Adiciona experiência e verifica se subiu de nível
     * Retorna true se subiu de nível
     */
    public boolean ganharExperiencia(int exp) {
        System.out.println("[DEBUG XP] Jogador " + this.nome + " ganhando " + exp + " XP");
        System.out.println("[DEBUG XP] XP Antes: " + this.experiencia + "/" + this.expParaProximoNivel);
        
        this.experiencia += exp;
        
        System.out.println("[DEBUG XP] XP Depois: " + this.experiencia + "/" + this.expParaProximoNivel);
        
        if (this.experiencia >= this.expParaProximoNivel) {
            System.out.println("[DEBUG XP] SUBIU DE NÍVEL!");
            subirNivel();
            return true;
        }
        return false;
    }
    
    /**
     * Sobe um nível e aumenta os atributos
     */
    private void subirNivel() {
        this.nivel++;
        this.experiencia -= this.expParaProximoNivel;
        this.expParaProximoNivel = calcularExpNecessaria(this.nivel);
        
        // Aumentar atributos
        int aumentoVida = 10;
        int aumentoAtaque = 2;
        int aumentoDefesa = 1;
        
        this.maxPontosVida += aumentoVida;
        this.pontosVida = this.maxPontosVida; // Cura completamente ao subir de nível
        this.ataque += aumentoAtaque;
        this.defesa += aumentoDefesa;
    }
}