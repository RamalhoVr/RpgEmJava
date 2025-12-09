package rpg.personagem.pokemon;

import rpg.personagem.Personagem;
import rpg.poderes.PoderPresets;
import rpg.poderes.Poder;

public class PokemonAgua extends Personagem {

    public PokemonAgua() {
        this("PokemonÁgua", 1);
    }

    public PokemonAgua(String nome, int nivel) {
        super(nome, 100 + nivel * 8, 15 + nivel * 2, 10 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "água";
        
        // Adicionar poderes de água baseados no nível
        for (Poder p : PoderPresets.getPoderes()) {
            if (p.getId().startsWith("agua") && nivel >= p.getNivelMinimo()) {
                this.poderes.add(p);
            }
        }
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