package rpg;

import rpg.personagem.*;
import rpg.personagem.pokemon.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║                                           ║");
        System.out.println("║        RPG POKÉMON - RESGATE DO AMIGO     ║");
        System.out.println("║                                           ║");
        System.out.println("╚═══════════════════════════════════════════╝");
        
        // Escolher personagem jogável
        Personagem jogador = escolherPersonagem(scanner);
        
        if (jogador == null) {
            System.out.println("\nJogo encerrado.");
            scanner.close();
            return;
        }
        
        // Escolher amigo aleatório (sempre um Pokémon)
        Personagem amigo = escolherAmigoAleatorio(random);
        
        System.out.println("\n----------------------------------------------");
        System.out.println("Seu amigo será: " + amigo.getNome() + " (" + amigo.getTipo() + ")");
        System.out.println("----------------------------------------------");
        
        // Iniciar o jogo
        Jogo jogo = new Jogo(jogador, amigo);
        jogo.iniciar();
        
        scanner.close();
    }
    
    private static Personagem escolherPersonagem(Scanner scanner) {
        System.out.println("\n==============================================");
        System.out.println("        ESCOLHA SEU PERSONAGEM");
        System.out.println("==============================================");
        System.out.println("1 - Humano (Guerreiro)");
        System.out.println("2 - Humano (Arqueiro)");
        System.out.println("3 - Pokémon");
        System.out.println("0 - Sair");
        System.out.print("\nEscolha: ");
        
        String escolha = scanner.nextLine().trim();
        
        switch (escolha) {
            case "1":
                return criarGuerreiro(scanner);
            case "2":
                return criarArqueiro(scanner);
            case "3":
                return escolherPokemon(scanner);
            case "0":
                return null;
            default:
                System.out.println("\nOpção inválida! Tente novamente.");
                return escolherPersonagem(scanner);
        }
    }
    
    private static Guerreiro criarGuerreiro(Scanner scanner) {
        System.out.print("\nDigite o nome do seu Guerreiro: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) nome = "Guerreiro";
        
        System.out.print("Escolha o nível inicial (1-10): ");
        int nivel = lerNumero(scanner, 1, 10);
        
        return new Guerreiro(nome, nivel);
    }
    
    private static Arqueiro criarArqueiro(Scanner scanner) {
        System.out.print("\nDigite o nome do seu Arqueiro: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) nome = "Arqueiro";
        
        System.out.print("Escolha o nível inicial (1-10): ");
        int nivel = lerNumero(scanner, 1, 10);
        
        return new Arqueiro(nome, nivel);
    }
    
    private static Personagem escolherPokemon(Scanner scanner) {
        System.out.println("\n==============================================");
        System.out.println("        ESCOLHA SEU POKÉMON");
        System.out.println("==============================================");
        
        List<Personagem> pokemons = PokemonPresets.getPokemons();
        
        // Agrupar por tipo para facilitar a visualização
        System.out.println("\n--- TIPO ÁGUA ---");
        System.out.println("1 - Squirtle (Nível 5)");
        System.out.println("2 - Vaporeon (Nível 15)");
        System.out.println("3 - Gyarados (Nível 20)");
        
        System.out.println("\n--- TIPO FOGO ---");
        System.out.println("4 - Charmander (Nível 5)");
        System.out.println("5 - Flareon (Nível 15)");
        System.out.println("6 - Arcanine (Nível 20)");
        
        System.out.println("\n--- TIPO PLANTA ---");
        System.out.println("7 - Bulbasaur (Nível 5)");
        System.out.println("8 - Leafeon (Nível 15)");
        System.out.println("9 - Venusaur (Nível 20)");
        
        System.out.println("\n--- TIPO PEDRA ---");
        System.out.println("10 - Onix (Nível 5)");
        System.out.println("11 - Geodude (Nível 15)");
        System.out.println("12 - Rhydon (Nível 20)");
        
        System.out.println("\n--- TIPO ELÉTRICO ---");
        System.out.println("13 - Pikachu (Nível 5)");
        System.out.println("14 - Jolteon (Nível 15)");
        System.out.println("15 - Electabuzz (Nível 20)");
        
        System.out.println("\n--- TIPO PSÍQUICO ---");
        System.out.println("16 - Abra (Nível 5)");
        System.out.println("17 - Espeon (Nível 15)");
        System.out.println("18 - Alakazam (Nível 20)");
        
        System.out.print("\nEscolha (1-18): ");
        int escolha = lerNumero(scanner, 1, 18);
        
        Personagem pokemonEscolhido = pokemons.get(escolha - 1);
        
        System.out.print("\nDeseja personalizar o nome? (s/n): ");
        String resp = scanner.nextLine().trim().toLowerCase();
        
        if (resp.equals("s") || resp.equals("sim")) {
            System.out.print("Digite o novo nome: ");
            String novoNome = scanner.nextLine().trim();
            if (!novoNome.isEmpty()) {
                // Criar uma cópia com o novo nome
                pokemonEscolhido = clonarPokemonComNovoNome(pokemonEscolhido, novoNome);
            }
        }
        
        return pokemonEscolhido;
    }
    
    private static Personagem clonarPokemonComNovoNome(Personagem original, String novoNome) {
        // Determinar o tipo e criar uma nova instância
        String tipo = original.getTipo();
        int nivel = original.getNivel();
        
        switch (tipo) {
            case "água":
                return new PokemonAgua(novoNome, nivel);
            case "fogo":
                return new PokemonFogo(novoNome, nivel);
            case "planta":
                return new PokemonPlanta(novoNome, nivel);
            case "pedra":
                return new PokemonPedra(novoNome, nivel);
            case "elétrico":
                return new PokemonEletrico(novoNome, nivel);
            case "psíquico":
                return new PokemonPsiquico(novoNome, nivel);
            default:
                return original;
        }
    }
    
    private static Personagem escolherAmigoAleatorio(Random random) {
        List<Personagem> pokemons = PokemonPresets.getPokemons();
        // Escolher um pokémon aleatório de nível baixo (primeiros 6 são nível 5)
        int indice = random.nextInt(6) * 3; // 0, 3, 6, 9, 12, 15 (um de cada tipo, nível 5)
        return pokemons.get(indice);
    }
    
    private static int lerNumero(Scanner scanner, int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int numero = Integer.parseInt(input);
                if (numero >= min && numero <= max) {
                    return numero;
                } else {
                    System.out.print("Número fora do intervalo! Digite um número entre " + min + " e " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida! Digite um número entre " + min + " e " + max + ": ");
            }
        }
    }
}
