package rpg;

import rpg.item.Item;
import rpg.personagem.Inimigo;
import rpg.personagem.Personagem;
import rpg.util.TipoUtil;

import java.util.Random;
import java.util.Scanner;

public class Jogo {
    private Personagem jogador;
    private Personagem amigo; // O amigo capturado pela Equipe Rocket
    private Scanner scanner;
    private Random random;
    private int pistasEncontradas;
    private static final int PISTAS_PARA_RESGATE = 5;

    public Jogo(Personagem jogador, Personagem amigo) {
        this.jogador = jogador;
        this.amigo = amigo;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.pistasEncontradas = 0;
    }

    public void iniciar() {
        System.out.println("\n==============================================");
        System.out.println("        BEM-VINDO AO RPG POKÉMON!");
        System.out.println("==============================================");
        System.out.println("\nVocê é: " + jogador.toString());
        System.out.println("\nSeu melhor amigo " + amigo.getNome() + " (" + amigo.getTipo() + ") foi capturado pela Equipe Rocket!");
        System.out.println("Você precisa encontrar pistas e derrotar inimigos para resgatá-lo!");
        System.out.println("\nPressione ENTER para começar sua jornada...");
        scanner.nextLine();

        loopPrincipal();
    }

    private void loopPrincipal() {
        while (jogador.estaVivo()) {
            System.out.println("\n==============================================");
            System.out.println("Pistas encontradas: " + pistasEncontradas + "/" + PISTAS_PARA_RESGATE);
            System.out.println("==============================================");
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Explorar");
            System.out.println("2 - Ver Status");
            System.out.println("3 - Usar Item do Inventário");
            System.out.println("4 - Sair do jogo");
            System.out.print("\nEscolha: ");

            String escolha = scanner.nextLine().trim();

            switch (escolha) {
                case "1":
                    explorar();
                    break;
                case "2":
                    verStatus();
                    break;
                case "3":
                    usarItem();
                    break;
                case "4":
                    System.out.println("\nVocê desistiu da jornada. Seu amigo continuará capturado...");
                    return;
                default:
                    System.out.println("\nOpção inválida!");
            }

            // Verifica se encontrou pistas suficientes para o resgate final
            if (pistasEncontradas >= PISTAS_PARA_RESGATE) {
                batalhaFinal();
                return;
            }
        }

        System.out.println("\n==============================================");
        System.out.println("        VOCÊ FOI DERROTADO!");
        System.out.println("==============================================");
        System.out.println("Seu amigo " + amigo.getNome() + " continuará prisioneiro...");
    }

    private void explorar() {
        System.out.println("\n----------------------------------------------");
        System.out.println("Você está explorando...");
        System.out.println("----------------------------------------------");

        int evento = random.nextInt(100);

        if (evento < 40) {
            // 40% - Encontro com membro da Equipe Rocket
            encontroComRocket();
        } else if (evento < 70) {
            // 30% - Pokémon selvagem
            encontroComPokemonSelvagem();
        } else if (evento < 85) {
            // 15% - Encontrou uma pista!
            encontrarPista();
        } else if (evento < 95) {
            // 10% - Encontrou um item!
            encontrarItem();
        } else {
            // 5% - Armadilha!
            armadilha();
        }
    }

    private void encontroComRocket() {
        System.out.println("\n⚠️  Um membro da Equipe Rocket apareceu!");
        
        int nivelInimigo = jogador.getNivel() + random.nextInt(3);
        Inimigo rocket = new Inimigo("Rocket " + random.nextInt(100), 
                                     80 + nivelInimigo * 8,
                                     12 + nivelInimigo * 2,
                                     8 + nivelInimigo,
                                     nivelInimigo);
        
        System.out.println(rocket.getNome() + " desafia você para a batalha!");
        batalhar(rocket, true);
    }

    private void encontroComPokemonSelvagem() {
        System.out.println("\n🐾 Um Pokémon selvagem apareceu!");
        
        // Criar um inimigo baseado em um tipo aleatório de Pokémon
        String[] tipos = {"água", "fogo", "planta", "pedra", "elétrico", "psíquico"};
        String tipoAleatorio = tipos[random.nextInt(tipos.length)];
        int nivelInimigo = Math.max(1, jogador.getNivel() + random.nextInt(3) - 1);
        
        String nomePokemon = gerarNomePokemonPorTipo(tipoAleatorio);
        
        Inimigo pokemonSelvagem = new Inimigo(nomePokemon,
                                               70 + nivelInimigo * 7,
                                               10 + nivelInimigo * 2,
                                               8 + nivelInimigo,
                                               nivelInimigo,
                                               tipoAleatorio);
        
        System.out.println(pokemonSelvagem.getNome() + " (" + tipoAleatorio + ") quer lutar!");
        batalhar(pokemonSelvagem, false);
    }

    private String gerarNomePokemonPorTipo(String tipo) {
        String[][] nomes = {
            {"Squirtle", "Vaporeon", "Gyarados"},      // água
            {"Charmander", "Flareon", "Arcanine"},     // fogo
            {"Bulbasaur", "Leafeon", "Venusaur"},      // planta
            {"Onix", "Geodude", "Rhydon"},             // pedra
            {"Pikachu", "Jolteon", "Electabuzz"},      // elétrico
            {"Abra", "Espeon", "Alakazam"}             // psíquico
        };
        
        int indice = 0;
        switch (tipo) {
            case "água": indice = 0; break;
            case "fogo": indice = 1; break;
            case "planta": indice = 2; break;
            case "pedra": indice = 3; break;
            case "elétrico": indice = 4; break;
            case "psíquico": indice = 5; break;
        }
        
        return nomes[indice][random.nextInt(nomes[indice].length)];
    }

    private void batalhar(Inimigo inimigo, boolean isRocket) {
        System.out.println("\n⚔️  BATALHA INICIADA!");
        System.out.println("Você: " + jogador.toString());
        System.out.println("Inimigo: " + inimigo.toString());
        
        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n--- Seu turno ---");
            System.out.println("1 - Atacar");
            System.out.println("2 - Tentar Fugir");
            System.out.print("Escolha: ");
            
            String acao = scanner.nextLine().trim();
            
            if (acao.equals("2")) {
                // Tentar fugir (com chance de falha)
                if (tentarFugir()) {
                    System.out.println("\n🏃 Você conseguiu fugir!");
                    return;
                } else {
                    System.out.println("\n❌ Você não conseguiu fugir!");
                }
            } else {
                // Atacar
                turnoAtaque(jogador, inimigo);
            }
            
            if (!inimigo.estaVivo()) {
                vitoria(inimigo, isRocket);
                return;
            }
            
            // Turno do inimigo
            System.out.println("\n--- Turno do inimigo ---");
            turnoAtaque(inimigo, jogador);
            
            if (!jogador.estaVivo()) {
                return;
            }
            
            // Mostrar HP atual
            System.out.println("\nSua vida: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida());
            System.out.println("Vida do inimigo: " + inimigo.getPontosVida() + "/" + inimigo.getMaxPontosVida());
        }
    }

    private void turnoAtaque(Personagem atacante, Personagem defensor) {
        int dado = random.nextInt(6) + 1; // Rolagem de 1d6
        System.out.println(atacante.getNome() + " rolou um " + dado + " no dado!");
        
        int ataqueTotal = atacante.atacar() + dado;
        int defesaTotal = defensor.getDefesa();
        
        System.out.println("Ataque total: " + ataqueTotal + " vs Defesa: " + defesaTotal);
        
        if (ataqueTotal > defesaTotal) {
            int danoBase = ataqueTotal - defesaTotal;
            
            // Aplicar multiplicador de tipo
            double multiplicador = TipoUtil.calcularMultiplicador(atacante.getTipo(), defensor.getTipo());
            int danoFinal = (int) Math.ceil(danoBase * multiplicador);
            
            if (multiplicador > 1.0) {
                System.out.println("🔥 É SUPER EFETIVO! (multiplicador: " + multiplicador + "x)");
            } else if (multiplicador < 1.0) {
                System.out.println("🛡️  Não é muito efetivo... (multiplicador: " + multiplicador + "x)");
            }
            
            defensor.receberDano(danoFinal);
            System.out.println(atacante.getNome() + " causou " + danoFinal + " de dano!");
        } else {
            System.out.println("❌ O ataque não foi forte o suficiente para passar pela defesa!");
        }
    }

    private boolean tentarFugir() {
        int chance = random.nextInt(100);
        return chance < 50; // 50% de chance de fugir
    }

    private void vitoria(Inimigo inimigo, boolean isRocket) {
        System.out.println("\n🎉 VITÓRIA!");
        System.out.println("Você derrotou " + inimigo.getNome() + "!");
        
        // Saquear inventário do inimigo (clonando itens)
        if (!inimigo.getInventario().listarItensOrdenados().isEmpty()) {
            System.out.println("\n💰 Você saqueou o inventário do inimigo!");
            for (Item item : inimigo.getInventario().listarItensOrdenados()) {
                // Clonar apenas parte dos itens (metade)
                int qtdSaqueada = Math.max(1, item.getQuantidade() / 2);
                Item itemSaqueado = item.clone();
                itemSaqueado.setQuantidade(qtdSaqueada);
                jogador.getInventario().adicionarItem(itemSaqueado);
                System.out.println("  + " + itemSaqueado.getNome() + " x" + qtdSaqueada);
            }
        }
        
        // Se derrotou um Rocket, pode encontrar pista
        if (isRocket && random.nextInt(100) < 60) {
            pistasEncontradas++;
            System.out.println("\n🔍 Você encontrou uma PISTA sobre seu amigo!");
            System.out.println("Pistas: " + pistasEncontradas + "/" + PISTAS_PARA_RESGATE);
        }
        
        jogador.recuperarVida();
        System.out.println("\nVocê recuperou um pouco de vida após a batalha.");
    }

    private void encontrarPista() {
        pistasEncontradas++;
        System.out.println("\n🔍 Você encontrou uma PISTA sobre o paradeiro do seu amigo!");
        System.out.println("Pistas: " + pistasEncontradas + "/" + PISTAS_PARA_RESGATE);
    }

    private void encontrarItem() {
        // Itens aleatórios
        String[] nomesItens = {"Poção de Cura", "Poção Maior", "Antídoto", "Reviver"};
        String[] descricoes = {"Recupera vida", "Recupera muita vida", "Cura envenenamento", "Revive de KO"};
        String[] efeitos = {"cura", "cura_maior", "antidoto", "reviver"};
        
        int indice = random.nextInt(nomesItens.length);
        int quantidade = 1 + random.nextInt(3);
        
        Item item = new Item(nomesItens[indice], descricoes[indice], efeitos[indice], quantidade);
        jogador.getInventario().adicionarItem(item);
        
        System.out.println("\n🎁 Você encontrou: " + item.getNome() + " x" + quantidade + "!");
    }

    private void armadilha() {
        System.out.println("\n💥 ARMADILHA!");
        int dado = random.nextInt(6) + 1;
        int dano = 5 + dado;
        jogador.receberDano(dano);
        System.out.println("Você caiu em uma armadilha e perdeu " + dano + " pontos de vida!");
        System.out.println("Vida restante: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida());
    }

    private void verStatus() {
        System.out.println("\n==============================================");
        System.out.println("           STATUS DO PERSONAGEM");
        System.out.println("==============================================");
        System.out.println(jogador.toString());
        System.out.println("\nInventário:");
        if (jogador.getInventario().listarItensOrdenados().isEmpty()) {
            System.out.println("  (vazio)");
        } else {
            for (Item item : jogador.getInventario().listarItensOrdenados()) {
                System.out.println("  - " + item.toString());
            }
        }
        System.out.println("\nPoderes:");
        if (jogador.getPoderes().isEmpty()) {
            System.out.println("  (nenhum poder especial)");
        } else {
            jogador.getPoderes().forEach(p -> System.out.println("  - " + p.toString()));
        }
        System.out.println("==============================================");
    }

    private void usarItem() {
        if (jogador.getInventario().listarItensOrdenados().isEmpty()) {
            System.out.println("\nSeu inventário está vazio!");
            return;
        }
        
        System.out.println("\n--- Inventário ---");
        int i = 1;
        for (Item item : jogador.getInventario().listarItensOrdenados()) {
            System.out.println(i + " - " + item.toString());
            i++;
        }
        System.out.println("0 - Cancelar");
        System.out.print("\nEscolha um item para usar: ");
        
        try {
            int escolha = Integer.parseInt(scanner.nextLine().trim());
            if (escolha == 0) return;
            if (escolha < 1 || escolha > jogador.getInventario().listarItensOrdenados().size()) {
                System.out.println("Opção inválida!");
                return;
            }
            
            Item item = jogador.getInventario().listarItensOrdenados().get(escolha - 1);
            aplicarEfeitoItem(item);
            jogador.getInventario().removerItem(item);
            
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida!");
        }
    }

    private void aplicarEfeitoItem(Item item) {
        switch (item.getEfeito().toLowerCase()) {
            case "cura":
                jogador.curarVida(20);
                System.out.println("\n✨ Você usou " + item.getNome() + " e recuperou 20 pontos de vida!");
                break;
            case "cura_maior":
                jogador.curarVida(50);
                System.out.println("\n✨ Você usou " + item.getNome() + " e recuperou 50 pontos de vida!");
                break;
            default:
                System.out.println("\n✨ Você usou " + item.getNome() + "!");
        }
        System.out.println("Vida atual: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida());
    }

    private void batalhaFinal() {
        System.out.println("\n\n==============================================");
        System.out.println("         VOCÊ ENCONTROU A BASE ROCKET!");
        System.out.println("==============================================");
        System.out.println("\nApós seguir todas as pistas, você finalmente");
        System.out.println("encontrou onde a Equipe Rocket está mantendo");
        System.out.println("seu amigo " + amigo.getNome() + " prisioneiro!");
        System.out.println("\nPressione ENTER para confrontar o líder...");
        scanner.nextLine();
        
        // Chefe da Equipe Rocket
        Inimigo chefe = new Inimigo("Giovanni (Líder Rocket)",
                                     150 + jogador.getNivel() * 10,
                                     20 + jogador.getNivel() * 3,
                                     15 + jogador.getNivel() * 2,
                                     jogador.getNivel() + 5);
        
        // Dar alguns itens ao chefe
        chefe.getInventario().adicionarItem(new Item("Poção Rara", "Poção especial", "cura_maior", 2));
        chefe.getInventario().adicionarItem(new Item("Antídoto Raro", "Antídoto especial", "antidoto", 1));
        
        System.out.println("\n⚔️  BATALHA FINAL!");
        System.out.println("Giovanni: 'Você chegou longe, mas não vai levar seu amigo!'");
        
        batalhar(chefe, true);
        
        if (jogador.estaVivo()) {
            System.out.println("\n\n==============================================");
            System.out.println("           🎊 VOCÊ VENCEU! 🎊");
            System.out.println("==============================================");
            System.out.println("\nGiovanni foi derrotado!");
            System.out.println("Você libertou " + amigo.getNome() + "!");
            System.out.println("\nSeu amigo: 'Obrigado por me salvar!'");
            System.out.println("\nVocês voltam para casa juntos e em segurança.");
            System.out.println("\n✨ PARABÉNS! VOCÊ COMPLETOU O JOGO! ✨");
            System.out.println("==============================================");
        }
    }
}
