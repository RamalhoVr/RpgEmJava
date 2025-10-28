package rpg.personagem.pokemon;

import rpg.personagem.Personagem;

public class PokemonPsiquico extends Personagem {

    public PokemonPsiquico() {
        this("PokemonPsíquico", 1);
    }

    public PokemonPsiquico(String nome, int nivel) {
        super(nome, 90 + nivel * 6, 22 + nivel * 4, 8 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "psíquico";
    }

    public PokemonPsiquico(PokemonPsiquico outro) {
        super(outro);
        this.origem = "pokemon";
        this.tipo = "psíquico";
    }

    @Override
    public int atacar() {
        return this.ataque + 8; 
    }
}