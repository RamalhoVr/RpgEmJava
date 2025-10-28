package rpg.personagem;

public class Inimigo extends Personagem {

    // Construtor padrão: inimigo humano genérico
    public Inimigo() {
        super();
        this.origem = "humano";
        this.tipo = "humano";
    }

    public Inimigo(String nome, int pontosVida, int ataque, int defesa, int nivel) {
        super(nome, pontosVida, ataque, defesa, nivel);
        this.origem = "humano";
        this.tipo = "humano";
    }

    // Permite eventualmente criar inimigos de outro tipo, se desejado
    public Inimigo(String nome, int pontosVida, int ataque, int defesa, int nivel, String tipo) {
        super(nome, pontosVida, ataque, defesa, nivel);
        this.origem = "humano";
        this.tipo = tipo;
    }

    // Construtor de cópia
    public Inimigo(Inimigo outro) {
        super(outro);
    }

    @Override
    public int atacar() {
        return ataque; // Ataque base
    }
}
