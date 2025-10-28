package rpg.personagem.pokemon;

import rpg.personagem.Personagem;

public class PokemonFogo extends Personagem {

    public PokemonFogo() {
        this("PokemonFogo", 1);
    }

    public PokemonFogo(String nome, int nivel) {
        super(nome, 90 + nivel * 7, 18 + nivel * 3, 8 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "fogo";
    }

    public PokemonFogo(PokemonFogo outro) {
        super(outro);
        this.origem = "pokemon";
        this.tipo = "fogo";
    }

    @Override
    public int atacar() {
        return this.ataque + 7;
    }
}