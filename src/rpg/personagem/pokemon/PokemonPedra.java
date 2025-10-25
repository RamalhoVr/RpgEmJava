package rpg.personagem.pokemon;

public class PokemonPedra extends Inimigo {

    public PokemonPedra(String nome, int nivel) {
        super(nome, 120 + nivel * 10, 12 + nivel * 2, 15 + nivel, nivel);
    }

    @Override
    public int atacar() {
        return super.atacar() + 3; 
    }
}