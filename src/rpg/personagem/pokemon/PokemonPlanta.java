package rpg.personagem.pokemon;

import rpg.personagem.Personagem;
import rpg.poderes.PoderPresets;
import rpg.poderes.Poder;

public class PokemonPlanta extends Personagem {

    public PokemonPlanta() {
        this("PokemonPlanta", 1);
    }

    public PokemonPlanta(String nome, int nivel) {
        super(nome, 110 + nivel * 8, 14 + nivel * 2, 12 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "planta";
        
        // Adicionar poderes de planta baseados no nível
        for (Poder p : PoderPresets.getPoderes()) {
            if (p.getId().startsWith("planta") && nivel >= p.getNivelMinimo()) {
                this.poderes.add(p);
            }
        }
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