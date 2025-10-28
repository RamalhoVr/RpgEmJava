package rpg.personagem;

public class Guerreiro extends Personagem {

    public Guerreiro() {
        this("Guerreiro", 1);
    }

    public Guerreiro(String nome, int nivel) {
        super(nome, 120 + nivel * 10, 18 + nivel * 2, 12 + nivel, nivel);
        this.origem = "humano";
        this.tipo = "humano";
    }

    @Override
    public int atacar() {
        return this.ataque;
    }

    @Override
    public void recuperarVida() {
        int vidaRecuperada = 5;
        this.pontosVida += vidaRecuperada;
        if (this.pontosVida > this.maxPontosVida) {
            this.pontosVida = this.maxPontosVida; 
        }
        System.out.println(this.nome + " recuperou " + vidaRecuperada + " pontos de vida!");
    }

    // Construtor de cópia
    public Guerreiro(Guerreiro outro) {
        super(outro);
        this.origem = "humano";
        this.tipo = "humano";
    }
}