package rpg.personagem;

public class Inimigo extends Personagem {

    private final String tipo; 

    public Inimigo(String nome, int pontosVida, int ataque, int defesa, int nivel, String tipo) {
        super(nome, pontosVida, ataque, defesa, nivel);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public int atacar() {
        return ataque; // Ataque base
    }
}
