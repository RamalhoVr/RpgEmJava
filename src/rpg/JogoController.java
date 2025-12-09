package rpg;

import rpg.item.Item;
import rpg.mapa.Local;
import rpg.mapa.Mapa;
import rpg.personagem.Inimigo;
import rpg.personagem.Personagem;
import rpg.util.TipoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controlador do jogo que gerencia a lógica sem depender de Scanner ou System.out.
 * Projetado para trabalhar com interfaces gráficas.
 */
public class JogoController {
    private Personagem jogador;
    private Personagem amigo;
    private Random random;
    private int pistasEncontradas;
    private static final int PISTAS_PARA_RESGATE = 3;
    private Mapa mapa;
    
    // Estado da batalha atual
    private Inimigo inimigoAtual;
    private boolean emBatalha;
    private boolean batalhaComRocket;

    public JogoController(Personagem jogador, Personagem amigo) {
        this.jogador = jogador;
        this.amigo = amigo;
        this.random = new Random();
        this.pistasEncontradas = 0;
        this.mapa = new Mapa();
        this.emBatalha = false;
    }

    // ========== Getters ==========
    
    public Personagem getJogador() {
        return jogador;
    }

    public Personagem getAmigo() {
        return amigo;
    }

    public int getPistasEncontradas() {
        return pistasEncontradas;
    }

    public int getPistasNecessarias() {
        return PISTAS_PARA_RESGATE;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public boolean isEmBatalha() {
        return emBatalha;
    }

    public Inimigo getInimigoAtual() {
        return inimigoAtual;
    }

    public boolean podeIniciarBatalhaFinal() {
        return pistasEncontradas >= PISTAS_PARA_RESGATE;
    }

    // ========== Classe de Resultado de Exploração ==========
    
    public static class ResultadoExploracao {
        public enum TipoEvento {
            ROCKET, POKEMON_SELVAGEM, PISTA, ITEM, ARMADILHA
        }
        
        public TipoEvento tipo;
        public String mensagem;
        public Inimigo inimigo;
        public Item item;
        
        public ResultadoExploracao(TipoEvento tipo, String mensagem) {
            this.tipo = tipo;
            this.mensagem = mensagem;
        }
    }

    // ========== Classe de Resultado de Batalha ==========
    
    public static class ResultadoBatalha {
        public boolean vitoria;
        public boolean fugiu;
        public String mensagem;
        public int expGanha;
        public List<Item> itensGanhos;
        public boolean jogadorMorreu;
        public boolean subiuNivel;
        
        public ResultadoBatalha() {
            this.itensGanhos = new ArrayList<>();
        }
    }

    // ========== Classe de Resultado de Turno ==========
    
    public static class ResultadoTurno {
        public String mensagem;
        public int danoJogador;
        public int danoInimigo;
        public boolean batalhaTerminou;
        public boolean vitoria;
        public boolean derrota;
        
        public ResultadoTurno(String mensagem) {
            this.mensagem = mensagem;
        }
    }

    // ========== Métodos Principais ==========
    
    /**
     * Explora o local atual e retorna o evento que ocorreu
     */
    public ResultadoExploracao explorar() {
        int evento = random.nextInt(100);
        
        if (evento < 20) {
            // 20% - Encontro com membro da Equipe Rocket
            return encontroComRocket();
        } else if (evento < 50) {
            // 30% - Pokémon selvagem
            return encontroComPokemonSelvagem();
        } else if (evento < 70) {
            // 20% - Encontrou uma pista!
            return encontrarPista();
        } else if (evento < 95) {
            // 25% - Encontrou um item!
            return encontrarItem();
        } else {
            // 5% - Armadilha!
            return armadilha();
        }
    }

    private ResultadoExploracao encontroComRocket() {
        int nivelInimigo = jogador.getNivel() + random.nextInt(5) - 2; // -2 a +2
        nivelInimigo = Math.max(1, nivelInimigo); // Mínimo nível 1
        inimigoAtual = new Inimigo("Rocket " + random.nextInt(100), 
                                    80 + nivelInimigo * 8,
                                    12 + nivelInimigo * 2,
                                    8 + nivelInimigo,
                                    nivelInimigo);
        
        emBatalha = true;
        batalhaComRocket = true;
        
        ResultadoExploracao resultado = new ResultadoExploracao(
            ResultadoExploracao.TipoEvento.ROCKET,
            "⚠️  Um membro da Equipe Rocket apareceu!\n" +
            inimigoAtual.getNome() + " desafia você para a batalha!"
        );
        resultado.inimigo = inimigoAtual;
        return resultado;
    }

    private ResultadoExploracao encontroComPokemonSelvagem() {
        String[] tipos = {"\u00e1gua", "fogo", "planta", "pedra", "el\u00e9trico", "ps\u00edquico"};
        String tipoAleatorio = tipos[random.nextInt(tipos.length)];
        int nivelInimigo = jogador.getNivel() + random.nextInt(5) - 2; // -2 a +2
        nivelInimigo = Math.max(1, nivelInimigo); // Mínimo nível 1
        
        String nomePokemon = gerarNomePokemonPorTipo(tipoAleatorio);
        
        inimigoAtual = new Inimigo(nomePokemon,
                                    70 + nivelInimigo * 7,
                                    10 + nivelInimigo * 2,
                                    8 + nivelInimigo,
                                    nivelInimigo,
                                    tipoAleatorio);
        
        emBatalha = true;
        batalhaComRocket = false;
        
        ResultadoExploracao resultado = new ResultadoExploracao(
            ResultadoExploracao.TipoEvento.POKEMON_SELVAGEM,
            "🐾 Um Pokémon selvagem apareceu!\n" +
            inimigoAtual.getNome() + " (" + tipoAleatorio + ") quer lutar!"
        );
        resultado.inimigo = inimigoAtual;
        return resultado;
    }

    private ResultadoExploracao encontrarPista() {
        pistasEncontradas++;
        
        String[] pistas = {
            "Você encontrou um pedaço de papel com o símbolo da Equipe Rocket!",
            "Você ouviu rumores sobre o esconderijo da Equipe Rocket!",
            "Você encontrou um mapa parcialmente queimado com uma localização marcada!",
            "Alguém deixou cair um comunicador da Equipe Rocket!",
            "Você encontrou pegadas suspeitas levando a uma direção específica!"
        };
        
        String mensagem = "🔍 " + pistas[random.nextInt(pistas.length)] + "\n" +
                         "Pistas encontradas: " + pistasEncontradas + "/" + PISTAS_PARA_RESGATE;
        
        if (pistasEncontradas >= PISTAS_PARA_RESGATE) {
            mensagem += "\n\n🎯 Você tem pistas suficientes! Pode enfrentar o líder da Equipe Rocket!";
        }
        
        return new ResultadoExploracao(ResultadoExploracao.TipoEvento.PISTA, mensagem);
    }

    private ResultadoExploracao encontrarItem() {
        Item itemEncontrado = gerarItemAleatorio();
        jogador.getInventario().adicionarItem(itemEncontrado);
        
        String mensagem = "✨ Você encontrou: " + itemEncontrado.getNome() + 
                         " x" + itemEncontrado.getQuantidade() + "!\n" +
                         itemEncontrado.getDescricao();
        
        ResultadoExploracao resultado = new ResultadoExploracao(
            ResultadoExploracao.TipoEvento.ITEM,
            mensagem
        );
        resultado.item = itemEncontrado;
        return resultado;
    }

    private ResultadoExploracao armadilha() {
        int dano = 10 + random.nextInt(11); // 10-20 de dano
        jogador.receberDano(dano);
        
        String mensagem = "💥 Você caiu em uma armadilha!\n" +
                         "Perdeu " + dano + " pontos de vida!\n" +
                         "Vida atual: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida();
        
        return new ResultadoExploracao(ResultadoExploracao.TipoEvento.ARMADILHA, mensagem);
    }

    private String gerarNomePokemonPorTipo(String tipo) {
        String[][] nomes = {
            {"Squirtle", "Vaporeon", "Gyarados"},
            {"Charmander", "Flareon", "Arcanine"},
            {"Bulbasaur", "Leafeon", "Venusaur"},
            {"Onix", "Geodude", "Rhydon"},
            {"Pikachu", "Jolteon", "Electabuzz"},
            {"Abra", "Espeon", "Alakazam"}
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

    private Item gerarItemAleatorio() {
        String[] nomes = {"Poção", "Super Poção", "Reviver", "Elixir"};
        String[] descricoes = {
            "Recupera 20 pontos de vida",
            "Recupera 50 pontos de vida",
            "Recupera completamente a vida",
            "Aumenta temporariamente o ataque"
        };
        String[] efeitos = {"cura", "cura", "cura", "buff"};
        
        int idx = random.nextInt(nomes.length);
        int quantidade = 1 + random.nextInt(3);
        
        return new Item(nomes[idx], descricoes[idx], efeitos[idx], quantidade);
    }

    // ========== Métodos de Batalha ==========
    
    /**
     * Usa um poder do jogador na batalha
     */
    public ResultadoTurno usarPoder(rpg.poderes.Poder poder) {
        if (!emBatalha || inimigoAtual == null) {
            ResultadoTurno resultado = new ResultadoTurno("Não há batalha em andamento!");
            resultado.batalhaTerminou = true;
            return resultado;
        }
        
        StringBuilder mensagem = new StringBuilder();
        
        // Turno do jogador usando poder
        int dadoJogador = random.nextInt(6) + 1;
        mensagem.append("✨ Você usou ").append(poder.getNome()).append("!\n");
        mensagem.append("🎲 Você rolou um ").append(dadoJogador).append(" no dado!\n");
        
        // Poderes dão +5 de bônus ao ataque
        int ataqueTotal = jogador.atacar() + dadoJogador + 5;
        int defesaInimigo = inimigoAtual.getDefesa();
        
        mensagem.append("⚔️ Seu ataque: ").append(ataqueTotal)
                .append(" vs Defesa inimiga: ").append(defesaInimigo).append("\n");
        
        int danoAoInimigo = 0;
        if (ataqueTotal > defesaInimigo) {
            double multiplicador = TipoUtil.calcularMultiplicador(jogador.getTipo(), inimigoAtual.getTipo());
            danoAoInimigo = (int) ((ataqueTotal - defesaInimigo) * multiplicador);
            inimigoAtual.receberDano(danoAoInimigo);
            
            mensagem.append("💥 ").append(inimigoAtual.getNome()).append(" recebeu ")
                    .append(danoAoInimigo).append(" de dano!");
            
            if (multiplicador > 1.0) {
                mensagem.append(" (Super efetivo! ×").append(multiplicador).append(")");
            } else if (multiplicador < 1.0) {
                mensagem.append(" (Pouco efetivo... ×").append(multiplicador).append(")");
            }
            mensagem.append("\n");
        } else {
            mensagem.append("🛡️ O inimigo defendeu!\n");
        }
        
        // Verificar se o inimigo morreu
        ResultadoTurno resultado = new ResultadoTurno(mensagem.toString());
        resultado.danoInimigo = danoAoInimigo;
        
        if (!inimigoAtual.estaVivo()) {
            resultado.batalhaTerminou = true;
            resultado.vitoria = true;
            return resultado;
        }
        
        // Turno do inimigo
        mensagem.append("\n--- Turno do inimigo ---\n");
        int dadoInimigo = random.nextInt(6) + 1;
        mensagem.append("🎲 ").append(inimigoAtual.getNome()).append(" rolou um ")
                .append(dadoInimigo).append(" no dado!\n");
        
        int ataqueInimigo = inimigoAtual.atacar() + dadoInimigo;
        int defesaJogador = jogador.getDefesa();
        
        mensagem.append("⚔️ Ataque inimigo: ").append(ataqueInimigo)
                .append(" vs Sua defesa: ").append(defesaJogador).append("\n");
        
        int danoAoJogador = 0;
        if (ataqueInimigo > defesaJogador) {
            double multiplicador = TipoUtil.calcularMultiplicador(inimigoAtual.getTipo(), jogador.getTipo());
            danoAoJogador = (int) ((ataqueInimigo - defesaJogador) * multiplicador);
            jogador.receberDano(danoAoJogador);
            
            mensagem.append("💔 Você recebeu ").append(danoAoJogador).append(" de dano!");
            
            if (multiplicador > 1.0) {
                mensagem.append(" (Super efetivo! ×").append(multiplicador).append(")");
            } else if (multiplicador < 1.0) {
                mensagem.append(" (Pouco efetivo... ×").append(multiplicador).append(")");
            }
            mensagem.append("\n");
        } else {
            mensagem.append("🛡️ Você defendeu o ataque!\n");
        }
        
        resultado.mensagem = mensagem.toString();
        resultado.danoJogador = danoAoJogador;
        
        // Verificar se o jogador morreu
        if (!jogador.estaVivo()) {
            resultado.batalhaTerminou = true;
            resultado.derrota = true;
        }
        
        return resultado;
    }
    
    /**
     * Executa um turno de ataque na batalha
     */
    public ResultadoTurno atacar() {
        if (!emBatalha || inimigoAtual == null) {
            ResultadoTurno resultado = new ResultadoTurno("Não há batalha em andamento!");
            resultado.batalhaTerminou = true;
            return resultado;
        }
        
        StringBuilder mensagem = new StringBuilder();
        
        // Turno do jogador
        int dadoJogador = random.nextInt(6) + 1;
        mensagem.append("🎲 Você rolou um ").append(dadoJogador).append(" no dado!\n");
        
        int ataqueTotal = jogador.atacar() + dadoJogador;
        int defesaInimigo = inimigoAtual.getDefesa();
        
        mensagem.append("⚔️ Ataque total: ").append(ataqueTotal)
                .append(" vs Defesa: ").append(defesaInimigo).append("\n");
        
        int danoAoInimigo = 0;
        if (ataqueTotal > defesaInimigo) {
            double multiplicador = TipoUtil.calcularMultiplicador(jogador.getTipo(), inimigoAtual.getTipo());
            danoAoInimigo = (int) ((ataqueTotal - defesaInimigo) * multiplicador);
            inimigoAtual.receberDano(danoAoInimigo);
            
            mensagem.append("💥 Causou ").append(danoAoInimigo).append(" de dano!");
            
            if (multiplicador > 1.0) {
                mensagem.append(" (Super efetivo! ×").append(multiplicador).append(")");
            } else if (multiplicador < 1.0) {
                mensagem.append(" (Pouco efetivo... ×").append(multiplicador).append(")");
            }
            mensagem.append("\n");
        } else {
            mensagem.append("❌ O inimigo defendeu o ataque!\n");
        }
        
        // Verificar se o inimigo morreu
        if (!inimigoAtual.estaVivo()) {
            ResultadoTurno resultado = new ResultadoTurno(mensagem.toString());
            resultado.danoInimigo = danoAoInimigo;
            resultado.batalhaTerminou = true;
            resultado.vitoria = true;
            return resultado;
        }
        
        // Turno do inimigo
        mensagem.append("\n--- Turno do inimigo ---\n");
        int dadoInimigo = random.nextInt(6) + 1;
        mensagem.append("🎲 ").append(inimigoAtual.getNome()).append(" rolou um ")
                .append(dadoInimigo).append(" no dado!\n");
        
        int ataqueInimigo = inimigoAtual.atacar() + dadoInimigo;
        int defesaJogador = jogador.getDefesa();
        
        mensagem.append("⚔️ Ataque inimigo: ").append(ataqueInimigo)
                .append(" vs Sua defesa: ").append(defesaJogador).append("\n");
        
        int danoAoJogador = 0;
        if (ataqueInimigo > defesaJogador) {
            double multiplicador = TipoUtil.calcularMultiplicador(inimigoAtual.getTipo(), jogador.getTipo());
            danoAoJogador = (int) ((ataqueInimigo - defesaJogador) * multiplicador);
            jogador.receberDano(danoAoJogador);
            
            mensagem.append("💔 Você recebeu ").append(danoAoJogador).append(" de dano!");
            
            if (multiplicador > 1.0) {
                mensagem.append(" (Super efetivo! ×").append(multiplicador).append(")");
            } else if (multiplicador < 1.0) {
                mensagem.append(" (Pouco efetivo... ×").append(multiplicador).append(")");
            }
            mensagem.append("\n");
        } else {
            mensagem.append("🛡️ Você defendeu o ataque!\n");
        }
        
        ResultadoTurno resultado = new ResultadoTurno(mensagem.toString());
        resultado.danoJogador = danoAoJogador;
        resultado.danoInimigo = danoAoInimigo;
        
        // Verificar se o jogador morreu
        if (!jogador.estaVivo()) {
            resultado.batalhaTerminou = true;
            resultado.derrota = true;
        }
        
        return resultado;
    }

    /**
     * Tenta fugir da batalha
     */
    public ResultadoBatalha tentarFugir() {
        ResultadoBatalha resultado = new ResultadoBatalha();
        
        int chance = random.nextInt(100);
        if (chance < 60) {
            resultado.fugiu = true;
            resultado.mensagem = "🏃 Você conseguiu fugir da batalha!";
            encerrarBatalha();
        } else {
            resultado.fugiu = false;
            resultado.mensagem = "❌ Não conseguiu fugir! O inimigo te alcançou!";
        }
        
        return resultado;
    }

    /**
     * Finaliza a batalha com vitória
     */
    public ResultadoBatalha finalizarBatalhaVitoria() {
        ResultadoBatalha resultado = new ResultadoBatalha();
        resultado.vitoria = true;
        resultado.subiuNivel = false;
        
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("🎉 VITÓRIA!\n");
        mensagem.append("Você derrotou ").append(inimigoAtual.getNome()).append("!\n\n");
        
        // Ganhar XP
        int expGanha = inimigoAtual.getNivel() * 100;
        resultado.expGanha = expGanha;
        boolean subiuNivel = jogador.ganharExperiencia(expGanha);
        resultado.subiuNivel = subiuNivel;
        mensagem.append("💫 Ganhou ").append(expGanha).append(" pontos de experiência!\n");
        mensagem.append("📊 EXP: ").append(jogador.getExperiencia()).append("/")
                .append(jogador.getExpParaProximoNivel()).append("\n");
        
        if (subiuNivel) {
            mensagem.append("\n⭐ LEVEL UP! Agora você é nível ").append(jogador.getNivel()).append("!\n");
            mensagem.append("HP máximo aumentou!\n");
            mensagem.append("Ataque e Defesa aumentaram!\n");
        }
        
        // Recuperar vida
        if (!subiuNivel) { // Só recupera 5 se não subiu de nível (level up já cura total)
            jogador.recuperarVida();
            mensagem.append("❤️ Recuperou 5 pontos de vida!\n");
        }
        
        // Saquear itens
        List<Item> itens = inimigoAtual.getInventario().listarItensOrdenados();
        if (!itens.isEmpty()) {
            mensagem.append("\n💰 Itens saqueados:\n");
            for (Item item : itens) {
                Item itemClonado = item.clone();
                itemClonado.setQuantidade(Math.max(1, item.getQuantidade() / 2));
                jogador.getInventario().adicionarItem(itemClonado);
                resultado.itensGanhos.add(itemClonado);
                mensagem.append("  - ").append(itemClonado.getNome())
                        .append(" x").append(itemClonado.getQuantidade()).append("\n");
            }
        }
        
        // Chance de encontrar pista se for Rocket (só até 3 pistas)
        if (batalhaComRocket && pistasEncontradas < PISTAS_PARA_RESGATE && random.nextInt(100) < 25) {
            pistasEncontradas++;
            mensagem.append("\n🔍 Você encontrou uma pista sobre o esconderijo da Equipe Rocket!\n");
            mensagem.append("Pistas: ").append(pistasEncontradas).append("/").append(PISTAS_PARA_RESGATE);
        }
        
        resultado.mensagem = mensagem.toString();
        encerrarBatalha();
        
        return resultado;
    }

    private void encerrarBatalha() {
        emBatalha = false;
        inimigoAtual = null;
    }

    /**
     * Usa um item do inventário
     */
    public String usarItem(Item item) {
        return usarItem(item, false);
    }
    
    /**
     * Usa um item do inventário (em batalha ou fora)
     */
    public String usarItem(Item item, boolean emBatalha) {
        StringBuilder mensagem = new StringBuilder();
        String nomeLower = item.getNome().toLowerCase();
        String efeito = item.getEfeito().toLowerCase();
        
        // Itens de cura
        if (nomeLower.contains("poção") || nomeLower.contains("pocao") || 
            nomeLower.contains("reviver") || nomeLower.contains("elixir") ||
            efeito.equals("cura") || efeito.equals("cura_maior")) {
            
            int vidaAntes = jogador.getPontosVida();
            int vidaMax = jogador.getMaxPontosVida();
            
            // Já está com vida cheia?
            if (vidaAntes >= vidaMax) {
                mensagem.append("⚠️ Sua vida já está no máximo! (")
                        .append(vidaMax).append("/").append(vidaMax).append(")");
                return mensagem.toString();
            }
            
            // Determinar quanto cura
            if (nomeLower.contains("super") || efeito.equals("cura_maior")) {
                jogador.curarVida(50);
                mensagem.append("❤️ Você usou ").append(item.getNome()).append("!\n");
            } else if (nomeLower.contains("reviver")) {
                jogador.curarVida(vidaMax);
                mensagem.append("💚 Você usou ").append(item.getNome()).append("!\n");
            } else if (nomeLower.contains("elixir")) {
                jogador.curarVida(vidaMax);
                mensagem.append("✨ Você usou ").append(item.getNome()).append("!\n");
            } else {
                jogador.curarVida(20);
                mensagem.append("❤️ Você usou ").append(item.getNome()).append("!\n");
            }
            
            int vidaCurada = jogador.getPontosVida() - vidaAntes;
            mensagem.append("Recuperou ").append(vidaCurada).append(" pontos de vida!\n");
            mensagem.append("Vida: ").append(jogador.getPontosVida())
                    .append("/").append(jogador.getMaxPontosVida());
            
            // Remover item do inventário
            jogador.getInventario().removerItem(item);
            
        // Antídotos (futuramente pode ter status de envenenamento)
        } else if (nomeLower.contains("antídoto") || nomeLower.contains("antidoto") || 
                   efeito.equals("antidoto")) {
            mensagem.append("💊 Você usou ").append(item.getNome()).append("!\n");
            mensagem.append("Você se sente revigorado!\n");
            mensagem.append("(Nenhum efeito negativo ativo no momento)");
            
            // Ainda assim remove o item
            jogador.getInventario().removerItem(item);
            
        } else {
            mensagem.append("⚠️ Este item não pode ser usado agora.");
        }
        
        return mensagem.toString();
    }

    /**
     * Inicia a batalha final contra Giovanni
     */
    public Inimigo iniciarBatalhaFinal() {
        inimigoAtual = new Inimigo("Giovanni (L\u00edder da Equipe Rocket)",
                                    350,  // HP muito alto
                                    40,   // Ataque forte
                                    25,   // Defesa s\u00f3lida
                                    35);  // N\u00edvel 35 (boss forte)
        
        // Adicionar itens ao inventário do Giovanni
        inimigoAtual.getInventario().adicionarItem(new Item("Super Poção", "Recupera 50 pontos de vida", "cura", 3));
        inimigoAtual.getInventario().adicionarItem(new Item("Reviver", "Recupera completamente a vida", "cura", 1));
        
        emBatalha = true;
        batalhaComRocket = true;
        
        return inimigoAtual;
    }

    /**
     * Finaliza a batalha final com vitória
     */
    public String finalizarBatalhaFinalVitoria() {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("🏆 VITÓRIA ÉPICA!\n\n");
        mensagem.append("Você derrotou Giovanni e resgatou seu amigo ").append(amigo.getNome()).append("!\n\n");
        mensagem.append("==============================================\n");
        mensagem.append("        PARABÉNS! VOCÊ VENCEU O JOGO!\n");
        mensagem.append("==============================================\n");
        mensagem.append("\n").append(amigo.getNome()).append(" está livre e agradece por sua coragem!\n");
        mensagem.append("A Equipe Rocket foi derrotada e a paz foi restaurada!");
        
        encerrarBatalha();
        return mensagem.toString();
    }
}
