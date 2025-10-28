package rpg.personagem.pokemon;

import rpg.personagem.Personagem;

public class PokemonPlanta extends Personagem {

    public PokemonPlanta() {
        this("PokemonPlanta", 1);
    }

    public PokemonPlanta(String nome, int nivel) {
        super(nome, 110 + nivel * 9, 14 + nivel * 2, 12 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "planta";
    }

    public PokemonPlanta(PokemonPlanta outro) {
        super(outro);
        this.origem = "pokemon";
        this.tipo = "planta";
    }

    @Override
    public int atacar() {
        return this.ataque + 4;
    }
}