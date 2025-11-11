package rpg;

import rpg.item.Item;
import rpg.mapa.Mapa;
import rpg.mapa.Local;
import rpg.mapa.Evento;
import rpg.personagem.Inimigo;
import rpg.personagem.Personagem;
import rpg.util.TipoUtil;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Jogo {
    private Personagem jogador;
    private Personagem amigo; // O amigo capturado pela Equipe Rocket
    private Scanner scanner;
    private Random random;
    private int pistasEncontradas;
    private static final int PISTAS_PARA_RESGATE = 3;
    private Mapa mapa;

    public Jogo(Personagem jogador, Personagem amigo) {
        this.jogador = jogador;
        this.amigo = amigo;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.pistasEncontradas = 0;
        this.mapa = new Mapa();
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
            System.out.println("Local atual: " + mapa.getLocalAtual().getNome());
            System.out.println("==============================================");
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Explorar local atual");
            System.out.println("2 - Ver Status");
            System.out.println("3 - Usar Item do Inventário");
            System.out.println("4 - Ver mapa completo");
            System.out.println("5 - Viajar para outro local");
            System.out.println("6 - Mapa compacto");
            System.out.println("7 - Sair do jogo");
            System.out.print("\nEscolha: ");

            String escolha = scanner.nextLine().trim();

            switch (escolha) {
                case "1":
                    explorarLocalAtual();
                    break;
                case "2":
                    verStatus();
                    break;
                case "3":
                    usarItem();
                    break;
                case "4":
                    mostrarMapa();
                    break;
                case "5":
                    viajar();
                    break;
                case "6":
                    mostrarMapaCompacto();
                    break;
                case "7":
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
        System.out.println("\n*** BATALHA INICIADA! ***");
        System.out.println("Você: " + jogador.toString());
        System.out.println("Inimigo: " + inimigo.toString());
        
        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n--- Seu turno ---");
            System.out.println("1 - Atacar");
            System.out.println("2 - Ver Inventário");
            System.out.println("3 - Usar Item");
            System.out.println("4 - Tentar Fugir");
            System.out.print("Escolha: ");
            
            String acao = scanner.nextLine().trim();
            
            boolean usouTurno = false; // Controla se o jogador usou o turno
            
            switch (acao) {
                case "1":
                    // Atacar
                    turnoAtaque(jogador, inimigo);
                    usouTurno = true;
                    break;
                    
                case "2":
                    // Ver inventário (não consome turno)
                    System.out.println("\n=== INVENTÁRIO ===");
                    if (jogador.getInventario().listarItensOrdenados().isEmpty()) {
                        System.out.println("(vazio)");
                    } else {
                        for (Item item : jogador.getInventario().listarItensOrdenados()) {
                            System.out.println("  - " + item.toString());
                        }
                    }
                    continue; // Volta para o menu sem consumir turno
                    
                case "3":
                    // Usar item (consome turno)
                    if (usarItemBatalha()) {
                        usouTurno = true;
                    } else {
                        continue; // Se não usou item, volta para o menu
                    }
                    break;
                    
                case "4":
                    // Tentar fugir (consome turno)
                    if (tentarFugir()) {
                        System.out.println("\nVocê conseguiu fugir!");
                        return;
                    } else {
                        System.out.println("\nVocê não conseguiu fugir!");
                        usouTurno = true;
                    }
                    break;
                    
                default:
                    System.out.println("\nOpção inválida!");
                    continue; // Volta para o menu sem consumir turno
            }
            
            if (!usouTurno) {
                continue; // Se não usou o turno, volta para o menu
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
                System.out.println(">>> É SUPER EFETIVO! (multiplicador: " + multiplicador + "x)");
            } else if (multiplicador < 1.0) {
                System.out.println(">>> Não é muito efetivo... (multiplicador: " + multiplicador + "x)");
            }
            
            defensor.receberDano(danoFinal);
            System.out.println(atacante.getNome() + " causou " + danoFinal + " de dano!");
        } else {
            System.out.println("X O ataque não foi forte o suficiente para passar pela defesa!");
        }
    }

    private boolean tentarFugir() {
        int chance = random.nextInt(100);
        return chance < 50; // 50% de chance de fugir
    }

    private void vitoria(Inimigo inimigo, boolean isRocket) {
        System.out.println("\n*** VITÓRIA! ***");
        System.out.println("Você derrotou " + inimigo.getNome() + "!");
        
        // Saquear inventário do inimigo (clonando itens)
        if (!inimigo.getInventario().listarItensOrdenados().isEmpty()) {
            System.out.println("\n[SAQUE] Você saqueou o inventário do inimigo!");
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
            System.out.println("\n[PISTA] Você encontrou uma PISTA sobre seu amigo!");
            System.out.println("Pistas: " + pistasEncontradas + "/" + PISTAS_PARA_RESGATE);
        }
        
        jogador.recuperarVida();
        System.out.println("\nVocê recuperou um pouco de vida após a batalha.");
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
                System.out.println("\n[ITEM] Você usou " + item.getNome() + " e recuperou 20 pontos de vida!");
                break;
            case "cura_maior":
                jogador.curarVida(50);
                System.out.println("\n[ITEM] Você usou " + item.getNome() + " e recuperou 50 pontos de vida!");
                break;
            default:
                System.out.println("\n[ITEM] Você usou " + item.getNome() + "!");
        }
        System.out.println("Vida atual: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida());
    }

    private boolean usarItemBatalha() {
        if (jogador.getInventario().listarItensOrdenados().isEmpty()) {
            System.out.println("\nSeu inventário está vazio!");
            System.out.println("Pressione ENTER para continuar...");
            scanner.nextLine();
            return false; // Não usou turno
        }
        
        System.out.println("\n=== USAR ITEM ===");
        int i = 1;
        for (Item item : jogador.getInventario().listarItensOrdenados()) {
            System.out.println(i + " - " + item.toString());
            i++;
        }
        System.out.println("0 - Cancelar");
        System.out.print("\nEscolha um item para usar: ");
        
        try {
            int escolha = Integer.parseInt(scanner.nextLine().trim());
            if (escolha == 0) {
                return false; // Cancelou, não usa turno
            }
            if (escolha < 1 || escolha > jogador.getInventario().listarItensOrdenados().size()) {
                System.out.println("Opção inválida!");
                System.out.println("Pressione ENTER para continuar...");
                scanner.nextLine();
                return false; // Não usou turno
            }
            
            Item item = jogador.getInventario().listarItensOrdenados().get(escolha - 1);
            aplicarEfeitoItem(item);
            jogador.getInventario().removerItem(item);
            return true; // Usou o item, consome turno
            
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida!");
            System.out.println("Pressione ENTER para continuar...");
            scanner.nextLine();
            return false; // Não usou turno
        }
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
        
        System.out.println("\n*** BATALHA FINAL! ***");
        System.out.println("Giovanni: 'Você chegou longe, mas não vai levar seu amigo!'");
        
        batalhar(chefe, true);
        
        if (jogador.estaVivo()) {
            System.out.println("\n\n==============================================");
            System.out.println("           *** VOCÊ VENCEU! ***");
            System.out.println("==============================================");
            System.out.println("\nGiovanni foi derrotado!");
            System.out.println("Você libertou " + amigo.getNome() + "!");
            System.out.println("\nSeu amigo: 'Obrigado por me salvar!'");
            System.out.println("\nVocês voltam para casa juntos e em segurança.");
            System.out.println("\n*** PARABÉNS! VOCÊ COMPLETOU O JOGO! ***");
            System.out.println("==============================================");
        }
    }

    public void mostrarMapa() {
        mapa.mostrarMapa();
    }

    public void mostrarMapaCompacto() {
        System.out.println("\n[MAPA RÁPIDO]:");
        System.out.println("┌─[Torre]──[Ruínas]──[Floresta]──[Caverna]─┐");
        System.out.println("│    │        │         │         │        │");
        System.out.println("│ [Fort.]     │     [Entrada]     │        │");
        System.out.println("│    │        │         │         │        │");
        System.out.println("└─[Ponte]──[Campo]────[Lago]──[Pântano]────┘");
        
        String localAtual = mapa.getLocalAtual().getNome();
        System.out.println(">> Você está em: " + localAtual);
    }

    public void viajar() {
        mapa.mostrarMapa();
        System.out.println("\n>> Para onde deseja ir?");
        
        List<Local> conectados = mapa.getLocaisConectados();
        
        if (conectados.isEmpty()) {
            System.out.println("Não há locais conectados!");
            return;
        }
        
        System.out.println("0. Cancelar viagem");
        for (int i = 0; i < conectados.size(); i++) {
            System.out.println((i + 1) + ". " + conectados.get(i).getNome());
        }
        
        try {
            int escolha = scanner.nextInt();
            scanner.nextLine();
            
            if (escolha == 0) {
                System.out.println("Viagem cancelada.");
                return;
            }
            
            if (escolha > 0 && escolha <= conectados.size()) {
                Local destino = conectados.get(escolha - 1);
                String nomeDestino = "";
                
                // Mapear nome do local para a chave do mapa
                Map<String, String> mapeamento = Map.of(
                    "Entrada da Floresta", "entrada",
                    "Floresta Sombria", "floresta",
                    "Caverna Misteriosa", "caverna",
                    "Lago Cristalino", "lago",
                    "Ruínas Antigas", "ruinas",
                    "Pântano Venenoso", "pantano",
                    "Campo de Flores", "campo",
                    "Torre Abandonada", "torre",
                    "Ponte de Pedra", "ponte",
                    "Fortaleza do Chefe", "fortaleza"
                );
                
                nomeDestino = mapeamento.get(destino.getNome());
                
                if (mapa.moverPara(nomeDestino)) {
                    System.out.println("\n>> Você viajou para: " + destino.getNome());
                    System.out.println("Descrição: " + destino.getDescricao());
                    System.out.println("\n[DICA] Use a opção '1 - Explorar local atual' para investigar este lugar!");
                } else {
                    System.out.println("X Não foi possível viajar para este local!");
                }
            } else {
                System.out.println("X Opção inválida!");
            }
        } catch (Exception e) {
            System.out.println("X Entrada inválida!");
            scanner.nextLine();
        }
    }

    private void explorarLocalAtual() {
        Local localAtual = mapa.getLocalAtual();
        
        System.out.println("\n[EXPLORANDO]: " + localAtual.getNome());
        System.out.println("Descrição: " + localAtual.getDescricao());
        
        // Verifica se há eventos pendentes neste local
        if (!localAtual.temEventosPendentes()) {
            System.out.println("\n[OK] Você já explorou este local completamente.");
            System.out.println("[DICA] Viaje para outros locais para continuar sua jornada!");
            return;
        }
        
        // Pega o próximo evento disponível
        Evento evento = localAtual.getProximoEvento();
        if (evento == null) {
            System.out.println("\nEste local está calmo no momento.");
            return;
        }
        
        // Exibe a descrição do evento
        System.out.println("\n" + evento.getDescricao());
        System.out.println(">> " + evento.getDetalhes());
        
        // Processa o evento baseado no tipo
        String tipo = evento.getTipo();
        switch (tipo) {
            case "pista":
                System.out.println("\n*** PISTA ENCONTRADA! ***");
                evento.marcarComoOcorrido();
                pistasEncontradas++;
                System.out.println("Total de pistas: " + pistasEncontradas + "/" + PISTAS_PARA_RESGATE);
                if (pistasEncontradas >= PISTAS_PARA_RESGATE) {
                    System.out.println("\n[DESBLOQUEADO] Você coletou pistas suficientes! A Fortaleza do Chefe está acessível!");
                }
                break;
                
            case "combate":
                System.out.println("\n*** BATALHA! ***");
                Inimigo inimigo = gerarInimigoAleatorio();
                batalhar(inimigo);
                if (jogador.estaVivo()) {
                    evento.marcarComoOcorrido();
                    // 25% de chance de dropar um item após vitória
                    if (random.nextInt(100) < 25) {
                        System.out.println("\n[DROP] O inimigo deixou cair um item!");
                        Item item = gerarItemAleatorio();
                        jogador.getInventario().adicionarItem(item);
                        System.out.println("Você ganhou: " + item.toString());
                    }
                }
                break;
                
            case "item":
                System.out.println("\n[ITEM] ITEM ENCONTRADO!");
                Item item = gerarItemAleatorio();
                jogador.getInventario().adicionarItem(item);
                System.out.println("Você encontrou: " + item.toString());
                evento.marcarComoOcorrido();
                break;
                
            case "armadilha":
                System.out.println("\n[!] ARMADILHA!");
                int dano = random.nextInt(15) + 5;
                jogador.receberDano(dano);
                System.out.println(">> Você perdeu " + dano + " pontos de vida!");
                System.out.println("HP atual: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida());
                evento.marcarComoOcorrido();
                break;
                
            case "chefe":
                if (mapa.podeAcessarChefe()) {
                    System.out.println("\n===== BATALHA FINAL =====");
                    System.out.println("Seu amigo está aqui! Prepare-se para a batalha final!");
                    batalhaFinal();
                } else {
                    System.out.println("\n[BLOQUEADO] A fortaleza está selada!");
                    System.out.println("Você precisa de " + PISTAS_PARA_RESGATE + " pistas para entrar.");
                    System.out.println("Pistas atuais: " + pistasEncontradas + "/" + PISTAS_PARA_RESGATE);
                }
                break;
                
            case "dialogo":
                System.out.println("\n[ENCONTRO]");
                evento.marcarComoOcorrido();
                // Alguns diálogos podem dar benefícios
                if (evento.getDetalhes().contains("HP") || evento.getDetalhes().contains("cura")) {
                    int cura = 20;
                    jogador.curar(cura);
                    System.out.println("HP: Você recuperou " + cura + " pontos de vida!");
                }
                break;
                
            case "descanso":
                System.out.println("\n[DESCANSO] LOCAL DE DESCANSO");
                int cura = 30;
                jogador.curar(cura);
                System.out.println("HP: Você descansou e recuperou " + cura + " pontos de vida!");
                System.out.println("HP atual: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida());
                evento.marcarComoOcorrido();
                break;
                
            default:
                System.out.println("\nNada de especial acontece aqui.");
                evento.marcarComoOcorrido();
                break;
        }
    }

    private Inimigo gerarInimigoAleatorio() {
        String[] tipos = {"água", "fogo", "planta", "pedra", "elétrico", "psíquico"};
        String tipoAleatorio = tipos[random.nextInt(tipos.length)];
        int nivelInimigo = Math.max(1, jogador.getNivel() + random.nextInt(3) - 1);
        
        String nomePokemon = gerarNomePokemonPorTipo(tipoAleatorio);
        
        return new Inimigo(nomePokemon,
                           70 + nivelInimigo * 7,
                           10 + nivelInimigo * 2,
                           8 + nivelInimigo,
                           nivelInimigo,
                           tipoAleatorio);
    }

    private Item gerarItemAleatorio() {
        String[] nomesItens = {"Poção de Cura", "Poção Maior", "Antídoto", "Reviver"};
        String[] descricoes = {"Recupera vida", "Recupera muita vida", "Cura envenenamento", "Revive de KO"};
        String[] efeitos = {"cura", "cura_maior", "antidoto", "reviver"};
        
        int indice = random.nextInt(nomesItens.length);
        int quantidade = 1 + random.nextInt(3); // 1 a 3 itens
        
        return new Item(nomesItens[indice], descricoes[indice], efeitos[indice], quantidade);
    }

    // Método sobrecarregado para batalhar (sem segundo parâmetro)
    private void batalhar(Inimigo inimigo) {
        batalhar(inimigo, false);
    }
}
