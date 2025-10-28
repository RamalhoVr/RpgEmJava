package rpg.personagem.pokemon;

import rpg.personagem.Personagem;

public class PokemonPedra extends Personagem {

    public PokemonPedra() {
        this("PokemonPedra", 1);
    }

    public PokemonPedra(String nome, int nivel) {
        super(nome, 120 + nivel * 10, 12 + nivel * 2, 15 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "pedra";
    }

    public PokemonPedra(PokemonPedra outro) {
        super(outro);
        this.origem = "pokemon";
        this.tipo = "pedra";
    }

    @Override
    public int atacar() {
        return this.ataque + 3; 
    }
}