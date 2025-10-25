package rpg.personagem.pokemon;

public class PokemonAgua extends Inimigo {

    public PokemonAgua(String nome, int nivel) {
        super(nome, 100 + nivel * 8, 15 + nivel * 2, 10 + nivel, nivel);
    }

    @Override
    public int atacar() {
        return super.atacar() + 5; 
    }
}