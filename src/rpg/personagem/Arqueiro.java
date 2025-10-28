package rpg.personagem;

import java.util.Random;

public class Arqueiro extends Personagem {

    public Arqueiro() {
        this("Arqueiro", 1);
    }

    public Arqueiro(String nome, int nivel) {
        super(nome, 100 + nivel * 8, 20 + nivel * 3, 10 + nivel, nivel);
        this.origem = "humano";
        this.tipo = "humano";
    }

    @Override
    public int atacar() {
        return this.ataque;
    }

    @Override
    public void recuperarVida() {
        Random rand = new Random();
        int vidaRecuperada = 8;
        if (rand.nextDouble() < 0.3) {
            vidaRecuperada += 5;
        }
        this.pontosVida += vidaRecuperada;
        if (this.pontosVida > this.maxPontosVida) {
            this.pontosVida = this.maxPontosVida;
        }
        System.out.println(this.nome + " recuperou " + vidaRecuperada + " pontos de vida!");
    }

    // Construtor de cópia
    public Arqueiro(Arqueiro outro) {
        super(outro);
        this.origem = "humano";
        this.tipo = "humano";
    }
}