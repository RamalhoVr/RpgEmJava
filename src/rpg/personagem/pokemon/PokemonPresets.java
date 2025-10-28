package rpg.personagem.pokemon;

import rpg.poderes.Poder;
import rpg.poderes.PoderPresets;
import rpg.personagem.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PokemonPresets {

    public static List<Personagem> getPokemons() {
        List<Personagem> pokemons = new ArrayList<>();
        List<Poder> poderes = PoderPresets.getPoderes();

        // Pokémon de Água
        PokemonAgua squirtle = new PokemonAgua("Squirtle", 5);
        if (squirtle.podeUsarPoder(poderes.get(0))) squirtle.adicionarPoder(poderes.get(0)); // Jato d'Água
        if (squirtle.podeUsarPoder(poderes.get(1))) squirtle.adicionarPoder(poderes.get(1)); // Hidro Bomba
        if (squirtle.podeUsarPoder(poderes.get(2))) squirtle.adicionarPoder(poderes.get(2)); // Redemoinho

        PokemonAgua vaporeon = new PokemonAgua("Vaporeon", 15);
        if (vaporeon.podeUsarPoder(poderes.get(0))) vaporeon.adicionarPoder(poderes.get(0));
        if (vaporeon.podeUsarPoder(poderes.get(1))) vaporeon.adicionarPoder(poderes.get(1));
        if (vaporeon.podeUsarPoder(poderes.get(2))) vaporeon.adicionarPoder(poderes.get(2));

        PokemonAgua gyarados = new PokemonAgua("Gyarados", 20);
        if (gyarados.podeUsarPoder(poderes.get(0))) gyarados.adicionarPoder(poderes.get(0));
        if (gyarados.podeUsarPoder(poderes.get(1))) gyarados.adicionarPoder(poderes.get(1));
        if (gyarados.podeUsarPoder(poderes.get(2))) gyarados.adicionarPoder(poderes.get(2));

        // Pokémon de Fogo
        PokemonFogo charmander = new PokemonFogo("Charmander", 5);
        if (charmander.podeUsarPoder(poderes.get(3))) charmander.adicionarPoder(poderes.get(3));
        if (charmander.podeUsarPoder(poderes.get(4))) charmander.adicionarPoder(poderes.get(4));
        if (charmander.podeUsarPoder(poderes.get(5))) charmander.adicionarPoder(poderes.get(5));

        PokemonFogo flareon = new PokemonFogo("Flareon", 15);
        if (flareon.podeUsarPoder(poderes.get(3))) flareon.adicionarPoder(poderes.get(3));
        if (flareon.podeUsarPoder(poderes.get(4))) flareon.adicionarPoder(poderes.get(4));
        if (flareon.podeUsarPoder(poderes.get(5))) flareon.adicionarPoder(poderes.get(5));

        PokemonFogo arcanine = new PokemonFogo("Arcanine", 20);
        if (arcanine.podeUsarPoder(poderes.get(3))) arcanine.adicionarPoder(poderes.get(3));
        if (arcanine.podeUsarPoder(poderes.get(4))) arcanine.adicionarPoder(poderes.get(4));
        if (arcanine.podeUsarPoder(poderes.get(5))) arcanine.adicionarPoder(poderes.get(5));

        // Pokémon de Planta
        PokemonPlanta bulbasaur = new PokemonPlanta("Bulbasaur", 5);
        if (bulbasaur.podeUsarPoder(poderes.get(6))) bulbasaur.adicionarPoder(poderes.get(6));
        if (bulbasaur.podeUsarPoder(poderes.get(7))) bulbasaur.adicionarPoder(poderes.get(7));
        if (bulbasaur.podeUsarPoder(poderes.get(8))) bulbasaur.adicionarPoder(poderes.get(8));

        PokemonPlanta leafeon = new PokemonPlanta("Leafeon", 15);
        if (leafeon.podeUsarPoder(poderes.get(6))) leafeon.adicionarPoder(poderes.get(6));
        if (leafeon.podeUsarPoder(poderes.get(7))) leafeon.adicionarPoder(poderes.get(7));
        if (leafeon.podeUsarPoder(poderes.get(8))) leafeon.adicionarPoder(poderes.get(8));

        PokemonPlanta venusaur = new PokemonPlanta("Venusaur", 20);
        if (venusaur.podeUsarPoder(poderes.get(6))) venusaur.adicionarPoder(poderes.get(6));
        if (venusaur.podeUsarPoder(poderes.get(7))) venusaur.adicionarPoder(poderes.get(7));
        if (venusaur.podeUsarPoder(poderes.get(8))) venusaur.adicionarPoder(poderes.get(8));

        // Pokémon de Pedra
        PokemonPedra onix = new PokemonPedra("Onix", 5);
        if (onix.podeUsarPoder(poderes.get(9))) onix.adicionarPoder(poderes.get(9));
        if (onix.podeUsarPoder(poderes.get(10))) onix.adicionarPoder(poderes.get(10));
        if (onix.podeUsarPoder(poderes.get(11))) onix.adicionarPoder(poderes.get(11));

        PokemonPedra geodude = new PokemonPedra("Geodude", 15);
        if (geodude.podeUsarPoder(poderes.get(9))) geodude.adicionarPoder(poderes.get(9));
        if (geodude.podeUsarPoder(poderes.get(10))) geodude.adicionarPoder(poderes.get(10));
        if (geodude.podeUsarPoder(poderes.get(11))) geodude.adicionarPoder(poderes.get(11));

        PokemonPedra rhydon = new PokemonPedra("Rhydon", 20);
        if (rhydon.podeUsarPoder(poderes.get(9))) rhydon.adicionarPoder(poderes.get(9));
        if (rhydon.podeUsarPoder(poderes.get(10))) rhydon.adicionarPoder(poderes.get(10));
        if (rhydon.podeUsarPoder(poderes.get(11))) rhydon.adicionarPoder(poderes.get(11));

        // Pokémon de Elétrico
        PokemonEletrico pikachu = new PokemonEletrico("Pikachu", 5);
        if (pikachu.podeUsarPoder(poderes.get(12))) pikachu.adicionarPoder(poderes.get(12));
        if (pikachu.podeUsarPoder(poderes.get(13))) pikachu.adicionarPoder(poderes.get(13));
        if (pikachu.podeUsarPoder(poderes.get(14))) pikachu.adicionarPoder(poderes.get(14));

        PokemonEletrico jolteon = new PokemonEletrico("Jolteon", 15);
        if (jolteon.podeUsarPoder(poderes.get(12))) jolteon.adicionarPoder(poderes.get(12));
        if (jolteon.podeUsarPoder(poderes.get(13))) jolteon.adicionarPoder(poderes.get(13));
        if (jolteon.podeUsarPoder(poderes.get(14))) jolteon.adicionarPoder(poderes.get(14));

        PokemonEletrico electabuzz = new PokemonEletrico("Electabuzz", 20);
        if (electabuzz.podeUsarPoder(poderes.get(12))) electabuzz.adicionarPoder(poderes.get(12));
        if (electabuzz.podeUsarPoder(poderes.get(13))) electabuzz.adicionarPoder(poderes.get(13));
        if (electabuzz.podeUsarPoder(poderes.get(14))) electabuzz.adicionarPoder(poderes.get(14));

        // Pokémon de Psíquico
        PokemonPsiquico abra = new PokemonPsiquico("Abra", 5);
        if (abra.podeUsarPoder(poderes.get(15))) abra.adicionarPoder(poderes.get(15));
        if (abra.podeUsarPoder(poderes.get(16))) abra.adicionarPoder(poderes.get(16));
        if (abra.podeUsarPoder(poderes.get(17))) abra.adicionarPoder(poderes.get(17));

        PokemonPsiquico espeon = new PokemonPsiquico("Espeon", 15);
        if (espeon.podeUsarPoder(poderes.get(15))) espeon.adicionarPoder(poderes.get(15));
        if (espeon.podeUsarPoder(poderes.get(16))) espeon.adicionarPoder(poderes.get(16));
        if (espeon.podeUsarPoder(poderes.get(17))) espeon.adicionarPoder(poderes.get(17));

        PokemonPsiquico alakazam = new PokemonPsiquico("Alakazam", 20);
        if (alakazam.podeUsarPoder(poderes.get(15))) alakazam.adicionarPoder(poderes.get(15));
        if (alakazam.podeUsarPoder(poderes.get(16))) alakazam.adicionarPoder(poderes.get(16));
        if (alakazam.podeUsarPoder(poderes.get(17))) alakazam.adicionarPoder(poderes.get(17));

        // Adicionar todos os Pokémon à lista
        pokemons.add(squirtle);
        pokemons.add(vaporeon);
        pokemons.add(gyarados);
        pokemons.add(charmander);
        pokemons.add(flareon);
        pokemons.add(arcanine);
        pokemons.add(bulbasaur);
        pokemons.add(leafeon);
        pokemons.add(venusaur);
        pokemons.add(onix);
        pokemons.add(geodude);
        pokemons.add(rhydon);
        pokemons.add(pikachu);
        pokemons.add(jolteon);
        pokemons.add(electabuzz);
        pokemons.add(abra);
        pokemons.add(espeon);
        pokemons.add(alakazam);

        return pokemons;
    }
}