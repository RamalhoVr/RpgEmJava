package rpg.personagem.pokemon;

public class PokemonEletrico extends Inimigo {

    public PokemonEletrico(String nome, int nivel) {
        super(nome, 95 + nivel * 7, 20 + nivel * 3, 9 + nivel, nivel);
    }

    @Override
    public int atacar() {
        return super.atacar() + 6; 
    }
}