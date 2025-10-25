package rpg.personagem.pokemon;

public class PokemonPlanta extends Inimigo {

    public PokemonPlanta(String nome, int nivel) {
        super(nome, 110 + nivel * 9, 14 + nivel * 2, 12 + nivel, nivel);
    }

    @Override
    public int atacar() {
        return super.atacar() + 4;
    }
}