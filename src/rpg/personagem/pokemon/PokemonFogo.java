package rpg.personagem.pokemon;

import rpg.personagem.Personagem;
import rpg.poderes.PoderPresets;
import rpg.poderes.Poder;

public class PokemonFogo extends Personagem {

    public PokemonFogo() {
        this("PokemonFogo", 1);
    }

    public PokemonFogo(String nome, int nivel) {
        super(nome, 100 + nivel * 8, 17 + nivel * 2, 8 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "fogo";
        
        // Adicionar poderes de fogo baseados no nível
        for (Poder p : PoderPresets.getPoderes()) {
            if (p.getId().startsWith("fogo") && nivel >= p.getNivelMinimo()) {
                this.poderes.add(p);
            }
        }
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