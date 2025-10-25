package rpg.personagem.pokemon;

public class PokemonPsiquico extends Inimigo {

    public PokemonPsiquico(String nome, int nivel) {
        super(nome, 90 + nivel * 6, 22 + nivel * 4, 8 + nivel, nivel);
    }

    @Override
    public int atacar() {
        return super.atacar() + 8; 
    }
}