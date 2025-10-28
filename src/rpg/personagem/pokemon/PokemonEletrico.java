package rpg.personagem.pokemon;

import rpg.personagem.Personagem;

public class PokemonEletrico extends Personagem {

    public PokemonEletrico() {
        this("PokemonElétrico", 1);
    }

    public PokemonEletrico(String nome, int nivel) {
        super(nome, 95 + nivel * 7, 20 + nivel * 3, 9 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "elétrico";
    }

    public PokemonEletrico(PokemonEletrico outro) {
        super(outro);
        this.origem = "pokemon";
        this.tipo = "elétrico";
    }

    @Override
    public int atacar() {
        return this.ataque + 6; 
    }
}