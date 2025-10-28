package rpg.personagem.pokemon;

import rpg.personagem.Personagem;

public class PokemonAgua extends Personagem {

    public PokemonAgua() {
        this("PokemonÁgua", 1);
    }

    public PokemonAgua(String nome, int nivel) {
        super(nome, 100 + nivel * 8, 15 + nivel * 2, 10 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "água";
    }

    // Construtor de cópia
    public PokemonAgua(PokemonAgua outro) {
        super(outro);
        this.origem = "pokemon";
        this.tipo = "água";
    }

    @Override
    public int atacar() {
        return this.ataque + 5; 
    }
}