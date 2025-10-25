package rpg.personagem.pokemon;

import rpg.poderes.Poder;
import rpg.poderes.PoderPresets;

import java.util.ArrayList;
import java.util.List;

public class PokemonPresets {

    public static List<Inimigo> getPokemons() {
        List<Inimigo> pokemons = new ArrayList<>();
        List<Poder> poderes = PoderPresets.getPoderes();

        // Pokémon de Água
        PokemonAgua squirtle = new PokemonAgua("Squirtle", 5);
        if (squirtle.podeUsarPoder(poderes.get(0))) squirtle.getInventario().adicionarPoder(poderes.get(0)); // Jato d'Água
        if (squirtle.podeUsarPoder(poderes.get(1))) squirtle.getInventario().adicionarPoder(poderes.get(1)); // Hidro Bomba
        if (squirtle.podeUsarPoder(poderes.get(2))) squirtle.getInventario().adicionarPoder(poderes.get(2)); // Redemoinho

        PokemonAgua vaporeon = new PokemonAgua("Vaporeon", 15);
        if (vaporeon.podeUsarPoder(poderes.get(0))) vaporeon.getInventario().adicionarPoder(poderes.get(0)); // Jato d'Água
        if (vaporeon.podeUsarPoder(poderes.get(1))) vaporeon.getInventario().adicionarPoder(poderes.get(1)); // Hidro Bomba
        if (vaporeon.podeUsarPoder(poderes.get(2))) vaporeon.getInventario().adicionarPoder(poderes.get(2)); // Redemoinho

        PokemonAgua gyarados = new PokemonAgua("Gyarados", 20);
        if (gyarados.podeUsarPoder(poderes.get(0))) gyarados.getInventario().adicionarPoder(poderes.get(0)); // Jato d'Água
        if (gyarados.podeUsarPoder(poderes.get(1))) gyarados.getInventario().adicionarPoder(poderes.get(1)); // Hidro Bomba
        if (gyarados.podeUsarPoder(poderes.get(2))) gyarados.getInventario().adicionarPoder(poderes.get(2)); // Redemoinho

        // Pokémon de Fogo
        PokemonFogo charmander = new PokemonFogo("Charmander", 5);
        if (charmander.podeUsarPoder(poderes.get(3))) charmander.getInventario().adicionarPoder(poderes.get(3)); // Brasas
        if (charmander.podeUsarPoder(poderes.get(4))) charmander.getInventario().adicionarPoder(poderes.get(4)); // Lança-Chamas
        if (charmander.podeUsarPoder(poderes.get(5))) charmander.getInventario().adicionarPoder(poderes.get(5)); // Explosão de Fogo

        PokemonFogo flareon = new PokemonFogo("Flareon", 15);
        if (flareon.podeUsarPoder(poderes.get(3))) flareon.getInventario().adicionarPoder(poderes.get(3)); // Brasas
        if (flareon.podeUsarPoder(poderes.get(4))) flareon.getInventario().adicionarPoder(poderes.get(4)); // Lança-Chamas
        if (flareon.podeUsarPoder(poderes.get(5))) flareon.getInventario().adicionarPoder(poderes.get(5)); // Explosão de Fogo

        PokemonFogo arcanine = new PokemonFogo("Arcanine", 20);
        if (arcanine.podeUsarPoder(poderes.get(3))) arcanine.getInventario().adicionarPoder(poderes.get(3)); // Brasas
        if (arcanine.podeUsarPoder(poderes.get(4))) arcanine.getInventario().adicionarPoder(poderes.get(4)); // Lança-Chamas
        if (arcanine.podeUsarPoder(poderes.get(5))) arcanine.getInventario().adicionarPoder(poderes.get(5)); // Explosão de Fogo

        // Pokémon de Planta
        PokemonPlanta bulbasaur = new PokemonPlanta("Bulbasaur", 5);
        if (bulbasaur.podeUsarPoder(poderes.get(6))) bulbasaur.getInventario().adicionarPoder(poderes.get(6)); // Chicote de Cipó
        if (bulbasaur.podeUsarPoder(poderes.get(7))) bulbasaur.getInventario().adicionarPoder(poderes.get(7)); // Folha Navalha
        if (bulbasaur.podeUsarPoder(poderes.get(8))) bulbasaur.getInventario().adicionarPoder(poderes.get(8)); // Raio Solar

        PokemonPlanta leafeon = new PokemonPlanta("Leafeon", 15);
        if (leafeon.podeUsarPoder(poderes.get(6))) leafeon.getInventario().adicionarPoder(poderes.get(6)); // Chicote de Cipó
        if (leafeon.podeUsarPoder(poderes.get(7))) leafeon.getInventario().adicionarPoder(poderes.get(7)); // Folha Navalha
        if (leafeon.podeUsarPoder(poderes.get(8))) leafeon.getInventario().adicionarPoder(poderes.get(8)); // Raio Solar

        PokemonPlanta venusaur = new PokemonPlanta("Venusaur", 20);
        if (venusaur.podeUsarPoder(poderes.get(6))) venusaur.getInventario().adicionarPoder(poderes.get(6)); // Chicote de Cipó
        if (venusaur.podeUsarPoder(poderes.get(7))) venusaur.getInventario().adicionarPoder(poderes.get(7)); // Folha Navalha
        if (venusaur.podeUsarPoder(poderes.get(8))) venusaur.getInventario().adicionarPoder(poderes.get(8)); // Raio Solar

        // Pokémon de Pedra
        PokemonPedra onix = new PokemonPedra("Onix", 5);
        if (onix.podeUsarPoder(poderes.get(9))) onix.getInventario().adicionarPoder(poderes.get(9)); // Lançamento de Rocha
        if (onix.podeUsarPoder(poderes.get(10))) onix.getInventario().adicionarPoder(poderes.get(10)); // Deslizamento de Pedras
        if (onix.podeUsarPoder(poderes.get(11))) onix.getInventario().adicionarPoder(poderes.get(11)); // Terremoto

        PokemonPedra geodude = new PokemonPedra("Geodude", 15);
        if (geodude.podeUsarPoder(poderes.get(9))) geodude.getInventario().adicionarPoder(poderes.get(9)); // Lançamento de Rocha
        if (geodude.podeUsarPoder(poderes.get(10))) geodude.getInventario().adicionarPoder(poderes.get(10)); // Deslizamento de Pedras
        if (geodude.podeUsarPoder(poderes.get(11))) geodude.getInventario().adicionarPoder(poderes.get(11)); // Terremoto

        PokemonPedra rhydon = new PokemonPedra("Rhydon", 20);
        if (rhydon.podeUsarPoder(poderes.get(9))) rhydon.getInventario().adicionarPoder(poderes.get(9)); // Lançamento de Rocha
        if (rhydon.podeUsarPoder(poderes.get(10))) rhydon.getInventario().adicionarPoder(poderes.get(10)); // Deslizamento de Pedras
        if (rhydon.podeUsarPoder(poderes.get(11))) rhydon.getInventario().adicionarPoder(poderes.get(11)); // Terremoto

        // Pokémon de Elétrico
        PokemonEletrico pikachu = new PokemonEletrico("Pikachu", 5);
        if (pikachu.podeUsarPoder(poderes.get(12))) pikachu.getInventario().adicionarPoder(poderes.get(12)); // Choque do Trovão
        if (pikachu.podeUsarPoder(poderes.get(13))) pikachu.getInventario().adicionarPoder(poderes.get(13)); // Trovão
        if (pikachu.podeUsarPoder(poderes.get(14))) pikachu.getInventario().adicionarPoder(poderes.get(14)); // Relâmpago

        PokemonEletrico jolteon = new PokemonEletrico("Jolteon", 15);
        if (jolteon.podeUsarPoder(poderes.get(12))) jolteon.getInventario().adicionarPoder(poderes.get(12)); // Choque do Trovão
        if (jolteon.podeUsarPoder(poderes.get(13))) jolteon.getInventario().adicionarPoder(poderes.get(13)); // Trovão
        if (jolteon.podeUsarPoder(poderes.get(14))) jolteon.getInventario().adicionarPoder(poderes.get(14)); // Relâmpago

        PokemonEletrico electabuzz = new PokemonEletrico("Electabuzz", 20);
        if (electabuzz.podeUsarPoder(poderes.get(12))) electabuzz.getInventario().adicionarPoder(poderes.get(12)); // Choque do Trovão
        if (electabuzz.podeUsarPoder(poderes.get(13))) electabuzz.getInventario().adicionarPoder(poderes.get(13)); // Trovão
        if (electabuzz.podeUsarPoder(poderes.get(14))) electabuzz.getInventario().adicionarPoder(poderes.get(14)); // Relâmpago

        // Pokémon de Psíquico
        PokemonPsiquico abra = new PokemonPsiquico("Abra", 5);
        if (abra.podeUsarPoder(poderes.get(15))) abra.getInventario().adicionarPoder(poderes.get(15)); // Confusão
        if (abra.podeUsarPoder(poderes.get(16))) abra.getInventario().adicionarPoder(poderes.get(16)); // Poder Psíquico
        if (abra.podeUsarPoder(poderes.get(17))) abra.getInventario().adicionarPoder(poderes.get(17)); // Campo Psíquico

        PokemonPsiquico espeon = new PokemonPsiquico("Espeon", 15);
        if (espeon.podeUsarPoder(poderes.get(15))) espeon.getInventario().adicionarPoder(poderes.get(15)); // Confusão
        if (espeon.podeUsarPoder(poderes.get(16))) espeon.getInventario().adicionarPoder(poderes.get(16)); // Poder Psíquico
        if (espeon.podeUsarPoder(poderes.get(17))) espeon.getInventario().adicionarPoder(poderes.get(17)); // Campo Psíquico

        PokemonPsiquico alakazam = new PokemonPsiquico("Alakazam", 20);
        if (alakazam.podeUsarPoder(poderes.get(15))) alakazam.getInventario().adicionarPoder(poderes.get(15)); // Confusão
        if (alakazam.podeUsarPoder(poderes.get(16))) alakazam.getInventario().adicionarPoder(poderes.get(16)); // Poder Psíquico
        if (alakazam.podeUsarPoder(poderes.get(17))) alakazam.getInventario().adicionarPoder(poderes.get(17)); // Campo Psíquico

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