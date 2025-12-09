package rpg.personagem.pokemon;

import rpg.personagem.Personagem;
import rpg.poderes.PoderPresets;
import rpg.poderes.Poder;

public class PokemonPsiquico extends Personagem {

    public PokemonPsiquico() {
        this("PokemonPsíquico", 1);
    }

    public PokemonPsiquico(String nome, int nivel) {
        super(nome, 90 + nivel * 7, 18 + nivel * 2, 8 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "psíquico";
        
        // Adicionar poderes psíquicos baseados no nível
        for (Poder p : PoderPresets.getPoderes()) {
            if (p.getId().startsWith("psiquico") && nivel >= p.getNivelMinimo()) {
                this.poderes.add(p);
            }
        }
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