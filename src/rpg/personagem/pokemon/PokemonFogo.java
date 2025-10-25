package rpg.personagem.pokemon;

public class PokemonFogo extends Inimigo {

    public PokemonFogo(String nome, int nivel) {
        super(nome, 90 + nivel * 7, 18 + nivel * 3, 8 + nivel, nivel);
    }

    @Override
    public int atacar() {
        return super.atacar() + 7;
    }
}