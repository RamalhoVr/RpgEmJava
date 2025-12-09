package rpg.personagem.pokemon;

import rpg.personagem.Personagem;
import rpg.poderes.PoderPresets;
import rpg.poderes.Poder;

public class PokemonEletrico extends Personagem {

    public PokemonEletrico() {
        this("PokemonElétrico", 1);
    }

    public PokemonEletrico(String nome, int nivel) {
        super(nome, 95 + nivel * 7, 16 + nivel * 2, 9 + nivel, nivel);
        this.origem = "pokemon";
        this.tipo = "elétrico";
        
        // Adicionar poderes elétricos baseados no nível
        for (Poder p : PoderPresets.getPoderes()) {
            if (p.getId().startsWith("eletrico") && nivel >= p.getNivelMinimo()) {
                this.poderes.add(p);
            }
        }
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