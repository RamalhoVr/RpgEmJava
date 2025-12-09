package rpg.personagem.pokemon;

import rpg.personagem.Personagem;
import rpg.poderes.PoderPresets;
import rpg.poderes.Poder;

public class PokemonPedra extends Personagem {

    public PokemonPedra() {
        this("PokemonPedra", 1);
    }

    public PokemonPedra(String nome, int nivel) {
        super(nome, 120 + nivel * 10, 12 + nivel * 2, 15 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "pedra";
        
        // Adicionar poderes de pedra baseados no nível
        for (Poder p : PoderPresets.getPoderes()) {
            if (p.getId().startsWith("pedra") && nivel >= p.getNivelMinimo()) {
                this.poderes.add(p);
            }
        }
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